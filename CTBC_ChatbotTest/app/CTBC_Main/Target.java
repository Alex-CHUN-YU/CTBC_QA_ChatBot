package CTBC_Main;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Get target and feature.
 *
 * @version 1.0 2017年4月30日
 * @author NCKU LAB
 *
 */
public class Target {
    /**
     * 處理時使用的判斷旗標.
     */
    private static int       flag, skip, comma = 0;
    /**
     * 數動詞名詞數，決定vo是否開啟.
     */
    private static int       countn = 0, countv = 0;
    /**
     * For Skip using.
     */
    private static final int IV     = 4;
    /**
     * For Skip using.
     */
    private static final int V      = 5;
    /**
     * Analyze sentence and pick all possible keywords for Target or Feature.
     *
     * @param pos sentence's part of speech result form CKIP
     * @throws IOException if anything goes wrong when get Target or Feature.
     * @return possible Target and Feature
     */
    public static ArrayList<String> getTarget(final ArrayList<POS.Tuple<String, String>> pos)
            throws IOException {
        //vo決定是否用vopair 預設1=open.
        int vo = 1;
        ArrayList<POS.Tuple<String, String>> w = pos;
        ArrayList<String> target = new ArrayList<String>();
        ArrayList<String> fe = new ArrayList<String>();
        // 進入數動詞名詞數迴圈
        for (int i = 0; i < w.size(); i++) {
            count(w.get(i).getPos());
        }
        for (int i = 0; i < w.size() - 1; i++) {
            delCount(w.get(i).getPos(), w.get(i + 1).getPos(), i, w.size());
        }
        // 根據動詞名詞數決定是否開啟VOPAIR查找
        if (countn == 1 && countv == 1) {
            vo = 0;
        }
        // 開始判斷詞
        for (int i = 0; i < w.size(); i++) {
            if (skip == 0) {
                if (i + 1 < w.size()) {
                    if (doubleTarget(w.get(i).getPos(), w.get(i + 1).getPos())) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord());
                        skip = 1;
                    }
                    if (doubleFeature(w.get(i).getPos(), w.get(i + 1).getPos())) {
                        fe.add(w.get(i).getWord() + w.get(i + 1).getWord());
                        skip = 1;
                    }
                }
                if (i + 2 < w.size()) {
                    if (tripleTarget(w.get(i).getPos(), w.get(i + 1).getPos(),
                            w.get(i + 2).getPos())) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord());
                        skip = 1;
                    }
                }
                if (i + 3 < w.size()) {
                    if (quadrupleTarget(w.get(i).getPos(), w.get(i + 1).getPos(),
                            w.get(i + 2).getPos(), w.get(i + 3).getPos())) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord() + w.get(i + 3).getWord());
                        skip = IV;
                    }
                }
                if (i + IV < w.size()) {
                    // *FW128*EZ Call or *FW128*
                    if ((w.get(i).getPos().equals("FW")) && (w.get(i + 1).getPos().equals("Neu"))
                            && (w.get(i + 2).getPos().equals("FW"))
                            && (w.get(i + 3).getPos().equals("FW"))) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord() + w.get(i + 3).getWord());
                        skip = IV;
                    } else if ((w.get(i).getPos().equals("FW"))
                            && (w.get(i + 1).getPos().equals("Neu"))
                            && (w.get(i + 2).getPos().equals("FW"))) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord());
                        skip = 3;
                    }
                    // 取消自動轉帳代繳 自動轉帳代繳
                    if ((w.get(i).getPos().equals("VC")) && (w.get(i + 1).getPos().equals("VH"))
                            && (w.get(i + 2).getPos().equals("Nv"))
                            && (w.get(i + 3).getPos().equals("Na"))
                            && (w.get(i + IV).getPos().equals("VD"))) {
                        target.add(
                                w.get(i).getWord() + w.get(i + 1).getWord() + w.get(i + 2).getWord()
                                        + w.get(i + 3).getWord() + w.get(i + IV).getWord());
                        skip = V;
                    } else if ((w.get(i).getPos().equals("VH"))
                            && (w.get(i + 1).getPos().equals("Nv"))
                            && (w.get(i + 2).getPos().equals("Na"))
                            && (w.get(i + 3).getPos().equals("VD"))) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord() + w.get(i + 3).getWord());
                        skip = IV;
                    }
                }
                if (i + 1 < w.size()) {
                    if ((w.get(i).getPos().equals("Neu")) && (w.get(i + 1).getPos().equals("FW"))
                            && (skip < 3)) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord());
                        skip = 2;
                    }
                }
                if (singleTarget(w.get(i).getPos())) {
                    target.add(w.get(i).getWord());
                }
                if (i + 1 < w.size() && vo == 1) {
                    if (doubleVerbTarget(w.get(i).getPos(), w.get(i + 1).getPos())) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord());
                        skip = 1;
                        flag = 1;
                    }
                }
                if (i + 2 < w.size()) {
                    if (w.get(i).getPos().equals("VC") && (w.get(i + 1).getPos().equals("Na"))
                            && (w.get(i + 2).getPos().equals("Na")) && vo == 1) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord());
                        skip = 1;
                        flag = 1;
                    }
                }
                if (i + 1 < w.size()) {
                    if ((w.get(i).getPos().equals("VC") || w.get(i).getPos().equals("VJ")
                            || w.get(i).getPos().equals("VF"))
                            && !(w.get(i + 1).getPos().equals("VB"))) {
                        for (int j = i + 1; j < w.size(); j++) {
                            if (w.get(j).getPos().equals("COMMACATEGORY")) {
                                comma = 1;
                            }
                            if ((w.get(j).getPos().equals("Na")
                                    || w.get(j).getPos().equals("Nv")
                                    || w.get(j).getPos().equals("VE")) && flag == 0 && comma == 0
                                    && vo == 1) {
                                target.add(w.get(i).getWord() + w.get(j).getWord());
                                flag = 1;
                            }
                        }
                    }
                }
            }
            flag = 0;
            comma = 0;
            if (skip > 0) {
                skip = skip - 1;
            }
        }
        countn = 0;
        countv = 0;
        skip = 0;
        for (int i = 0; i < fe.size(); i++) {
            target.add(fe.get(i));
        }
        //System.out.println(target);
        return target;
    }
    /**
     * Analyze singleTarget case.
     *
     * @param i first POS of word.
     * @return true or false
     */
    private static Boolean singleTarget(final String i) {
        if (i.equals("Na") || (i.equals("Nv") || i.equals("FW")) && skip == 0) {
            return true;
        }
        if (i.equals("Nb") || i.equals("Nc")) {
            return true;
        }
        //信福貸
        if (i.equals("VC")) {
            return true;
        }
        //刷卡
        if (i.equals("VA")) {
            return true;
        }
        if (i.equals("N")) {
            return true;
        }
        return false;
    }
    /**
     * Analyze doubleTarget case.
     *
     * @param i first POS of word.
     * @param ii second POS of word.
     * @return true or false
     */
    private static Boolean doubleTarget(final String i, final String ii) {
        // for 普通名詞
        if (((i.equals("A")) && (ii.equals("Na") || ii.equals("Nb")))) {
            return true;
        }
        if ((i.equals("Na") || i.equals("Nv")) && ((ii.equals("Na")) || (ii.equals("Nv")))) {
            return true;
        }
        // 自停 、自願停話、自行停話、復話、號碼攜入、SIM卡、溢繳款
        if (i.equals("P") && ii.equals("VHC")) {
            return true;
        }
        if (i.equals("VL") && ii.equals("VH")) {
            return true;
        }
        if (i.equals("D") && ii.equals("VH")) {
            return true;
        }
        if (i.equals("VHC") && ii.equals("Na")) {
            return true;
        }
        if (i.equals("Na") && (ii.equals("VC") || ii.equals("VA"))) {
            return true;
        }
        if (i.equals("FW") && ii.equals("Na")) {
            return true;
        }
        if (i.equals("VH") && ii.equals("VA")) {
            return true;
        }
        // 預借現金
        if (i.equals("VD") && ii.equals("Na")) {
            return true;
        }
        // 日限額
        if (i.equals("Nf") && ii.equals("Na")) {
            return true;
        }
        return false;
    }
    /**
     * Analyze doubleFeature case.
     *
     * @param i first POS of word.
     * @param ii second POS of word.
     * @return true or false
     */
    private static Boolean doubleFeature(final String i, final String ii) {
        // 指定轉接(VE)(Nv/VC)授權失敗(VE)(VH)
        if ((i.equals("VE")) && ((ii.equals("VC")) || (ii.equals("Nv")) || (ii.equals("VH")))) {
            return true;
        }
        // 退租事宜(VB)(Na)
        if (i.equals("VB") && (ii.equals("Na"))) {
            return true;
        }
        // 在國外
        if (i.equals("P") && (ii.equals("Nep") || ii.equals("Nc"))) {
            return true;
        }
        // 其他國家
        if (i.equals("Neqa") && (ii.equals("Na"))) {
            return true;
        }
        // 成功後 、購買後、繳款後
        if ((i.equals("VH") || i.equals("VC") || i.equals("VA")) && (ii.equals("Ng"))) {
            return true;
        }
        // 已經完成、已經申請
        if ((i.equals("D")) && (ii.equals("VC") || ii.equals("VF"))) {
            return true;
        }
        // 不適用
        if ((i.equals("D")) && (ii.equals("VJ"))) {
            return true;
        }
        // 台灣客服
        if ((i.equals("Nc")) && (ii.equals("Na"))) {
            return true;
        }
        // 聽語音
        if ((i.equals("VE")) && (ii.equals("Na"))) {
            return true;
        }
        // 餘額不足
        if ((i.equals("Na")) && (ii.equals("VH"))) {
            return true;
        }
        // 帳單分期
        if ((i.equals("Na") && ii.equals("D"))) {
            return true;
        }
        // 24小時
        if ((i.equals("Neu") && ii.equals("Nf"))) {
            return true;
        }
        return false;
    }
    /**
     * Analyze tripleTarget case.
     *
     * @param i first POS of word.
     * @param ii second POS of word.
     * @param iii third POS of word.
     * @return true or false
     */
    private static Boolean tripleTarget(final String i, final String ii,
            final String iii) {
        // 號碼可攜(Na)(D)(VC)
        if ((i.equals("Na")) && (ii.equals("D")) && (iii.equals("VC"))) {
            return true;
        }
        if ((i.equals("Neu")) && (ii.equals("Na")) && (iii.equals("VA"))) {
            return true;
        }
        // 網站線上繳款/線上繳款(Na)(Ncd)(VA)
        if ((i.equals("Na")) && (ii.equals("Ncd")) && (iii.equals("VA"))) {
            return true;
        }
        // 去話保密(VCL)(Na)(Nv/VA)
        if ((i.equals("VCL")) && (ii.equals("Na")) && ((iii.equals("Nv")) || (iii.equals("VA")))) {
            return true;
        }
        // 台灣大客戶(Nc)(VH)(N)
        if ((i.equals("Nc")) && (ii.equals("VH")) && (iii.equals("N"))) {
            return true;
        }
        // 手機數據漫遊(VC)(Na)(Na)
        /*
         * if((i.equals("VC"))&&(w.get(i+1).pos.equals("Na"))&&(w.get(i+2). pos.equals("Na"))){
         * return true; }
         */
        // 話中插接 (Na)(Ng)(VC)
        if ((i.equals("Na")) && (ii.equals("Ng")) && (iii.equals("VC"))) {
            return true;
        }
        // 台灣大客戶(Nc)(VH)(Na)
        if ((i.equals("Nc")) && (ii.equals("VH")) && (iii.equals("Na"))) {
            return true;
        }
        // for 國際wifi通
        if ((i.equals("Nc")) && (ii.equals("FW")) && ((iii.equals("VC")) || (iii.equals("VH")))) {
            return true;
        }
        // 發受話
        if ((i.equals("VD")) && (ii.equals("P")) && (iii.equals("Na"))) {
            return true;
        }
        // 日本上網漫遊
        if ((i.equals("Nc")) && (ii.equals("VA")) && (iii.equals("VA"))) {
            return true;
        }
        // 額高信用額度
        if ((i.equals("Nb")) && (ii.equals("Na")) && (iii.equals("Na"))) {
            return true;
        }
        // 約定帳單分期
        if ((i.equals("VE")) && (ii.equals("Na")) && (iii.equals("Na") || iii.equals("D"))) {
            return true;
        }
        // 信用卡權益手冊
        if ((i.equals("Na")) && (ii.equals("Na")) && (iii.equals("Na"))) {
            return true;
        }
        // 語音回覆系統
        if ((i.equals("Na")) && (ii.equals("VD")) && (iii.equals("Na"))) {
            return true;
        }
        return false;
    }
    /**
     * Analyze quadrupleTarget case.
     *
     * @param i first POS of word.
     * @param ii second POS of word.
     * @param iii third POS of word.
     * @param iv fourth POS of word.
     * @return true or false
     */
    private static Boolean quadrupleTarget(final String i, final String ii, final String iii,
            final String iv) {
        // 漫遊行動上網日租型
        if (((i.equals("VA")) || (i.equals("Nv"))) && (ii.equals("Na")) && (iii.equals("Nv"))
                && (iv.equals("Na"))) {
            return true;
        }
        // 日租型漫遊
        if ((i.equals("Nc")) && (ii.equals("VD")) && (iii.equals("Na"))
                && ((iv.equals("VA") || iv.equals("Nv")))) {
            return true;
        }
        // 發受話限制
        if ((i.equals("VD")) && (ii.equals("P")) && (iii.equals("Na")) && (iv.equals("Na"))) {
            return true;
        }
        // 線上門號預約
        if ((i.equals("Na")) && (ii.equals("Ncd")) && (iii.equals("Na")) && (iv.equals("Nv"))) {
            return true;
        }
        // 約定單筆分期
        if ((i.equals("VE")) && (ii.equals("A")) && (iii.equals("Na")) && (iv.equals("Na"))) {
            return true;
        }
        // 長期循環轉換專案
        if ((i.equals("Nd")) && (ii.equals("Na")) && (iii.equals("Nv")) && (iv.equals("Na"))) {
            return true;
        }
        // 分期之單筆
        if ((i.equals("Na")) && (ii.equals("DE")) && (iii.equals("A")) && (iv.equals("Na"))) {
            return true;
        }
        // 「一次性預借現金密碼」
        if ((i.equals("Na")) && (ii.equals("VD")) && (iii.equals("Na")) && (iv.equals("Na"))) {
            return true;
        }
        return false;
    }
    /**
     * Analyze doubleVerbTarget case.
     *
     * @param i first POS of word.
     * @param ii second POS of word.
     * @return true or false
     */
    private static Boolean doubleVerbTarget(final String i, final String ii) {
        if (i.equals("VCL") && (ii.equals("Nc") || ii.equals("Nb"))) {
            return true;
        }
        if (i.equals("VE") && (ii.equals("Nv"))) {
            return true;
        }
        if (i.equals("VC") && (ii.equals("VC"))) {
            return true;
        }
        if (i.equals("VA") && (ii.equals("VH"))) { // 繳款成功
            return true;
        }
        if ((i.equals("VHC") && ii.equals("VB"))) { // 結束連線
            return true;
        }
        return false;
    }
    /**
     * count.
     *
     * @param i POS of word.
     */
    private static void count(final String i) {
        if (i.equals("Na") || (i.equals("Nb")) || (i.equals("Nd")) || (i.equals("Nv"))) {
            countn = countn + 1;
        }
        if (i.equals("VA") || (i.equals("VB")) || (i.equals("VC")) || (i.equals("VD"))
                || i.equals("VE") || (i.equals("VH")) || (i.equals("VJ")) || (i.equals("VF"))) {
            countv = countv + 1;
        }
    }
    /**
     * delCount.
     *
     * @param i first POS of word.
     * @param ii second POS of word.
     * @param w which word in for loop.
     * @param size size of sentence.
     */
    private static void delCount(final String i, final String ii, final int w, final int size) {
        if (w + 1 < size) {
            if ((i.equals("Na") && ii.equals("Na"))) {
                countn = countn - 1;
            }
        }
    }
}