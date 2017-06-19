/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.idv.ken.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 代表盧老師課程中的事件這個概念的類別.
 * @author ken
 */
public class Event {
    private final List<CtuTerm> components = new LinkedList<>();
    
    private Event() {
        
    }
    
    /**
     * 用一個CtuTerm instance來建立一個事件.
     * @param term a CtuTerm instance
     * @return an Event instance
     */
    public static Event create(CtuTerm term) {
        Event instance = new Event();
        instance.components.add(term);
        return instance;
    }
    
    /**
     * 串接一個CtuTerm instance到一個已存在的事件上.
     * @param term a CtuTerm instance
     * @return an Event instance
     */
    public Event append(CtuTerm term) {
        this.components.add(term);
        return this;
    }
    
    /**
     * 產生一個只有動詞和受詞的簡單事件.
     * @return a String which represents a simple event
     */
    public String toSimpleEvent() {
        Optional<CtuTerm> verb, obj;        
        verb = components.stream().filter(c -> c.isVerb()).findFirst();
        obj = components.stream().filter(c -> c.isNoun()).findFirst();
        StringBuilder builder = new StringBuilder();
        if(verb.isPresent()) builder.append(verb.get().getTerm());
        if(obj.isPresent()) builder.append(obj.get().getTerm());
        return builder.toString();
    }
    
    /**
     * 產生一個有動詞,受詞及其他修飾用詞的完整事件.
     * @return a String which represents a complete event
     */
    public String toCompleteEvent() {
        StringBuilder builder = new StringBuilder();
        components.stream().filter(c -> c.isVerb() || c.isNoun() || c.isDeterminer() || c.isMeasure()).forEach(c -> builder.append(c.getTerm()));
        return builder.toString();
    }
}
