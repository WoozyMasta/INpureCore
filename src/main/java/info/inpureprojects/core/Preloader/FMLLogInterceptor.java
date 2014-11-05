package info.inpureprojects.core.Preloader;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.API.IINpureSubmoduleExpanded;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;
import info.inpureprojects.core.Utils.Events.EventNEIReady;
import info.inpureprojects.core.Utils.Loggers.EventLogger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by den on 11/1/2014.
 */
public class FMLLogInterceptor{

    private Logger fmlOriginal;
    private Field myLog;
    private Object relaunch;
    private EventLogger FMLFiltered;
    private Logger log = LogManager.getLogger("INpureLogInterceptor");
    private Set<String> registry = new LinkedHashSet<String>();

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
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return this;
    }

    public void unhook(){
        try{
            this.FMLFiltered.getBus().unregister(this);
            myLog.set(relaunch, fmlOriginal);
            log.info("System detached from FML. Normal logging systems restored.");
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

}
