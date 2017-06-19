/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.idv.ken.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to represent a Dialog Act defined by Charles University, Prague.
 * @author ken
 * @deprecated use {@link DialogueAnalysis} instead.
 */
@Deprecated
public class DialogueAct {
    /**
     * 屬於某個句子裡的所有dialogue act item.
     */
    private List<? extends DialogueActItem> dialogueActItems;
    
    public DialogueAct(List<? extends DialogueActItem> dialogueActItems) {
        this.dialogueActItems = new LinkedList<>();
        this.dialogueActItems = dialogueActItems;
    }

    /**
     * @return the dialogueActItems
     */
    public List<? extends DialogueActItem> getDialogueActItems() {
        return dialogueActItems;
    }
    
    /**
     * Get dialogue items of INFORM type.
     * @return a list of DialogueActItem's subclass instances
     */
    public List<? extends DialogueActItem> getInformation() {
        return dialogueActItems.stream().filter(t -> t.getActType() == DialogueActType.INFORM)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    /**
     * Get dialogue items of REQUEST type.
     * @return a list of DialogueActItem's subclass instances 
     */
    public List<? extends DialogueActItem> getRequests() {
        return dialogueActItems.stream().filter(t -> t.getActType() == DialogueActType.REQUEST)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
