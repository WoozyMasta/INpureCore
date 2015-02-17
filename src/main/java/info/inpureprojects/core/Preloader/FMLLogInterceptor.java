package info.inpureprojects.core.Preloader;

import info.inpureprojects.core.API.Events.INpureEventBus;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.API.Utils.LogWrapper;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;
import info.inpureprojects.core.Utils.Events.EventNEIReady;
import info.inpureprojects.core.Utils.Loggers.EventFilter;
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
    private EventFilter filter;
    private Set<String> registry = new LinkedHashSet<String>();

    @INpureEventBus.INpureSubscribe
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

    @INpureEventBus.INpureSubscribe
    public void onNEIReady(EventNEIReady evt) {
        ArrayList<String> list = new ArrayList();
        list.addAll(registry);
        if (list.isEmpty()) {
            log.info("Log parsing appears to have failed. Attempting to dig the data out of FML directly...");
            TechnicHandler.instance.reparse(list);
        }
        Collections.sort(list);
        evt.setList(Collections.unmodifiableList(list));
        this.log.info("NEI has entered the ready state. Sending data to culling system. List contains %s entries.", registry.size());
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

    public void unhook() {
        this.filter.getBus().unregister(this);
        PreloaderAPI.preLoaderEvents.unregister(this);
        log.info("System no longer monitoring FML console messages.");
    }

}
