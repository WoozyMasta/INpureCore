package info.inpureprojects.core.Utils.Loggers;

import com.google.common.eventbus.EventBus;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;
import info.inpureprojects.core.Utils.Loggers.Fake.FakeLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

/**
 * Created by den on 11/2/2014.
 */
@Deprecated
public class EventLogger extends FakeLogger {

    private Logger original;
    private EventBus bus = new EventBus();

    public EventLogger(Logger fmlLogger) {
        this.original = fmlLogger;
    }

    @Override
    public void log(Level level, String msg) {
        EventFMLMessage evt = new EventFMLMessage(level, msg);
        this.getBus().post(evt);
        if (!evt.isCanceled()) {
            this.original.log(evt.getLevel(), evt.getMessage());
        }
    }

    public EventBus getBus() {
        return bus;
    }
}
