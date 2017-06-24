package CTBC_Main;

import java.io.IOException;

/**
 *
 * Write down the class description here.
 *
 * @version 1.0 2017年5月20日
 * @author WMMKS LAB
 *
 */
public class TermWeightProduce {

    /**
     * Term Weight Produce Test.
     * @param args default
     */
    //
    public static void main(final String[] args) {
        TFIDF rd = new TFIDF();
        try {
            rd.termWeightProduce();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
