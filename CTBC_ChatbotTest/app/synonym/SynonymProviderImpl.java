/*
 * SynonymProviderImpl.java    1.0 2017年5月10日
 *
 * Copyright (c) 2017-2030 Monmouth Technologies, Inc.
 * http://www.mt.com.tw
 * 10F-1 No. 306 Chung-Cheng 1st Road, Linya District, 802, Kaoshiung, Taiwan
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Monmouth
 * Technologies, Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you
 * entered into with Monmouth Technologies.
 */
package synonym;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;
import data.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 同義詞服務的實作類別.
 *
 * @version 1.0 2017年5月10日
 * @author ken
 *
 */
public class SynonymProviderImpl implements SynonymProvider {
    /**
     * 作為同義詞詞典的CSV檔路徑和檔名.
     */
    public static final String DICTIONARY_SYNONYM_CSV = "dictionary/Synonym.csv";
    /**
     * 由CSV檔讀取Entity及同義詞定義的Cell Parsers,只針對一定要存在的Cell.
     */
    private final CellProcessor[] basicProcessors = new CellProcessor[] {
            new NotNull(), // EntityType
            new NotNull(), // EntityId
            new NotNull()}; // ReferenceValue
    /**
     * 由CSV檔讀取Entity及同義詞定義的Cell Parsers,針對所有Cell.
     */
    private CellProcessor[] allProcessors;
    /**
     * 由CSV檔讀取出來的所有Entity及同義詞定義.
     */
    private List<Entity> entityDefinitions = new ArrayList<>();
    /**
     * Default constructor.
     * @throws IOException if any io action fails
     */
    public SynonymProviderImpl() throws IOException {
        int colNum = 0;
        File file = new File(".");
        String path = file.getCanonicalPath() + "\\app\\resources\\";
        try (ICsvListReader listReader = new CsvListReader(
                new InputStreamReader(new FileInputStream(path + DICTIONARY_SYNONYM_CSV), StandardCharsets.UTF_8),
                CsvPreference.STANDARD_PREFERENCE)) {
            listReader.getHeader(true); // skip the header (can't be used with CsvListReader)
            colNum = listReader.length();
            allProcessors = new CellProcessor[colNum];
            System.arraycopy(basicProcessors, 0, allProcessors, 0, basicProcessors.length);
            for (int i = basicProcessors.length; i < allProcessors.length; i++) {
                allProcessors[i] = new Optional();
            }
            while ((listReader.read()) != null) {
                final List<Object> customerList = listReader.executeProcessors(allProcessors);
                entityDefinitions.add(makeEntity(customerList));
            }
        }
    }

    /**
     * Create Entity instance by data read from csv file.
     * @param data data read from csv file
     * @return an Entity instance
     */
    private Entity makeEntity(final List<Object> data) {
        List<String> strings = data.stream().filter(d -> d != null)
                .map(object -> Objects.toString(object, null))
                .collect(Collectors.toList());
        Entity entity = new Entity();
        entity.setEntityId(strings.get(0));
        entity.setValueId(strings.get(1));
        entity.setReferenceValue(strings.get(2));
        entity.setSynonyms(strings.subList(2, strings.size()).toArray(new String[0]));
        return entity;
    }

    /* (non-Javadoc)
     * @see tw.com.mt.synonym.SynonymProvider#findReferenceValue(java.lang.String)
     */
    @Override
    public String findReferenceValue(final String entityId, final String word) {
        java.util.Optional<Entity> entity = findEntityDefinition(entityId, word);
        if (entity.isPresent()) {
            return entity.get().getReferenceValue();
        } else {
            return "";
        }
    }

    /* (non-Javadoc)
     * @see tw.com.mt.synonym.SynonymProvider#getSynonyms(java.lang.String)
     */
    @Override
    public String[] getSynonyms(final String entityId, final String word) {
        java.util.Optional<Entity> entity = findEntityDefinition(entityId, word);
        if (entity.isPresent()) {
            return entity.get().getSynonyms();
        } else {
            return new String[0];
        }
    }

    /**
     * 根據Entity Id和一個字詞來尋找是否有定義這樣一個Entity.
     * @param entityId entity id
     * @param word 一個字詞
     * @return 可能找到的Entity
     */
    private java.util.Optional<Entity> findEntityDefinition(final String entityId,
            final String word) {
        java.util.Optional<Entity> entity = this.entityDefinitions.stream()
                .filter(e -> e.getEntityId().equalsIgnoreCase(entityId)
                        && (e.getReferenceValue().equalsIgnoreCase(word)
                                || Arrays.asList(e.getSynonyms()).contains(word)))
                .findFirst();
        return entity;
    }

    /* (non-Javadoc)
     * @see tw.com.mt.synonym.SynonymProvider#getSynonymPattern(java.lang.String)
     */
    @Override
    public String getSynonymPattern(final String entityId, final String word) {
        StringBuilder builder = new StringBuilder();
        builder.append(".*[");
        String[] synonyms = this.getSynonyms(entityId, word);
        if (synonyms.length <= 0) {
            return "";
        }
        Arrays.stream(synonyms).forEach(s -> {
            builder.append(s);
            builder.append("|");
        });
        builder.deleteCharAt(builder.lastIndexOf("|"));
        builder.append("]+.*");
        return builder.toString();
    }

    @Override
    public String[] getReferenceValues(String entityId) {
        String[] values = entityDefinitions.stream()
                .filter(e -> e.getEntityId().equalsIgnoreCase(entityId))
                .map(e -> e.getReferenceValue()).toArray(String[]::new);
        return values;
    }
}
