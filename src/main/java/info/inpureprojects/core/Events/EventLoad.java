package info.inpureprojects.core.Events;

import info.inpureprojects.core.API.Events.INpureEvent;

import java.util.HashMap;

/**
 * Created by den on 7/18/2014.
 */
public class EventLoad extends INpureEvent {

    private HashMap<String, HashMap> map;

    public EventLoad(HashMap<String, HashMap> map) {
        this.map = map;
    }

    public HashMap load(String tag) {
        return map.get(tag);
    }

}
