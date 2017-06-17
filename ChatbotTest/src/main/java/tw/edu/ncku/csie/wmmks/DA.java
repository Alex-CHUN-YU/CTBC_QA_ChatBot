package tw.edu.ncku.csie.wmmks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
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
     * 查完辭典，更改詞性後的結果.
     */
    private static ArrayList<Tuple<String, String>> revisePOS;

    /**
     * Entity.
     */
    private static ArrayList<Tuple<String, String>> entity;

    /**
     * Doc Of Corpus.
     */
    private static ArrayList<String> doc;

    /**
     * sentence.
     */
    private static String userSentence;

    /**
     * readTermWeightDic.
     */
    private static ReadTermWeightDic readTermWeightDic;

    /**
     * 詞性為一組配對時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set stopWordSet = new HashSet();

    /**
     * 詞性單一時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set singleQWSet = new HashSet();

    /**
     * 詞性單一時所需的集合.
     */
    @SuppressWarnings("rawtypes")
    private static Set singleActSet = new HashSet();

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
        entity = new ArrayList<Tuple<String, String>>();
        revisePOS = new ArrayList<Tuple<String, String>>();
        pos = new ArrayList<Tuple<String, String>>();
        doc = new ArrayList<String>();
        try {
            readTermWeightDic = new ReadTermWeightDic();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        stopDictionary();
        posDictionary("singleQWSet");
        posDictionary("singleActSet");
        readDictionary();
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
           for (int i = 0; i < entity.size(); i++) {
                if (entity.get(i).getWord().equals(pair.getWord())
                        && entity.get(i).getPos().equals(pair.getPos())) {
                    equalNum = 1;
                }
            }
           if (equalNum == 0 && !pair.getWord().equals("")) {
              entity.add(pair);
           }
    }
    
    /**
     * dictionary data.
     */
	private static HashMap<String, String> words = new HashMap<String, String>();
    
    /**
     * Read dictionaries.
     */
	private static void readDictionary() {
    	SynonymProvider instance = null;
        String[] entityID = {"A", "B", "C", "D"};
        try {
            instance = new SynonymProviderImpl();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        for (int i = 0; i < entityID.length; i++) {
        	String[] synonym = instance.getReferenceValues(entityID[i]);
        	for (int j = 0; j < synonym.length; j++) { 
        		String[] str = instance.getSynonyms(entityID[i], synonym[j]);
        		for(int k = 0; k < str.length; k++) {
        			words.put(str[k], entityID[i]);
        		}
        	}
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
                readPOSDic(setName,
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
    private static void readPOSDic(final String entityName,
            final BufferedReader reader) {
        @SuppressWarnings("rawtypes")
        Set temp = null;
        if (entityName.equals("singleQWSet")) {
            temp = singleQWSet;
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
     * Revise POS.
     */
    private POS.Tuple<String, String> pair;
    
    /**
     * Named entity recognition.
     * @param sentence a sentence
     * @throws IOException if something goes wrong
     */
    @SuppressWarnings("rawtypes")
	public void ner(final String sentence) throws IOException {
        //Renew
        entity = new ArrayList<Tuple<String, String>>();
        pos = new ArrayList<Tuple<String, String>>();
        DA.doc = new ArrayList<String>();
        revisePOS = new ArrayList<Tuple<String, String>>();
        // CKIP POS
        POS ws = new POS();
        //filter stopWord
        userSentence = sentence;
        Iterator iterator = stopWordSet.iterator();
        while (iterator.hasNext()) {
            String oldStr = (String) iterator.next();
            if (userSentence.indexOf(oldStr) > -1) {
                userSentence = userSentence.replace(oldStr, "");
                break;
            }
        }
        //Determine whether the words in the dictionary are in the sentence
        for (Object key : words.keySet()) {
            //System.out.println(key + " : " + words.get(key));
            if (userSentence.indexOf(key.toString()) > -1) {
            	if (words.get(key).toString().equals("A")) {
            		pair = new POS.Tuple<String, String>(key.toString(), "D");
            	}
            	if (words.get(key).toString().equals("B")) {
            		pair = new POS.Tuple<String, String>(key.toString(), "V");
            	}
            	if (words.get(key).toString().equals("C")) {
            		pair = new POS.Tuple<String, String>(key.toString(), "N");
            	}
            	if (words.get(key).toString().equals("D")) {
            		pair = new POS.Tuple<String, String>(key.toString(), "N");
            	}
            	int tempNumber = revisePOS.size();
                for (int i = 0; i < revisePOS.size(); i++) {
                	if (!revisePOS.get(i).getWord().equals(pair.getWord())) {
                		tempNumber--;
                	}
                }
                if (tempNumber == 0) {
                	revisePOS.add(pair);
                }
            }
        }
        /*for (int i = 0; i < revisePOS.size(); i++) {
        	System.out.println("WORD:" + revisePOS.get(i).getWord() + " POS:" + revisePOS.get(i).getPos());
        }*/
        pos = ws.seg(userSentence);
        //After POS and revise POS
        for (int i = 0; i < pos.size(); i++) {
        	int tempNumber = revisePOS.size();
        	for (int j = 0; j < revisePOS.size(); j++) {
        		if (revisePOS.get(j).getWord().indexOf(pos.get(i).getWord()) <= -1) {
        			tempNumber--;
        		}
        	}
        	if (tempNumber == 0) {
        		pair = new POS.Tuple<String, String>(pos.get(i).getWord(), pos.get(i).getPos());
        		revisePOS.add(pair);
        	}
        }
        /*for (int i = 0; i < revisePOS.size(); i++) {
        	System.out.println("WORD1:" + revisePOS.get(i).getWord() + " POS1:" + revisePOS.get(i).getPos());
        }*/
        // Extract Event
        target = getTarget();
        feature = getFeature();
        //doc.addAll(feature);
        qw = getQuestionWord();
        act = getAct();
    }

    /**
     * For TF-IDF
     * @return sentence template
     */
    public ArrayList<String> getDoc() {
        return doc;
    }

    /**
     * Find question word(疑問).
     * @return a string which represents user's question
     */
    @SuppressWarnings("rawtypes")
	public String getQuestionWord() {
        String questionWord = "";
        ArrayList<String> qwTemp = new ArrayList<String>();
        //determine whether in the singleQWSet
            for (int i = 0; i < revisePOS.size(); i++) {
                if ((target.indexOf(revisePOS.get(i).getWord()) <= -1)) {
                	Iterator iterator = singleQWSet.iterator();
                    while (iterator.hasNext()) {
                    String oldStr = (String) iterator.next();
                    if (revisePOS.get(i).getPos().equals(oldStr)) {
                    	//look for white dictionary and find Synonym
                        wordClassification(Entity.ENTITY_ID_QUESTION, revisePOS.get(i).getWord());
                        break;
                        }
                    }
                }
            }
        //Check POS QW
        for (int i = 0; i < entity.size(); i++) {
        	//System.out.println("1." + entity.get(i).getWord()+ "2."+entity.get(i).getPos());
            if (entity.get(i).getPos().equals(Entity.ENTITY_ID_QUESTION)
                    && (questionWord.indexOf(entity.get(i).getWord()) <= -1)
                    && (target.indexOf(entity.get(i).getWord()) <= -1)) {
                if ((entity.get(i).getWord().length() >= questionWord.length())
                        && (entity.get(i).getWord().indexOf(questionWord) > -1)) {
                    questionWord = entity.get(i).getWord();
                    //System.out.println(questionWord);
                    doc.add(questionWord);
                } else {
                    qwTemp.add(entity.get(i).getWord());
                    //System.out.println(entity.get(i).getWord());
                    doc.add(entity.get(i).getWord());
                }
            }
        }
        //Public ArrayList<String>
        if (!questionWord.equals("")) {
            qwTemp.add(questionWord);
        }
        /*System.out.println("QW靠北工程師");
        for (int i=0;i<doc.size();i++)
        {
        	System.out.println(doc.get(i));
        }*/
        //Compare Priority
        if (qwTemp.size() > 1) {
            questionWord = readTermWeightDic.getWord(qwTemp);
        }
        //return question word
        return questionWord;
    }
    
    /**
     * Find the act(動作) in user's input sentence.
     * @return a string which represents the act in a sentence
     */
    @SuppressWarnings("rawtypes")
	public String getAct() {
        String actionWord = "";
       ArrayList<String> actTemp = new ArrayList<String>();
        //determine whether in the singleActSet
        for (int i = 0; i < revisePOS.size(); i++) {
            if ((target.indexOf(revisePOS.get(i).getWord()) <= -1)
                    && (qw.indexOf(revisePOS.get(i).getWord()) <= -1)) {
            	Iterator iterator = singleActSet.iterator();
                while (iterator.hasNext()) {
                    String oldStr = (String) iterator.next();
                    if (revisePOS.get(i).getPos().equals(oldStr)) {
                    	//look for white dictionary and find Synonym
                        wordClassification(Entity.ENTITY_ID_ACT, revisePOS.get(i).getWord());
                        break;
                    }
                }
            }
        }
        //Check POS ACT
        for (int i = 0; i < entity.size(); i++) {
        	//System.out.println("1." + entity.get(i).getWord() + entity.get(i).getPos());
            //don't repeat ACT and 所存在的(act不能存在於 QW and Target)可以不用..
            if (entity.get(i).getPos().equals(Entity.ENTITY_ID_ACT)
                    && (actionWord.indexOf(entity.get(i).getWord()) <= -1)
                    && (target.indexOf(entity.get(i).getWord()) <= -1)
                    && (qw.indexOf(entity.get(i).getWord()) <= -1)) {
                // 判斷act
                int flag = 0;
                for (int j = 0; j < feature.size(); j++) {
                if (feature.get(j).indexOf(entity.get(i).getWord()) > -1) {
                    flag = 1;
                    }
                }
                if (flag == 0) {
                if ((entity.get(i).getWord().length() >= actionWord.length())
                        && (entity.get(i).getWord().indexOf(actionWord) > -1)) {
                    actionWord = entity.get(i).getWord();
                    doc.add(actionWord);
                } else {
                    actTemp.add(entity.get(i).getWord());
                    doc.add(entity.get(i).getWord());
                }
                }
            }
        }
        if (!actionWord.equals("")) {
            actTemp.add(actionWord);
        }
        /*System.out.println("Act靠北工程師");
        for (int i=0;i<doc.size();i++)
        {
        	System.out.println(doc.get(i));
        }*/
        if (actTemp.size() > 1) {
            actionWord = readTermWeightDic.getWord(actTemp);
        }
        return actionWord;
    }

    /**
     * Find the target(目標) of user's input sentence.
     * @return a string which represents the target in a sentence
     * @throws IOException if something goes wrong
     */
    public String getTarget() throws IOException {
        ArrayList<String> t = Target.getTarget(revisePOS);
        String targetWord = "";
        ArrayList<String> qwTemp = new ArrayList<String>();
        for (int i = 0; i < t.size(); i++) {
            //System.out.println(t.get(i));
            wordClassification(Entity.ENTITY_ID_TARGET, t.get(i)); // check dictionary
        }
        for (int i = 0; i < entity.size(); i++) {
            if (entity.get(i).getPos().equals(Entity.ENTITY_ID_TARGET)
                    && (targetWord.indexOf(entity.get(i).getWord()) <= -1)) {
                if ((entity.get(i).getWord().length() >= targetWord.length())
                        && (entity.get(i).getWord().indexOf(targetWord) > -1)) {
                    targetWord = entity.get(i).getWord();
                    //System.out.println("1."+targetWord);
                    doc.add(targetWord);
                } else {
                    qwTemp.add(entity.get(i).getWord());
                    //getTarget += eventPOS.get(i).getWord();
                    //System.out.println("2."+entity.get(i).getWord());
                    doc.add(entity.get(i).getWord());
                }
            }
        }
        //public ArrayList<String>
        if (!targetWord.equals("")) {
            qwTemp.add(targetWord);
        }
        /*System.out.println("Target靠北工程師");
        for (int i = 0;i<doc.size();i++)
        {
        	System.out.println(doc.get(i));
        }*/
        if (qwTemp.size() > 1) {
            targetWord = readTermWeightDic.getWord(qwTemp);
        }
        return targetWord;
    }

    /**
     * Find the features(特徵) in user's input sentence.
     * @return a string which represents the features in a sentence
     * @throws IOException 處理例外
     */
    public ArrayList<String> getFeature() throws IOException {
        String featureWord = "";
        ArrayList<String> t = Target.getTarget(revisePOS);
        ArrayList<String> temp = new ArrayList<String>();
        for (int i = 0; i < t.size(); i++) {
            //System.out.println(t.get(i));
            wordClassification(Entity.ENTITY_ID_FEATURES, t.get(i)); // check dictionary
        }
        for (int i = 0; i < entity.size(); i++) {
            if (entity.get(i).getPos().equals(Entity.ENTITY_ID_FEATURES)
                    && (target.indexOf(entity.get(i).getWord()) <= -1)) {
                featureWord = entity.get(i).getWord();
                temp.add(featureWord);
                int tempNumber = doc.size();
                for (int j = 0; j < doc.size(); j++)
                {
                    if (!doc.get(j).equals(featureWord)) {
                    	tempNumber--;
                    }
                }
                if (tempNumber == 0) {
                	doc.add(featureWord);
                	//System.out.println("增加成功:" + featureWord);
                }
            }
        }
        
        return temp;
    }
}
