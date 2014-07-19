package info.inpureprojects.core.API.Events;

import java.util.HashMap;

/**
 * Created by den on 7/18/2014.
 */
public class EventStartLoad extends INpureEvent {

    private HashMap<String, HashMap> map = new HashMap();

    public EventStartLoad() {
    }

    public HashMap<String, HashMap> getMap() {
        return map;
    }
}
