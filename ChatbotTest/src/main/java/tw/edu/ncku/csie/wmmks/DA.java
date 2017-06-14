package tw.edu.ncku.csie.wmmks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import tw.com.mt.synonym.SynonymProvider;
import tw.com.mt.synonym.SynonymProviderImpl;
import tw.edu.ncku.csie.wmmks.POS.Tuple;
import tw.idv.ken.data.Entity;

/**
 * Parse user input and generate dialogue analysis result.
 *
 * @version 1.0 2017年4月27日
 * @author NCKU WMMKS LAB
 *
 */
public class DA {

    /**
     * POS詞典檔相對路徑.
     */
    private static final String POS_DICT_DIR = "POSdictionary/";

    /**
     * 詞典檔延伸名.
     */
    private static final String FILE_EXTENTION = ".txt";

    /**
     * 處理階段用的中介字串.
     */
    @SuppressWarnings("unused")
    private static String qw = "", target = "", act = "";

    /**
     * 處理階段用的中介字串List.
     */
    private static ArrayList<String> feature;

    /**
     * CKIP斷詞.
     */
    private static ArrayList<Tuple<String, String>> pos;

    /**
     * CKIP斷詞.
     */
    private static ArrayList<Tuple<String, String>> eventPOS;

    /**
     * Doc Of Corpus.
     */
    private static ArrayList<String> doc;

    /**
     * sentence.
     */
    private static String userSentence;

    /**
     * sentence.
     */
    private static ReadTermWeightDic ra;

    /**
     * 詞性為一組配對時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set stopWordSet = new HashSet();

    /**
     * 詞性為一組配對時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set tripleQWSet = new HashSet();

    /**
     * 詞性為一組配對時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set doubleQWSet = new HashSet();

    /**
     * 詞性單一時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set singleQWSet = new HashSet();

    /**
     * 詞性為一組配對時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set doubleActSet = new HashSet();

    /**
     * 詞性單一時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set singleActSet = new HashSet();

    /**
     * 集合的迭代.
     */
    @SuppressWarnings("rawtypes")
    private static Iterator iterator;

    /**
     * 初始化.
     */
    public DA() {
        //Initial
        DA.initial();
    }

    /**
     * initial dictionary.
     */
    public static void initial() {
        //dictionary(); //storage to hash map
        eventPOS = new ArrayList<Tuple<String, String>>();
        pos = new ArrayList<Tuple<String, String>>();
        doc = new ArrayList<String>();
        try {
            ra = new ReadTermWeightDic();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        stopDictionary();
        posDictionary("tripleQWSet");
        posDictionary("doubleQWSet");
        posDictionary("singleQWSet");
        posDictionary("doubleActSet");
        posDictionary("singleActSet");
    }

    /**
     * word classification.
     * @param word POS
     * @param entityName QW or Act or Target or Feature
     */
    private static void wordClassification(final String entityName, final String word) {
        SynonymProvider instance = null;
        try {
            instance = new SynonymProviderImpl();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        String synonym = instance.findReferenceValue(entityName, word);
        int equalNum;
        equalNum = 0;
            POS.Tuple<String, String> pair =
                    new POS.Tuple<String, String>(synonym, entityName);
           for (int i = 0; i < eventPOS.size(); i++) {
                if (eventPOS.get(i).getWord().equals(pair.getWord())
                        && eventPOS.get(i).getPos().equals(pair.getPos())) {
                    equalNum = 1;
                }
            }
           if (equalNum == 0 && !pair.getWord().equals("")) {
              eventPOS.add(pair);
           }

    }

    /**
     * Read stopWord dictionaries.
     */
    private static void stopDictionary() {
            try {
                readStopDic("stopWord",
                        getBufferedReader("stopWord" + FILE_EXTENTION));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * Read all content from a dictionary file.
     * @param entityName entity name, Example: QW(question word)
     * @param reader file reader for a dictionary file
     * @throws IOException if anything goes wrong when read dictionary file
     */
    @SuppressWarnings("unchecked")
    private static void readStopDic(final String entityName, final BufferedReader reader)
            throws IOException {
        while (reader.ready()) {
            stopWordSet.add(reader.readLine());
        }
    }

    /**
     * Read POS dictionaries.
     * @param setName SetName
     */
    private static void posDictionary(final String setName) {
            try {
                readSetContent(setName,
                        getBufferedReader(POS_DICT_DIR + setName + FILE_EXTENTION));
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    /**
     * Read all content from a POSdictionary file.
     * @param entityName entity name, Example: doubleQWSet
     * @param reader file reader for a dictionary file
     * @throws IOException if anything goes wrong when read dictionary file
     */
    @SuppressWarnings("unchecked")
    private static void readSetContent(final String entityName,
            final BufferedReader reader) {
        @SuppressWarnings("rawtypes")
        Set temp = null;
        if (entityName.equals("tripleQWSet")) {
            temp = tripleQWSet;
        }
        if (entityName.equals("doubleQWSet")) {
            temp = doubleQWSet;
        }
        if (entityName.equals("singleQWSet")) {
            temp = singleQWSet;
        }
        if (entityName.equals("doubleActSet")) {
            temp = doubleActSet;
        }
        if (entityName.equals("singleActSet")) {
            temp = singleActSet;
        }
        try {
            while (reader.ready()) {
                temp.add(reader.readLine());
                }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        }

    /**
     * Get buffered reader for a dictionary file.
     * @param fileName dictionary full file name
     * @return a BufferedReader instance
     * @throws UnsupportedEncodingException if UTF-8 is not supported
     */
    private static BufferedReader getBufferedReader(final String fileName)
            throws UnsupportedEncodingException {
        InputStream configStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(configStream, "UTF-8"));
    }

    /**
     * Named entity recognition.
     * @param sentence a sentence
     * @throws IOException if something goes wrong
     */
    public void ner(final String sentence) throws IOException {
        //filter stopWord
        userSentence = sentence;
        iterator = stopWordSet.iterator();
        while (iterator.hasNext()) {
            String oldStr = (String) iterator.next();
            if (userSentence.indexOf(oldStr) > -1) {
                userSentence = userSentence.replace(oldStr, "");
                break;
            }
        }
        //Renew
        eventPOS = new ArrayList<Tuple<String, String>>();
        pos = new ArrayList<Tuple<String, String>>();
        DA.doc = new ArrayList<String>();
        // CKIP POS
        POS ws = new POS();
        pos = ws.seg(userSentence);
        // Extract Event
        target = getTarget();
        feature = getFeature();
        doc.addAll(feature);
        qw = getQuestionWord();
        act = getAct();
    }

    /**
     *
     * @return sentence template
     */
    public ArrayList<String> getTermProduce() {
        //System.out.println(doc);
        return doc;
    }

    /**
     * Find question word(疑問).
     * @return a string which represents user's question
     */
    public String getQuestionWord() {
        String getQW = "";
        ArrayList<String> qwTemp = new ArrayList<String>();
        //determine whether in the tripleQWSet
        for (int i = 0; i + 2 < pos.size(); i++) {
            if ((target.indexOf(pos.get(i).getWord()) <= -1)) {
            iterator = tripleQWSet.iterator();
            while (iterator.hasNext()) {
                String oldStr = (String) iterator.next();
                    if ((pos.get(i).getPos() + pos.get(i + 1).getPos() + pos.get(i + 2).getPos())
                            .equals(oldStr)) {
                        wordClassification(Entity.ENTITY_ID_QUESTION,
                                (pos.get(i).getWord() + pos.get(i + 1).getWord()
                                        + pos.get(i + 2).getWord()));
                    break;
                }
            }
          }
        }
        //determine whether in the doubleQWSet
        for (int i = 0; i + 1 < pos.size(); i++) {
            if ((target.indexOf(pos.get(i).getWord()) <= -1)) {
            iterator = doubleQWSet.iterator();
            while (iterator.hasNext()) {
                String oldStr = (String) iterator.next();
                if ((pos.get(i).getPos() + pos.get(i + 1).getPos()).equals(oldStr)) {
                        wordClassification(Entity.ENTITY_ID_QUESTION,
                                (pos.get(i).getWord() + pos.get(i + 1).getWord()));
                    break;
                }
            }
          }
        }
        //determine whether in the singleQWSet
            for (int i = 0; i < pos.size(); i++) {
                if ((target.indexOf(pos.get(i).getWord()) <= -1)) {
                    iterator = singleQWSet.iterator();
                    while (iterator.hasNext()) {
                    String oldStr = (String) iterator.next();
                    if (pos.get(i).getPos().equals(oldStr)) {
                        wordClassification(Entity.ENTITY_ID_QUESTION, pos.get(i).getWord());
                        break;
                        }
                    }
                }
            }
        //check POS QW
        for (int i = 0; i < eventPOS.size(); i++) {
            if (eventPOS.get(i).getPos().equals(Entity.ENTITY_ID_QUESTION)
                    && (getQW.indexOf(eventPOS.get(i).getWord()) <= -1)
                    && (target.indexOf(eventPOS.get(i).getWord()) <= -1)) {
                if ((eventPOS.get(i).getWord().length() >= getQW.length())
                        && (eventPOS.get(i).getWord().indexOf(getQW) > -1)) {
                    getQW = eventPOS.get(i).getWord();
                    doc.add(getQW);
                } else {
                    qwTemp.add(eventPOS.get(i).getWord());
                    doc.add(eventPOS.get(i).getWord());
                }
            }
        }
        //public ArrayList<String>
        if (!getQW.equals("")) {
            qwTemp.add(getQW);
        }
        if (qwTemp.size() > 1) {
            getQW = ra.getWord(qwTemp);
        }
        //return question word
        return getQW;
    }

    /**
     * Find the target(目標) of user's input sentence.
     * @return a string which represents the target in a sentence
     * @throws IOException if something goes wrong
     */
    public String getTarget() throws IOException {
        ArrayList<String> t = Target.getTarget(pos);
        String getTarget = "";
        for (int i = 0; i < t.size(); i++) {
            wordClassification(Entity.ENTITY_ID_TARGET, t.get(i)); // check dictionary
        }
        for (int i = 0; i < eventPOS.size(); i++) {
            if (eventPOS.get(i).getPos().equals(Entity.ENTITY_ID_TARGET)
                    && (getTarget.indexOf(eventPOS.get(i).getWord()) <= -1)) {
                if ((eventPOS.get(i).getWord().length() >= getTarget.length())
                        && (eventPOS.get(i).getWord().indexOf(getTarget) > -1)) {
                    getTarget = eventPOS.get(i).getWord();
                    doc.add(getTarget);
                } else {
                    getTarget += eventPOS.get(i).getWord();
                    doc.add(eventPOS.get(i).getWord());
                }
            }
        }
        return getTarget;
    }

    /**
     * Find the act(動作) in user's input sentence.
     * @return a string which represents the act in a sentence
     */
    public String getAct() {
        String getAct = "";
       ArrayList<String> actTemp = new ArrayList<String>();
        for (int i = 0; i + 1 < pos.size(); i++) {
            if ((target.indexOf(pos.get(i).getWord()) <= -1)
                    && (qw.indexOf(pos.get(i).getWord()) <= -1)) {
                iterator = doubleActSet.iterator();
                while (iterator.hasNext()) {
                    String oldStr = (String) iterator.next();
                    if ((pos.get(i).getPos() + pos.get(i + 1).getPos()).equals(oldStr)) {
                        wordClassification(Entity.ENTITY_ID_ACT,
                                (pos.get(i).getWord() + pos.get(i + 1).getWord()));
                        break;
                    }
                }
            }
        }
        //determine whether in the singleActSet
        for (int i = 0; i < pos.size(); i++) {
            if ((target.indexOf(pos.get(i).getWord()) <= -1)
                    && (qw.indexOf(pos.get(i).getWord()) <= -1)) {
                iterator = singleActSet.iterator();
                while (iterator.hasNext()) {
                    String oldStr = (String) iterator.next();
                    if (pos.get(i).getPos().equals(oldStr)) {
                        wordClassification(Entity.ENTITY_ID_ACT, pos.get(i).getWord());
                        break;
                    }
                }
            }
        }
        //check POS ACT
        for (int i = 0; i < eventPOS.size(); i++) {
            //don't repeat ACT and 所存在的(act不能存在於 QW and Target)可以不用..
            if (eventPOS.get(i).getPos().equals(Entity.ENTITY_ID_ACT)
                    && (getAct.indexOf(eventPOS.get(i).getWord()) <= -1)
                    && (target.indexOf(eventPOS.get(i).getWord()) <= -1)
                    && (qw.indexOf(eventPOS.get(i).getWord()) <= -1)) {
                // 判斷act
                int flag = 0;
                for (int j = 0; j < feature.size(); j++) {
                if (feature.get(j).indexOf(eventPOS.get(i).getWord()) > -1) {
                    flag = 1;
                    }
                }
                if (flag == 0) {
                if ((eventPOS.get(i).getWord().length() >= getAct.length())
                        && (eventPOS.get(i).getWord().indexOf(getAct) > -1)) {
                    getAct = eventPOS.get(i).getWord();
                    doc.add(getAct);
                } else {
                    actTemp.add(eventPOS.get(i).getWord());
                    doc.add(eventPOS.get(i).getWord());
                }
                }
            }
        }
        if (!getAct.equals("")) {
            actTemp.add(getAct);
        }
        if (actTemp.size() > 1) {
            getAct = ra.getWord(actTemp);
        }
        return getAct;
    }

    /**
     * Find the features(特徵) in user's input sentence.
     * @return a string which represents the features in a sentence
     * @throws IOException 處理例外
     */
    public ArrayList<String> getFeature() throws IOException {
        String getFeature = "";
        ArrayList<String> t = Target.getTarget(pos);
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < t.size(); i++) {
            wordClassification(Entity.ENTITY_ID_FEATURES, t.get(i)); // check dictionary
        }
        for (int i = 0; i < eventPOS.size(); i++) {
            if (eventPOS.get(i).getPos().equals(Entity.ENTITY_ID_FEATURES)
                    && (target.indexOf(eventPOS.get(i).getWord()) <= -1)) {
                getFeature = eventPOS.get(i).getWord();
                temp.add(getFeature);
            }
        }
        return temp;
    }
}
