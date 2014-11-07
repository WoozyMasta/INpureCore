package info.inpureprojects.core.Preloader;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.API.Utils.LogWrapper;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;
import info.inpureprojects.core.Utils.Events.EventNEIReady;
import info.inpureprojects.core.Utils.Loggers.EventFilter;
import info.inpureprojects.core.Utils.Loggers.EventLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by den on 11/1/2014.
 */
public class FMLLogInterceptor {

    public LogWrapper log = new LogWrapper(LogManager.getLogger("INpureLogInterceptor"), null);
    private Logger fmlOriginal;
    private Field myLog;
    private Object relaunch;
    @Deprecated
    private EventLogger FMLFiltered;
    private EventFilter filter;
    private Set<String> registry = new LinkedHashSet<String>();
    private boolean hooked = false;

    @Subscribe
    public void onFMLMessage(EventFMLMessage evt) {
        if (evt.getLevel().equals(Level.TRACE)) {
            if (evt.getMessage().contains("Registry add: ")) {
                // Found registry add call. Parse it.
                String copy = evt.getMessage().replace("Registry add: ", "");
                String[] split = copy.split("\\s+");
                copy = split[0];
                if (copy.contains(":")) {
                    // If we get here we've obtained what we need.
                    registry.add(copy);
                }
            }
        }
    }

    @Subscribe
    public void onNEIReady(EventNEIReady evt) {
        ArrayList<String> list = new ArrayList();
        list.addAll(registry);
        Collections.sort(list);
        evt.setList(Collections.unmodifiableList(list));
    }

    public FMLLogInterceptor setup() {
        filter = new EventFilter();
        filter.getBus().register(this);
        try {
            Class c = Class.forName("cpw.mods.fml.relauncher.FMLRelaunchLog");
            relaunch = c.getDeclaredField("log").get(null);
            myLog = relaunch.getClass().getDeclaredField("myLog");
            myLog.setAccessible(true);
            this.fmlOriginal = (Logger) myLog.get(relaunch);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        ((org.apache.logging.log4j.core.Logger) this.fmlOriginal).addFilter(filter);
        PreloaderAPI.preLoaderEvents.register(this);
        log.info("System attached to FML. Now intercepting all logging calls.");
        return this;
    }

    @Deprecated
    public FMLLogInterceptor setup_old() {
        if (hooked) {
            return this;
        }
        try {
            Class c = Class.forName("cpw.mods.fml.relauncher.FMLRelaunchLog");
            relaunch = c.getDeclaredField("log").get(null);
            myLog = relaunch.getClass().getDeclaredField("myLog");
            myLog.setAccessible(true);
            this.fmlOriginal = (Logger) myLog.get(relaunch);
            FMLFiltered = new EventLogger(this.fmlOriginal);
            FMLFiltered.getBus().register(this);
            myLog.set(relaunch, FMLFiltered);
            log.info("System attached to FML. Now intercepting all logging calls.");
            hooked = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return this;
    }

    public void unhook() {
        if (hooked) {
            this.filter.getBus().unregister(this);
            PreloaderAPI.preLoaderEvents.unregister(this);
            log.info("System no longer monitoring FML console messages.");
            hooked = false;
        }
    }

    @Deprecated
    public void unhook_old() {
        if (!hooked) {
            return;
        }
        try {
            this.FMLFiltered.getBus().unregister(this);
            myLog.set(relaunch, fmlOriginal);
            log.info("System detached from FML. Normal logging systems restored.");
            hooked = false;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
