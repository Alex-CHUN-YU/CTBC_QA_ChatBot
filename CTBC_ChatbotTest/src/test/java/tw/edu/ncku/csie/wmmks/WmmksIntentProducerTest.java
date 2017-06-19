/*
 * WmmksIntentProducerTest.java    1.0 2017年4月25日
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
package tw.edu.ncku.csie.wmmks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.*;
import java.io.IOException;
import java.util.AbstractMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tw.idv.ken.data.DialogueAnalysis;
import tw.mt.com.xbot.nlu.IntentProducer;

/**
 * 針對ncku wmmks lab程式的測試類別.
 *
 * @version 1.0 2017年4月25日
 * @author ken
 *
 */
public class WmmksIntentProducerTest {
    private static IntentProducer producer = new WmmksIntentProducer();
    private DialogueAnalysis result;
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testQ1() throws IOException {
        result = producer.produce("手機上網如何設定?");
        assertNotNull(result);
        assertEquals("如何", result.getQuestionWord());
        assertEquals("設定", result.getAct());
        assertEquals("手機上網", result.getTarget());
        assertEquals(0, result.getFeatures().size());
    }

    @Test
    public void testQ4() throws IOException {
        result = producer.produce("「保證金」退還作業方式為何?");
        assertNotNull(result);
        assertEquals("為何", result.getQuestionWord());
        assertEquals("退還", result.getAct());
        assertEquals("保證金", result.getTarget());
        assertEquals(1, result.getFeatures().size());
        assertThat(result.getFeatures(),hasItems("作業方式"));
    }
   
       @Test
   public void testSimpleInputWithNumber() throws IOException {
       result = producer.produce("1");
       assertNotNull(result);
       assertEquals("", result.getQuestionWord());
       assertEquals("", result.getAct());
       assertEquals("", result.getTarget());
       assertEquals(0, result.getFeatures().size());
   }
   
   @Test
   public void testSimpleInputWithLetters() throws IOException {
       result = producer.produce("a b c d");
       assertNotNull(result);
       assertEquals("", result.getQuestionWord());
       assertEquals("", result.getAct());
       assertEquals("", result.getTarget());
       assertEquals(0, result.getFeatures().size());
   }
   
   @Test
   public void testSimpleInputWithMeaninglessWords() throws IOException {
       result = producer.produce("abcd");
       assertNotNull(result);
       assertEquals("", result.getQuestionWord());
       assertEquals("", result.getAct());
       assertEquals("", result.getTarget());
       assertEquals(0, result.getFeatures().size());
   }
}
