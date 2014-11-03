package info.inpureprojects.core.Utils.Events;

import cpw.mods.fml.common.eventhandler.Event;
import org.apache.logging.log4j.Level;

/**
 * Created by den on 11/3/2014.
 */
public class EventFMLMessage extends Event {

    private Level level;
    private String message;

    public EventFMLMessage(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
