/*
 * SynonymProvider.java    1.0 2017年5月10日
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

import java.util.Arrays;

/**
 * 提供與同義詞有關的服務方法.
 *
 * @version 1.0 2017年5月10日
 * @author ken
 *
 */
public interface SynonymProvider {
    /**
     * 找到某字詞所屬的代表詞.
     * @param entityId Entity Id
     * @param word 字詞
     * @return 代表詞
     */
    String findReferenceValue(String entityId, String word);
    /**
     * 找出某類Entity的所有代表值(Reference Value),ex:疑問這種Entity可能有以下代表值:如何 為何 哪裡.
     * @param entityId Entity Id
     * @return 某類Entity所有代表值組成的一個字串陣列
     */
    String[] getReferenceValues(String entityId);

    /**
     * 取得與某字詞具有相同意義的所有同義詞,包含該字詞本身.
     * @param entityId Entity Id
     * @param word 字詞
     * @return 所有同義詞組成的一個字串陣列
     */
    String[] getSynonyms(String entityId, String word);

    /**
     * 比較兩個字詞是否為同義詞.
     * @param entityId Entity Id
     * @param word1 字詞1
     * @param word2 字詞2
     * @return true if two words are synonyms
     */
    default boolean wordsAreSynonyms(String entityId, String word1, String word2) {
        return Arrays.asList(getSynonyms(entityId, word1)).contains(word2);
    }

    /**
     * 用所有同義詞來組成用來做字串樣式比對的Pattern.
     * @param entityId Entity Id
     * @param word 字詞
     * @return 樣式比對用的Pattern
     */
    String getSynonymPattern(String entityId, String word);
}
