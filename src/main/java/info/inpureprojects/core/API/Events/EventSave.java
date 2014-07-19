package info.inpureprojects.core.API.Events;

import java.util.HashMap;

/**
 * Created by den on 7/18/2014.
 */
public class EventSave extends INpureEvent {
    private HashMap<String, HashMap> map = new HashMap();

    public void save(String tag, HashMap m) {
        map.put(tag, m);
    }

    public HashMap<String, HashMap> getMap() {
        return map;
    }
}
