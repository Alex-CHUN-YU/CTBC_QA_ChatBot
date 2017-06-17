package tw.edu.ncku.csie.wmmks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The BM25 weighting scheme, often called Okapi weighting.
 */
public class BM25 {

    /**
     * Free parameter, usually chosen as k1 = 2.0.
     */
    private double k1;

    /**
     * Free parameter, usually chosen as b = 0.75.
     */
    private double b;

    /**
     * Default constructor with k1 = 1.2, b = 0.75.
     * @throws IOException
     */
    public BM25() throws IOException {
        this(1.2, 0.75);
    }

    /**
     * Constructor.
     *
     * @param k1 is a positive tuning parameter that calibrates
     * the document term frequency scaling. A k1 value of 0 corresponds to a
     * binary model (no term frequency), and a large value corresponds to using
     * raw term frequency.
     * @param b b is another tuning parameter which determines
     * the scaling by document length: b = 1 corresponds to fully scaling the
     * term weight by the document length, while b = 0 corresponds to no length
     * normalization.
     .
     * @throws IOException */
    public BM25(final double k1, final double b) throws IOException {
        if (k1 < 0) {
            throw new IllegalArgumentException("Negative k1 = " + k1);
        }
        if (b < 0 || b > 1) {
            throw new IllegalArgumentException("Invalid b = " + b);
        }
        this.k1 = k1;
        this.b = b;
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
            int questionnumber = 1;
            //data into ArryList
            while (((str = read.readLine()) != null)) {
                    entity.ner(str);
                    doc = new ArrayList<String>();
                    doc = entity.getDoc();
                    //撠���隞賣��隞嗉��慢orpus蝯虫�TF��蝞�
                    demoDocument.addAll(doc);
                    //撠���隞賣��隞嗉��慢orpus蝯虫�IDF��蝞�
                    documentList.add(doc);
                    map.put(questionnumber, str);
                    questionnumber++;
            }
            // Close Reader
            isr.close();
            read.close();
    }

    /**
     * @param tfDocument list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(final ArrayList<String> tfDocument, final String term) {
        double freq = 0;
        double avgDocSize = (demoDocument.size() / documentList.size());
        for (String word : tfDocument) {
            if (term.equalsIgnoreCase(word)) {
                freq++;
            }
        }
        double tf = (freq * (k1 + 1)) / (freq + k1 * (1 - b + b * (tfDocument.size()) / avgDocSize));
        return tf;
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
        return Math.log((corpus.size() - count + 0.5) / (count + 0.5));
    }

    /**
     * rank the score.
     * @param tfdoc Each Document TF
     * @param corpus Corpus
     * @param terms terms of question
     * @return the TF-IDF of term
     */
    public double score(final ArrayList<String> tfdoc, final ArrayList<ArrayList<String>> corpus,
            final ArrayList<String> terms) {
        double sumScore = 0.0;
        for (String term : terms) {
             sumScore += tf(tfdoc, term) * idf(corpus, term);
        }
        return sumScore;
    }

    /**
     * Corpus.
     */
    private static ArrayList<ArrayList<String>> documentList = new ArrayList<ArrayList<String>>();

    /**
     * Doc Of Corpus.
     */
    private static ArrayList<String> doc = new ArrayList<String>();

    /**
     * Terms of Question.
     */
    private static ArrayList<String> terms = new ArrayList<String>();

    /**
     * Test Document(�游����隞嗉��箔���doc閮�蝞�TF).
     */
    private static ArrayList<String> demoDocument = new ArrayList<String>();

    /**
     * Storage question.
     */
    private static Map<Integer, String> map = new HashMap<Integer, String>();

    /**
     * Rank Answer By BM25.
     * @throws IOException except
     * @param question is our question
     */
    public void rankBM25(final String question) throws IOException {
            DA qterm = new DA();
            terms = new ArrayList<String>();
            qterm.ner(question);
            terms = qterm.getDoc();
            int counter = 1;
            int id = 0;
            double score = 0;
            double max = 0;
            for (ArrayList<String> tfdoc : documentList) {
                score = this.score(tfdoc, documentList, terms);
                //System.out.println(counter + "," + score);
                if (score > max) {
                    max = score;
                    id = counter;
                    }
                counter++;
                }
            System.out.println("Your Question is:" + id + "." + map.get(id));
    }
}