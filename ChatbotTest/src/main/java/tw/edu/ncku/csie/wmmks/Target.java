package tw.edu.ncku.csie.wmmks;

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
     * ������雿輻�函���斗�瑟��璅�.
     */
    private static int       flag, skip, comma = 0;
    /**
     * �詨��閰���閰��賂�瘙箏�vo�臬�阡����.
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
        //vo瘙箏��臬�衣�肖opair ��閮�1=open.
        int vo = 1;
        ArrayList<POS.Tuple<String, String>> w = pos;
        ArrayList<String> target = new ArrayList<String>();
        ArrayList<String> fe = new ArrayList<String>();
        // �脣�交�詨��閰���閰��貉艘��
        for (int i = 0; i < w.size(); i++) {
            count(w.get(i).getPos());
        }
        for (int i = 0; i < w.size() - 1; i++) {
            delCount(w.get(i).getPos(), w.get(i + 1).getPos(), i, w.size());
        }
        // �寞����閰���閰��豢捱摰��臬�阡����VOPAIR�交��
        if (countn == 1 && countv == 1) {
            vo = 0;
        }
        // ��憪��斗�瑁�
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
                        skip = 3;
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
                    // ��瘨��芸��頧�撣喃誨蝜� �芸��頧�撣喃誨蝜�
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
                        skip = 2;
                        flag = 1;
                    }
                }
                if (i + 2 < w.size()) {
                    if (w.get(i).getPos().equals("VC") && (w.get(i + 1).getPos().equals("Na"))
                            && (w.get(i + 2).getPos().equals("Na")) && vo == 1) {
                        target.add(w.get(i).getWord() + w.get(i + 1).getWord()
                                + w.get(i + 2).getWord());
                        skip = 3;
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
        //靽∠�鞎�
        if (i.equals("VC")) {
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
        // for �桅����閰�
        if (((i.equals("A")) && (ii.equals("Na") || ii.equals("Nb")))) {
            return true;
        }
        if ((i.equals("Na") || i.equals("Nv")) && ((ii.equals("Na")) || (ii.equals("Nv")))) {
            return true;
        }
        // �芸�� ���芷���閰晞���芾���閰晞��敺抵店����蝣潭���乓��SIM�∼��皞Ｙ像甈�
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
        // �����暸��
        if (i.equals("VD") && ii.equals("Na")) {
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
        // ��摰�頧���(VE)(Nv/VC)��甈�憭望��(VE)(VH)
        if ((i.equals("VE")) && ((ii.equals("VC")) || (ii.equals("Nv")) || (ii.equals("VH")))) {
            return true;
        }
        // ��蝘�鈭�摰�(VB)(Na)
        if (i.equals("VB") && (ii.equals("Na"))) {
            return true;
        }
        // �典��憭�
        if (i.equals("P") && (ii.equals("Nep") || ii.equals("Nc"))) {
            return true;
        }
        // �嗡���摰�
        if (i.equals("Neqa") && (ii.equals("Na"))) {
            return true;
        }
        // ����敺� ��鞈潸眺敺���蝜單狡敺�
        if ((i.equals("VH") || i.equals("VC") || i.equals("VA")) && (ii.equals("Ng"))) {
            return true;
        }
        // 撌脩�摰�����撌脩��唾�
        if ((i.equals("D")) && (ii.equals("VC") || ii.equals("VF"))) {
            return true;
        }
        // 銝��拍��
        if ((i.equals("D")) && (ii.equals("VJ"))) {
            return true;
        }
        // �啁��摰Ｘ��
        if ((i.equals("Nc")) && (ii.equals("Na"))) {
            return true;
        }
        // �質���
        if ((i.equals("VE")) && (ii.equals("Na"))) {
            return true;
        }
        // 擗�憿�銝�頞�
        if ((i.equals("Na")) && (ii.equals("VH"))) {
            return true;
        }
        // 撣喳�桀����
        if ((i.equals("Na") && ii.equals("D"))) {
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
        // ��蝣澆�舀��(Na)(D)(VC)
        if ((i.equals("Na")) && (ii.equals("D")) && (iii.equals("VC"))) {
            return true;
        }
        if ((i.equals("Neu")) && (ii.equals("Na")) && (iii.equals("VA"))) {
            return true;
        }
        // 蝬脩�蝺�銝�蝜單狡/蝺�銝�蝜單狡(Na)(Ncd)(VA)
        if ((i.equals("Na")) && (ii.equals("Ncd")) && (iii.equals("VA"))) {
            return true;
        }
        // �餉店靽�撖�(VCL)(Na)(Nv/VA)
        if ((i.equals("VCL")) && (ii.equals("Na")) && ((iii.equals("Nv")) || (iii.equals("VA")))) {
            return true;
        }
        // �啁��憭批恥��(Nc)(VH)(N)
        if ((i.equals("Nc")) && (ii.equals("VH")) && (iii.equals("N"))) {
            return true;
        }
        // ��璈��豢��瞍恍��(VC)(Na)(Na)
        /*
         * if((i.equals("VC"))&&(w.get(i+1).pos.equals("Na"))&&(w.get(i+2). pos.equals("Na"))){
         * return true; }
         */
        // 閰曹葉���� (Na)(Ng)(VC)
        if ((i.equals("Na")) && (ii.equals("Ng")) && (iii.equals("VC"))) {
            return true;
        }
        // �啁��憭批恥��(Nc)(VH)(Na)
        if ((i.equals("Nc")) && (ii.equals("VH")) && (iii.equals("Na"))) {
            return true;
        }
        // for ����wifi��
        if ((i.equals("Nc")) && (ii.equals("FW")) && ((iii.equals("VC")) || (iii.equals("VH")))) {
            return true;
        }
        // �澆��閰�
        if ((i.equals("VD")) && (ii.equals("P")) && (iii.equals("Na"))) {
            return true;
        }
        // �交�砌�蝬脫憤��
        if ((i.equals("Nc")) && (ii.equals("VA")) && (iii.equals("VA"))) {
            return true;
        }
        // 憿�擃�靽∠�券�摨�
        if ((i.equals("Nb")) && (ii.equals("Na")) && (iii.equals("Na"))) {
            return true;
        }
        // 蝝�摰�撣喳�桀����
        if ((i.equals("VE")) && (ii.equals("Na")) && (iii.equals("Na") || iii.equals("D"))) {
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
        // 瞍恍��銵���銝�蝬脫�亦���
        if (((i.equals("VA")) || (i.equals("Nv"))) && (ii.equals("Na")) && (iii.equals("Nv"))
                && (iv.equals("Na"))) {
            return true;
        }
        // �亦���瞍恍��
        if ((i.equals("Nc")) && (ii.equals("VD")) && (iii.equals("Na"))
                && ((iv.equals("VA") || iv.equals("Nv")))) {
            return true;
        }
        // �澆��閰梢����
        if ((i.equals("VD")) && (ii.equals("P")) && (iii.equals("Na")) && (iv.equals("Na"))) {
            return true;
        }
        // 蝺�銝�������蝝�
        if ((i.equals("Na")) && (ii.equals("Ncd")) && (iii.equals("Na")) && (iv.equals("Nv"))) {
            return true;
        }
        // 蝝�摰��桃�����
        if ((i.equals("VE")) && (ii.equals("A")) && (iii.equals("Na")) && (iv.equals("Na"))) {
            return true;
        }
        // �瑟��敺芰�啗���撠�獢�
        if ((i.equals("Nd")) && (ii.equals("Na")) && (iii.equals("Nv")) && (iv.equals("Na"))) {
            return true;
        }
        // ����銋��桃�
        if ((i.equals("Na")) && (ii.equals("DE")) && (iii.equals("A")) && (iv.equals("Na"))) {
            return true;
        }
        // ��銝�甈⊥�折�����暸��撖�蝣潦��
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
        if (i.equals("VA") && (ii.equals("VH"))) { // 蝜單狡����
            return true;
        }
        if ((i.equals("VHC") && ii.equals("VB"))) { // 蝯�����蝺�
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