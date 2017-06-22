package controllers;

import data.DialogueAnalysis;

/**
 * Created by alex on 2017/6/22.
 */
public class Threshold {

    public boolean pass(String sentence){
        WmmksIntentProducer wmmksIntentProducer = new WmmksIntentProducer();
        DialogueAnalysis intent = wmmksIntentProducer.produce(sentence);
        System.out.println("Extract Entity Format:");
        System.out.print("QW(" + intent.getQuestionWord() + ") ");
        System.out.print("Act(" + intent.getAct() + ") ");
        System.out.print("Target(" + intent.getTarget() + ") ");
        System.out.println("Feature(" + intent.getFeatures() + ") ");

        if (!intent.getTarget().equals("")) {
            return true;
        }
        if (intent.getAct().equals("")) {
            return false;
        }
        else {
            //if ()
        }

        return false;
    }

}
