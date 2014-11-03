package info.inpureprojects.core.Utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by den on 11/2/2014.
 */
public class FilterLogger extends FakeLogger {

    public static final Logger log = LogManager.getLogger("INpureLogFilter");
    private Logger original;
    private ArrayList<Filter> filters = new ArrayList();
    private int blockedMessageCount = 0;
    private HashMap<Level, Integer> levelCounts = new HashMap();

    public FilterLogger(Logger fmlLogger) {
        this.original = fmlLogger;
    }

    public void registerFilter(Filter f) {
        filters.add(f);
        FilterLogger.log.info("Registered filter on channel " + original.getName() + " of type " + f.getClass().getName());
    }

    @Override
    public void log(Level level, String msg) {
        boolean display = true;
        for (Filter f : filters) {
            if (!f.display(msg)) {
                display = false;
                break;
            }
        }
        if (display) {
            this.original.log(level, msg);
        } else {
            blockedMessageCount++;
        }
        if (!levelCounts.containsKey(level)) {
            levelCounts.put(level, 0);
        }
        levelCounts.put(level, (levelCounts.get(level).intValue() + 1));
    }

    public void report() {
        log.info(String.valueOf(this.blockedMessageCount) + " messages suppressed from logger: " + this.original.getName());
        if (!levelCounts.isEmpty()) {
            log.info("Logger Stats:");
            for (Level l : levelCounts.keySet()) {
                log.info(l.toString() + ": " + levelCounts.get(l));
            }
        }
    }

    public static class Filter {

        private String searchFor;

        public Filter(String searchFor) {
            this.searchFor = searchFor;
        }

        public boolean display(String msg) {
            return !msg.contains(this.searchFor);
        }
    }
}
