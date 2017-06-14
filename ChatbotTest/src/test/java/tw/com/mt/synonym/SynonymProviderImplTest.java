/*
 * SynonymProviderImplTest.java    1.0 2017年5月10日
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
package tw.com.mt.synonym;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tw.idv.ken.data.Entity;

/**
 * Write down the class description here.
 *
 * @version 1.0 2017年5月10日
 * @author ken
 *
 */
public class SynonymProviderImplTest {
    /**
     * 受測對象.
     */
    private static SynonymProvider instance;
    /**
     * 存放所有類型Entity Id的陣列.
     */
    private static String[] entityIdArray = {Entity.ENTITY_ID_QUESTION, Entity.ENTITY_ID_ACT,
            Entity.ENTITY_ID_TARGET, Entity.ENTITY_ID_FEATURES};
    /**
     * 存放各類型Entity各自有多少代表值的陣列.
     */
    private static int[] entityIdCountArray = {0, 0, 0, 0};
    /**
     * @throws java.lang.Exception if anything goes wrong
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            instance = new SynonymProviderImpl();
            countValuesByEntity();
        } catch (IOException e) {
            fail("construct SynonymProvider instance fails");
        }
    }

    /**
     * 由同義詞詞典CSV檔計算各類Entity各有多少不同的代表值.
     * @throws IOException if read csv file fails
     */
    private static void countValuesByEntity() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Thread.currentThread().getContextClassLoader()
                                .getResourceAsStream(SynonymProviderImpl.DICTIONARY_SYNONYM_CSV),
                        StandardCharsets.UTF_8));
        String n;
        while ((n = br.readLine()) != null) {
            for (int i = 0; i < entityIdArray.length; i++) {
                if (n.startsWith("\"" + entityIdArray[i] + "\"")) {
                    entityIdCountArray[i]++;
                }
            }
        }
    }

    /**
     * @throws java.lang.Exception if anything goes wrong
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Test entity definition: C,022,自行停話,自停,自願停話,,,.
     */
    @Test
    public void testC022() {
        String word = "自停";
        assertEquals("自行停話", instance.findReferenceValue(Entity.ENTITY_ID_TARGET, word));
        assertEquals(3, instance.getSynonyms(Entity.ENTITY_ID_TARGET, word).length);
        assertEquals(".*[自行停話|自停|自願停話]+.*",
                instance.getSynonymPattern(Entity.ENTITY_ID_TARGET, word));
    }

    /**
     * Test getReferenceValues().
     */
    @Test
    public void testGetReferenceValues() {
        for (int i = 0; i < entityIdArray.length; i++) {
            String[] result = instance.getReferenceValues(entityIdArray[i]);
            assertNotNull(result);
            assertEquals(entityIdCountArray[i], result.length);
        }
    }

    /**
     * Test two words are synonyms.
     */
    @Test
    public void testWordsAreSynonyms() {
        assertTrue(instance.wordsAreSynonyms(Entity.ENTITY_ID_QUESTION, "何謂", "怎樣"));
        assertTrue(instance.wordsAreSynonyms(Entity.ENTITY_ID_ACT, "取消", "停用"));
    }
}
