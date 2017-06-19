/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.mt.com.xbot.nlu;

import tw.idv.ken.data.DialogueAct;

/**
 * All NLU related methods are defined here.
 * @author ken
 * @deprecated use {@link IntentProducer} instead.
 */
@Deprecated
public interface NaturalLangUnderstanding {
    /**
     * 將使用者輸入的句子轉換為Dailogue Act.
     * @param sentence 使用者輸入的句子
     * @return a DailogueAct instance
     */
    DialogueAct makeDialogueAct(String sentence) ;
}
