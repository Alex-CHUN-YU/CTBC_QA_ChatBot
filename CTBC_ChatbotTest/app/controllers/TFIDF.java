package controllers;

import synonym.SynonymProvider;
import synonym.SynonymProviderImpl;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * TFIDF TEST: 由於TestFile.txt裡面總共73句(每一句視為doc計算IDF)(整個文件視為一個doc計算TF).
 *
 * @version 1.0 2017年5月18日
 * @author ALEX-CHUN-YU
 *
 */
public class TFIDF {

    /**
     * @param tfDemoDocument  list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(final ArrayList<String> tfDemoDocument, final String term) {
        double result = 0;
        for (String word : tfDemoDocument) {
            if (term.equalsIgnoreCase(word)) {
                result++;
            }
        }
        return result / tfDemoDocument.size();
    }

    /**
     * @param corpus list of list of strings represents the DataSet
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(final ArrayList<ArrayList<String>> corpus, final String term) {
        double count = 0;
        for (ArrayList<String> idfdoc : corpus) {
            for (String word : idfdoc) {
                if (term.equalsIgnoreCase(word)) {
                    count++;
                    break;
                }
            }
        }
        //count + 1 avoid infinity
        return Math.log(corpus.size()/ (count));
    }

    /**
     * @param tfidfdoc  a text document
     * @param corpus corpus
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(final ArrayList<String> tfidfdoc, final ArrayList<ArrayList<String>> corpus,
            final String term) {
        return tf(tfidfdoc, term) * idf(corpus, term);
    }

    /**
     * Corpus.
     */
    private static ArrayList<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();

    /**
     * Doc Of Corpus.
     */
    private ArrayList<String> doc = new ArrayList<String>();

    /**
     * Test Document(整個文件視為一個doc計算TF).
     */
    private static ArrayList<String> demoDocument = new ArrayList<String>();

    /**
     * Term Weight Produce.
     * @throws IOException except
     */
    public void termWeightProduce() throws IOException {

        /**
         * Produce Dictionary
         * @throws IOException
         */
            File file = new File(".");
            String path = file.getCanonicalPath();
            // Set File Name
            String fileSeparator = System.getProperty("file.separator");
            String fileName = path + fileSeparator + "TestFile.txt";
            InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
            BufferedReader read = new BufferedReader(isr);
            String str;
            DA entity = new DA();
            //data into ArryList
            while ((str = read.readLine()) != null) {
                entity.ner(str);
                doc = new ArrayList<String>();
                //每句話，NER後的 QW ACT TARGET FEATURE
                //System.out.println(entity.getTermProduce());
                /*ArrayList<String> template = new ArrayList<>(
                            Arrays.asList(entity.getTermProduce().split("\\+")));*/
                doc = entity.getDoc();
                System.out.println(str + " doc:" + doc);
                //將這份文件視為Corpus給予TF運算
                demoDocument.addAll(doc);
                //將這份文件視為Corpus給予IDF運算
                documentList.add(doc);
            }
            String termWeight  = path + fileSeparator + "\\src\\main\\resources\\TermWeight.txt";
            FileWriter fw = new FileWriter(termWeight);
            //calculate TF or IDF
            TFIDF calculator = new TFIDF();
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
                //TF 疑問QW
                if (entityID[i].equals("A")) {
                    System.out.println(synonym[j] + "," + calculator.tf(demoDocument, synonym[j]));
                    fw.write(synonym[j] + "," + calculator.tf(demoDocument, synonym[j]) + "\n");
                }
                //IDF 動作Act
                if (entityID[i].equals("B")) {
                    System.out.println(synonym[j] + "," + calculator.idf(documentList, synonym[j]));
                    fw.write(synonym[j] + "," + calculator.idf(documentList, synonym[j]) + "\n");
                }
                //IDF 目標Target
                if (entityID[i].equals("C")) {
                    System.out.println(synonym[j] + "," + calculator.idf(documentList, synonym[j]));
                    fw.write(synonym[j] + "," + calculator.idf(documentList, synonym[j]) + "\n");
                }
                /*
                //TF-IDF(暫且不用到)
                if (entityID[i].equals("D")) {

                }*/
            }
            }
            // Close Writer
            fw.flush();
            fw.close();
            // Close Reader
            isr.close();
            read.close();
    }

}