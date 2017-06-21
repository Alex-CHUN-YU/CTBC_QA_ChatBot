/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlu;

import data.DialogueAnalysis;

/**
 *
 * @author ken
 */
public interface IntentProducer {

    /**
     * 根據使用者的輸入句，解析出他的意圖（intent）.
     * @param sentence 輸入句
     * @return a DialogueAnalysis instance
     */
    DialogueAnalysis produce(String sentence);
}
