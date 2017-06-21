/*
 * Synonym.java    1.0 2017年5月10日
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
package data;

/**
 * 代表某個Entity定義的類別.ex:疑問(question)是一種Entity,它有“如何“、"為何"、"哪裡"、"哪些"這四種可能的值,其中的“如何“
 * 擁有"怎麼辦"、"怎辦"、"怎麼"、"怎樣"四個同義詞.所以系統裡會有四個本類別實例（Entity instance） ,它們的Entity
 * Id都是"A"（定義為ENTITY_ID_QUESTION），而referenceValue分別是“如何“、 "為何"、"哪裡"、"哪些". 在這四個Entity
 * instance裡，referenceValue是“如何“的那個instance ,它的synonyms就是["怎麼辦","怎辦","怎麼","怎樣"]
 *
 * @version 1.0 2017年5月10日
 * @author ken
 *
 */
public class Entity {
    /**
     * 代表疑問這種Entity的Id值.
     */
    public static final String ENTITY_ID_QUESTION = "A";
    /**
     * 代表動作這種Entity的Id值.
     */
    public static final String ENTITY_ID_ACT = "B";
    /**
     * 代表目標這種Entity的Id值.
     */
    public static final String ENTITY_ID_TARGET = "C";
    /**
     * 代表特徵這種Entity的Id值.
     */
    public static final String ENTITY_ID_FEATURES = "D";
    /**
     * Entity Id值.
     */
    private String entityId = "";
    /**
     * 這個Entity代表值(Reference Value)的Id.
     */
    private String valueId = "";
    /**
     * 這個Entity的代表值.
     */
    private String referenceValue = "";
    /**
     * 這個Entity的所有同義詞.
     */
    private String[] synonyms;
    /**
     * Default constructor.
     */
    public Entity() {
        // TODO Auto-generated constructor stub
    }
    /**
     * @return the entityId
     */
    public final String getEntityId() {
        return entityId;
    }
    /**
     * @param entityId the entityId to set
     */
    public final void setEntityId(String entityId) {
        this.entityId = entityId;
    }
    /**
     * @return the valueId
     */
    public final String getValueId() {
        return valueId;
    }
    /**
     * @param valueId the valueId to set
     */
    public final void setValueId(String valueId) {
        this.valueId = valueId;
    }
    /**
     * @return the referenceValue
     */
    public final String getReferenceValue() {
        return referenceValue;
    }
    /**
     * @param referenceValue the referenceValue to set
     */
    public final void setReferenceValue(String referenceValue) {
        this.referenceValue = referenceValue;
    }
    /**
     * @return the synonyms
     */
    public final String[] getSynonyms() {
        return synonyms;
    }
    /**
     * @param synonyms the synonyms to set
     */
    public final void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }
}
