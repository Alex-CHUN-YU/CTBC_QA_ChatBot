/*
* To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 根據wmmks lab的建議，重新設計用來表達使用者意圖的類別.
 * @author ken
 */
public final class DialogueAnalysis {
    /**
     *總共有幾個可能的Entity.
     */
    private static final int TOTAL_ENTITY_NUMBER = 4;
    /**
     * 原始問句.
     */
    private String originalQuestion = "";
    /**
     * 句子中的疑問.
     */
    private String questionWord = "";
    /**
     * 句子中的動作.
     */
    private String act = "";
    /**
     * 句子中的目標.
     */
    private String target = "";
    /**
     * 特徵，句子中作為修飾前三者之用.
     */
    private String[] features;

    /**
     * Static method to quickly get an empty instance.
     * @return an empty instance
     */
    public static DialogueAnalysis emptyInstance() {
        return new DialogueAnalysis();
    }

    /**
     * Non-arg constructor.
     */
    protected DialogueAnalysis() {

    }

    /**
     * 主要的constuctor.
     * @param originalQuestion 原始問句
     * @param questionWord 疑問
     * @param act 動作
     * @param target 目標
     * @param features 修飾用的其他特徵
     */
    public DialogueAnalysis(final String originalQuestion, final String questionWord,
            final String act, final String target, final String... features) {
        this.originalQuestion = originalQuestion;
        this.questionWord = questionWord;
        this.act = act;
        this.target = target;
        this.features = Arrays.stream(features)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new);
    }

    /**
     * @return the questionWord
     */
    public String getQuestionWord() {
        return questionWord;
    }

    /**
     * @return the act
     */
    public String getAct() {
        return act;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @return the characteristics
     */
    public List<String> getFeatures() {
        if (features != null) {
            return Arrays.asList(features);
        } else {
            return new ArrayList<String>();
        }
    }

    /**
     * 取得找到的Entity數量.
     * @return 找到的Entity數量
     */
    public int getNumbersOfFoundEntities() {
        int count = TOTAL_ENTITY_NUMBER;
        if (this.getQuestionWord().isEmpty()) {
            count--;
        }
        if (this.getAct().isEmpty()) {
            count--;
        }
        if (this.getTarget().isEmpty()) {
            count--;
        }
        if (this.getFeatures().isEmpty()) {
            count--;
        }
        return count;
    }

    @Override
    public String toString() {
        return String.format("question(%s)-act(%s)-target(%s)-features(%s)", this.questionWord,
                this.act, this.target, this.getFeatures().stream().collect(Collectors.joining()));
    }

    public String getOriginalQuestion() {
        return originalQuestion;
    }

    /**
     * @param questionWord the questionWord to set
     */
    public void setQuestionWord(String questionWord) {
        this.questionWord = questionWord;
    }

    /**
     * @param act the act to set
     */
    public void setAct(String act) {
        this.act = act;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * Get value by entity id.
     * @param entityId entity id @see Entity
     * @return entity value
     */
    public String getEntityValue(String entityId) {
        switch (entityId) {
        case Entity.ENTITY_ID_QUESTION:
            return this.getQuestionWord();
        case Entity.ENTITY_ID_ACT:
            return this.getAct();
        case Entity.ENTITY_ID_TARGET:
            return this.getTarget();
        case Entity.ENTITY_ID_FEATURES:
            return String.join(" ", this.getFeatures());
        default:
            return "";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DialogueAnalysis)) {
            return false;
        }
        DialogueAnalysis d = (DialogueAnalysis) obj;
        return this.getEntityValue(Entity.ENTITY_ID_QUESTION)
                .equalsIgnoreCase(d.getEntityValue(Entity.ENTITY_ID_QUESTION))
                && this.getEntityValue(Entity.ENTITY_ID_ACT)
                        .equalsIgnoreCase(d.getEntityValue(Entity.ENTITY_ID_ACT))
                && this.getEntityValue(Entity.ENTITY_ID_TARGET)
                        .equalsIgnoreCase(d.getEntityValue(Entity.ENTITY_ID_TARGET))
                && this.getEntityValue(Entity.ENTITY_ID_FEATURES)
                        .equalsIgnoreCase(d.getEntityValue(Entity.ENTITY_ID_FEATURES));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
