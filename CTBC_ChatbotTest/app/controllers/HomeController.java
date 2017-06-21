package controllers;

import play.mvc.*;

import java.io.IOException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private static BM25 question=null;
    //private static WmmksIntentProducer wmmksIntentProducer;
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("Welcome ~~~~");
    }


    public Result WMMKSRespond(String sentence) {
        //Initial
        if(question==null)
        {
            try {
                question=new BM25();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Ajax Authentication
        try {
            response().setHeader("Access-Control-Allow-Origin", "*");
            response().setHeader("Allow", "*");
            response().setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
            String str = question.rankBM25(sentence);
            return ok(str);
        } catch (Exception e) {
            return ok("WMMKSRESPOND Error");
        }
    }

}
