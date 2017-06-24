package CTBC_Main;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.HashMap;

/**
 * @author ALEX-CHUN-YU
 */

public class ReadJson {
	
    /**
     * Q&A Pair.
     */
    private static HashMap<String, String> qaRespond = new HashMap<String, String>();
    public static HashMap<String, String> getQARespond() {
        JSONParser parser = new JSONParser();
        String path = "";
        //please change file path
        File file = new File(".");
        try {
            path = file.getCanonicalPath() + "\\CTBC_QA_Crawler\\CTBC_Crawl_Result";
        } catch (Exception e) {
            System.out.print("Error:" + e);
        }

        try {
            Object obj1 = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(path + "//CTBC1.json"), "UTF-8")));
            Object obj2 = parser.parse(new BufferedReader(new InputStreamReader(new FileInputStream(path + "//CTBC2.json"), "UTF-8")));
            JSONArray jsonArray1 = (JSONArray) obj1;
            JSONArray jsonArray2 = (JSONArray) obj2;
            //System.out.println(jsonArray);
            for (int i = 0; i < jsonArray1.size(); i++ ){
            	JSONObject jsonObject = (JSONObject) jsonArray1.get(i);
            	String question = (String) jsonObject.get("question");
            	String answer = (String) jsonObject.get("answer");
            	qaRespond.put(question, answer);
            	//System.out.println("question: " + question);
                //System.out.println("answer: " + answer);
            }
            for (int i = 0; i < jsonArray2.size(); i++ ){
            	JSONObject jsonObject = (JSONObject) jsonArray2.get(i);
            	String question = (String) jsonObject.get("question");
            	String answer = (String) jsonObject.get("answer");
            	qaRespond.put(question, answer);
            	//System.out.println("question: " + question);
                //System.out.println("answer: " + answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return qaRespond;
    }
}