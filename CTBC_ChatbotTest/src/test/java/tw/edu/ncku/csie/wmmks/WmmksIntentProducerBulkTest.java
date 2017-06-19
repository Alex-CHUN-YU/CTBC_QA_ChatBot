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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Test;

import com.opencsv.CSVReader;

import tw.idv.ken.data.DialogueAnalysis;
import tw.mt.com.xbot.nlu.IntentProducer;

/**
 * <p>針對 ncku wmmks lab 會話分析程式的大宗測試類別.</p>
 * <p>
 * (放置 class path 中的) WmmksIntentProducerBulkTest.csv 存放測試用樣本<br/>
 * 檔案第一行為欄位名稱，前六欄："No"、"例句"、"疑問"、"動作"、"目標"、"特徵"<br/>
 * 檔案第第二行開始，存放測試用樣本，依第一行的欄位名稱所示，擺放測試樣本資料於各欄位<br/>
 * <br/>
 * 每行測試樣本被依序帶入進行測試，<br/>
 * 每行的"例句"傳入會話分析程式，並預期會話分析的結果如每行的"疑問"、"動作"、"目標"、"特徵"<br/>
 * </p>
 */
@RunWith(Parameterized.class)
public class WmmksIntentProducerBulkTest {

    /** WmmksIntentProducer Instance. */
    private static IntentProducer producer = null;

    /** 大宗測試樣本檔名. */
    private static String testPatternFileName = "WmmksIntentProducerBulkTest.csv";

    /**
     * @throws java.lang.Exception 錯誤發生時的例外
     */
    @BeforeClass
    public static void setUp4Class() throws Exception {
        producer = new WmmksIntentProducer();
    }

    /**
     * @throws java.lang.Exception 錯誤發生時的例外
     */
    @AfterClass
    public static void tearDown4Class() throws Exception {
        producer = null;
    }

    /**
     * 產生大宗測試資料.
     * @return 大宗測試資料
     **/
    @Parameters
    public static Collection<String[]> getTestPatterns() {
        Collection<String[]> ret = new ArrayList<String[]>();
        CSVReader reader = null;
        try {
            InputStream is = WmmksIntentProducerBulkTest.class.getClassLoader()
                    .getResourceAsStream(testPatternFileName);
            reader = new CSVReader(new InputStreamReader(is));

            String[] row; // 首 六欄 欄位: [No][例句][疑問][動作][目標][特徵]
            boolean isFirstRow = true;
            while ((row = reader.readNext()) != null) {
                if (isFirstRow) { //跳過首行的欄位名
                    isFirstRow = false;
                    continue;
                }

                // [例句(Sentence)][疑問(Question)][動作(Act)][目標(Target)][特徵(Features)]
                final int stupid5 = 5;
                String[] sxQxAxTxF = new String[stupid5];

                for (int i = 0; i < sxQxAxTxF.length; i++) {
                    sxQxAxTxF[i] = "";
                    int colInd = i + 1; // 跳過首欄的[No]，自第二欄位[例句]開始讀
                    if (row.length > colInd && row[colInd] != null) {
                        sxQxAxTxF[i] = row[colInd].trim();
                    }
                }

                final int stupid4 = 4;
                boolean hasNoSentence = sxQxAxTxF[0].equals("");
                boolean hasNoEntity = sxQxAxTxF[1].equals("")
                        && sxQxAxTxF[2].equals("") && sxQxAxTxF[3].equals("")
                        && sxQxAxTxF[stupid4].equals("");
                if (hasNoSentence || hasNoEntity) { //無效的行跳過
                    continue;
                }

                ret.add(sxQxAxTxF);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }

    /** 例句. */
    private String sentence;
    /** 疑問. */
    private String question;
    /** 動作. */
    private String act;
    /** 目標. */
    private String target;
    /** 特徵. */
    private List<String> features;
    /** 會話分析結果. */
    private DialogueAnalysis da;

    /**
     * @param s 例句
     * @param q 疑問
     * @param a 動作
     * @param t 目標
     * @param f 特徵。可能有零到多個特徵，每行表示一個特徵
     */
    public WmmksIntentProducerBulkTest(
            final String s,
            final String q, final String a, final String t, final String f
    ) {
        this.sentence = s;
        this.question = q;
        this.act = a;
        this.target = t;
        this.features = new ArrayList<String>();
        for (String line : f.split("\\r?\\n")) {
            if (line.trim().equals("")) {
                continue;
            }
            features.add(line.trim());
        }
    }

    /**
     * @throws java.lang.Exception 錯誤發生時的例外
     */
    @Before
    public void setUp()  throws Exception {
        this.da = producer.produce(this.sentence);
    }

    /**
     * @throws java.lang.Exception 錯誤發生時的例外
    */
    @After
    public void tearDown()  throws Exception {
        this.da = null;
    }

    /**
     * Testing for DialogueAnalysis instance.
     */
    @Test
    public void testDialogueAnalysisNotNull() {
        assertNotNull(da);
    }

    /**
     * Testing for DialogueAnalysis's Question.
     */
    @Test
    public void testDialogueAnalysisQuestion() {
        assertEquals("疑問不符合", this.question, da.getQuestionWord());
    }

    /**
     * Testing for DialogueAnalysis's Action.
     */
    @Test
    public void testDialogueAnalysisAct() {
        assertEquals("動作不符合", this.act, da.getAct());
    }

    /**
     * Testing for DialogueAnalysis's Target.
     */
    @Test
    public void testDialogueAnalysisTarget() {
        assertEquals("目標不符合", this.target, da.getTarget());
    }

    /**
     * Testing for DialogueAnalysis's Features.
     */
    @Test
    public void testDialogueAnalysisFeatures() {
        assertTrue(
                String.format("特徵的預期(%s)與結果(%s)不符合", this.features, da.getFeatures()),
                this.features.containsAll(da.getFeatures())
                && da.getFeatures().containsAll(this.features)
        );
    }

    /**
     * @param args 傳入的參數
     */
    public static void main(final String[] args) {
//        try {
//            for (String[] sxQxAxTxF : getTestPatterns()) {
//                for (int i = 0; i < sxQxAxTxF.length; i++) {
//                    System.out.print("[" + sxQxAxTxF[i] + "]");
//                }
//                WmmksIntentProducerBulkTest w = new WmmksIntentProducerBulkTest(
//                        sxQxAxTxF[0],
//                        sxQxAxTxF[1], sxQxAxTxF[2], sxQxAxTxF[3], sxQxAxTxF[4]
//                );
//                System.out.print("==>" + w.features);
//                System.out.println();
//            }
//        } catch (Throwable t) {
//            t.printStackTrace();
//        }
    }
}
