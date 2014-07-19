package info.inpureprojects.core.API.Events;

import java.util.HashMap;

/**
 * Created by den on 7/18/2014.
 */
public class EventSaveComplete extends INpureEvent {

    private HashMap<String, HashMap> map = new HashMap();

    public EventSaveComplete(HashMap<String, HashMap> map) {
        this.map = map;
    }

    public HashMap<String, HashMap> getMap() {
        return map;
    }
}
