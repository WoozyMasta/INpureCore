package info.inpureprojects.core.Utils.Loggers;

import com.google.common.eventbus.EventBus;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.message.Message;

/**
 * Created by den on 11/7/2014.
 */
public class EventFilter implements Filter {

    private EventBus bus = new EventBus();

    public EventBus getBus() {
        return bus;
    }

    @Override
    public Result getOnMismatch() {
        return Result.NEUTRAL;
    }

    @Override
    public Result getOnMatch() {
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, String msg, Object... params) {
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(LogEvent event) {
        EventFMLMessage evt = new EventFMLMessage(event.getLevel(), event.getMessage().getFormattedMessage());
        this.getBus().post(evt);
        if (evt.isCanceled()) {
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }
}
