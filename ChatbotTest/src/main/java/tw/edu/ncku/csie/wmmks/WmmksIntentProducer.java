package tw.edu.ncku.csie.wmmks;

import java.io.IOException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.idv.ken.data.DialogueAnalysis;
import tw.mt.com.xbot.nlu.IntentProducer;

/**
 *
 * @version 1.0 2017年4月27日
 * @author NCKU WMMKS LAB
 *
 */
public class WmmksIntentProducer implements IntentProducer {

    /**
     * Slf4j logger instance.
     */
    private final Logger logger = LoggerFactory.getLogger(POS.class);

    /**
     * This is a demo.
     * @param args system format
     * @throws IOException if anything goes wrong when read dictionary file
     */
    public static void main(final String[] args) throws IOException {
        // TODO Auto-generated method stub
        BM25 question = new BM25();
        WmmksIntentProducer wmmksIntentProducer = new WmmksIntentProducer();
        System.out.println("Hello ~ My name is chatbot ^^ ");
        while (true) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();
        DialogueAnalysis intent = wmmksIntentProducer.produce(sentence);
        System.out.println("取出的Entity格式:");
        System.out.print("QW(" + intent.getQuestionWord() + ") ");
        System.out.print("Act(" + intent.getAct() + ") ");
        System.out.print("Target(" + intent.getTarget() + ") ");
        System.out.println("Feature(" + intent.getFeatures() + ") ");
        question.rankBM25(sentence);
        }
    }

    /**
     * DiagloueAnalysis produce, input sentence Produce object.
     * @param sentence
     * @return return object
     */
    @Override
    public DialogueAnalysis produce(final String sentence) {
        DA entity = new DA();
        try {
            entity.ner(sentence);
            logger.info(String.format(
                    "found entities [question, act, target, features]=[%s, %s, %s, %s]",
                    entity.getQuestionWord(), entity.getAct(), entity.getTarget(),
                    entity.getFeature()));
            return new DialogueAnalysis(sentence, entity.getQuestionWord(), entity.getAct(),
                    entity.getTarget(),
                    entity.getFeature().toArray(new String[entity.getFeature().size()]));
        } catch (IOException e) {
            e.printStackTrace();
            return DialogueAnalysis.emptyInstance();
        }
    }
}

