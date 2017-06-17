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
 *
 * TermWeight.
 *
 * @version 1.0 2017撟�4��27��
 * @author NCKU WMMKS LAB
 *
 */
public class ReadTermWeightDic {

    /**
     * Storage TermWeight.
     */
    private static Map<String, Double> map = new HashMap<String, Double>();

    /**
     * Constructor.
     * @throws IOException IOException
     */
    public ReadTermWeightDic() throws IOException {
        File file = new File(".");
        String path = file.getCanonicalPath();
        // Set File Name
        String fileSeparator = System.getProperty("file.separator");
        String fileName = path + fileSeparator + "\\src\\main\\resources\\TermWeight.txt";
        InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
        @SuppressWarnings("resource")
        BufferedReader read = new BufferedReader(isr);
        String str;
        while ((str = read.readLine()) != null) {
            String[] termWeight = str.split("\\,");
            map.put(termWeight[0], Double.parseDouble(termWeight[1]));
        }
    }

    /**
     *
     * @param word Compare word
     * @return highest weight
     */
    public String getWord(final ArrayList<String> word) {
        String max = null;
        for (int i = 0; i + 1  < word.size(); i++) {
        	try {
            max = word.get(i);
            if (map.get(word.get(i + 1)) >= map.get(max)) {
                max = word.get(i + 1);
            }
            } catch(Exception e) {
            	System.out.println("this word not exist");
            }
        }
        return max;
    }
}