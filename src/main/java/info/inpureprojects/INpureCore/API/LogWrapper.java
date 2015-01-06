package info.inpureprojects.INpureCore.API;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.Logger;

import java.io.File;

/**
 * Created by den on 11/2/2014.
 */
public class LogWrapper {

    private Logger log;
    private File debug;

    public LogWrapper(Logger log, File debug) {
        this.log = log;
        this.debug = debug;
        if (this.debug != null) {
            if (this.debug.exists()) {
                this.debug.delete();
            }
        }

    }

    public Logger getLog() {
        return log;
    }

    public String IntArrayToString(int[] array) {
        String s = "";
        for (int i : array) {
            s += String.format("%s,", i);
        }
        return s.substring(0, s.length() - 1);
    }

    public void info(String msg) {
        this.log.info(msg);
        this.writeToLog(String.format(msg));
    }

    public void info(String msg, Object... data) {
        this.log.log(Level.INFO, String.format(msg, data));
        this.writeToLog(String.format(msg, data));
    }

    public void warn(String msg) {
        this.log.warn(msg);
        this.writeToLog(String.format(msg));
    }

    public void warn(String msg, Object... data) {
        this.log.log(Level.WARN, String.format(msg, data));
        this.writeToLog(String.format(msg, data));
    }

    public void debug(String msg) {
        this.log.debug(msg);
        this.writeToLog(msg);
    }

    public void debug(String msg, Object... data) {
        this.log.log(Level.DEBUG, String.format(msg, data));
        this.writeToLog(String.format(msg, data));
    }

    public void bigWarning(String msg) {
        this.log.warn("--------------------------");
        this.log.warn(String.format(msg));
        this.log.warn("--------------------------");
        this.writeToLog("--------------------------");
        this.writeToLog(String.format(msg));
        this.writeToLog("--------------------------");
    }

    public void bigWarning(String msg, Object... data) {
        this.log.warn("--------------------------");
        this.log.warn(String.format(msg, data));
        this.log.warn("--------------------------");
        this.writeToLog("--------------------------");
        this.writeToLog(String.format(msg, data));
        this.writeToLog("--------------------------");
    }

    private void writeToLog(String msg) {
        try {
            if (debug == null) {
                return;
            }
            FileUtils.writeStringToFile(debug, msg.concat(SystemUtils.LINE_SEPARATOR), true);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
