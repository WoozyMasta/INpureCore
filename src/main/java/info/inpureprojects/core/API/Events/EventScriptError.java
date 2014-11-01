package info.inpureprojects.core.API.Events;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * Created by den on 11/1/2014.
 */
public class EventScriptError extends Event {

    private Throwable t;

    public EventScriptError(Throwable t) {
        this.t = t;
    }

    public Throwable getT() {
        return t;
    }
}
