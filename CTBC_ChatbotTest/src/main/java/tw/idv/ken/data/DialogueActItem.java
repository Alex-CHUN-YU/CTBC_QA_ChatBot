/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.idv.ken.data;

/**
 * Class to represent a Dialog Act Item defined by Charles University, Prague.
 * @author ken
 * @deprecated use {@link DialogueAnalysis} instead.
 */
@Deprecated
public class DialogueActItem {
    /**
     * Type of a dialog act.
     */
    private DialogueActType actType;
    /**
     * Name of a dialog slot
     */
    private String slotName;
    /**
     * Value of a dialog value, this is optional in some cases.
     */
    private String slotValue;
    
    /**
     * Non-args constructor of this class.
     */
    public DialogueActItem() {
        
    }
    
    /**
     * Constructor of this class.
     * @param type dialogue act type
     * @param sn slot name
     * @param sv slot value
     */
    public DialogueActItem(DialogueActType type, String sn, String sv) {
        this.actType = type;
        this.slotName = sn;
        this.slotValue = sv;
    }

    /**
     * @return the slotName
     */
    public String getSlotName() {
        return slotName;
    }

    /**
     * @param slotName the slotName to set
     */
    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    /**
     * @return the slotValue
     */
    public String getSlotValue() {
        return slotValue;
    }

    /**
     * @param slotValue the slotValue to set
     */
    public void setSlotValue(String slotValue) {
        this.slotValue = slotValue;
    }

    /**
     * @return the actType
     */
    public DialogueActType getActType() {
        return actType;
    }

    /**
     * @param actType the actType to set
     */
    public void setActType(DialogueActType actType) {
        this.actType = actType;
    }
}
