/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import tw.cheyingwu.ckip.Term;

/**
 * 包裝了cheyingwu ckip client函式庫裡的Term class的自訂類別.
 * @author ken
 */
public class CtuTerm extends Term{
    private final Term term;
    
    public CtuTerm(tw.cheyingwu.ckip.Term term) {
        this.term = term;
    }
    
    /**
     * 是否為動詞
     * @return  true if this is a verb
     */
    public boolean isVerb() {
        return isIntransitiveVerb() || isTransitiveVerb();
    }
    
    /**
     * 是否為不及物動詞.
     * @return true if this is a 不及物動詞
     */
    public boolean isIntransitiveVerb() {
        return term.getTag().equalsIgnoreCase("VA") || term.getTag().equalsIgnoreCase("VE");
    }
    
    /**
     * 是否為及物動詞.
     * @return true if this is a 及物動詞
     */
    public boolean isTransitiveVerb() {
        return term.getTag().matches("VC.*") || term.getTag().equalsIgnoreCase("VJ");
    }
    
    /**
     * 是否為名詞
     * @return  true if this is a noun
     */
    public boolean isNoun() {
        return term.getTag().matches("N[abc]+");
    }
    
    /**
     * 是否為定詞
     * @return true if this is a determiner
     */
    public boolean isDeterminer() {
        return term.getTag().equalsIgnoreCase("Neu");
    }
    
    /**
     * 是否為量詞
     * @return true if this is a determiner
     */
    public boolean isMeasure() {
        return term.getTag().equalsIgnoreCase("Nf");
    }
    
    @Override
    public String getTerm() {
        return term.getTerm();
    }
    
    @Override
    public String getTag() {
        return term.getTag();
    }
}
