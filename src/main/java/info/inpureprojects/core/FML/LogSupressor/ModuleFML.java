package info.inpureprojects.core.FML.LogSupressor;

import cpw.mods.fml.common.FMLLog;
import info.inpureprojects.core.API.IINpureSubmoduleExpanded;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Utils.FilterLogger;
import info.inpureprojects.core.Utils.FilterPrintStream;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;

/**
 * Created by den on 11/1/2014.
 */
public class ModuleFML implements IINpureSubmoduleExpanded {

    private PrintStream original;
    private Logger fmlOriginal;
    private Field myLog;
    private Object relaunch;
    private FilterLogger FMLFiltered;
    private FilterPrintStream SystemFiltered;

    @Override
    public void pre(File configFolder) {
        try {
            Class c = Class.forName("cpw.mods.fml.relauncher.FMLRelaunchLog");
            relaunch = c.getDeclaredField("log").get(null);
            myLog = relaunch.getClass().getDeclaredField("myLog");
            myLog.setAccessible(true);
            this.fmlOriginal = (Logger) myLog.get(relaunch);
            FMLFiltered = new FilterLogger(this.fmlOriginal);
            myLog.set(relaunch, FMLFiltered);
            this.original = System.out;
            SystemFiltered = new FilterPrintStream(this.original);
            System.setOut(SystemFiltered);
            INpureCore.proxy.print("Log filter system engaged.");
            FMLLog.info("Testing log thing.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void post() {
        FMLFiltered.report();
        SystemFiltered.report();
    }

    @Override
    public void onServerAboutToStart() {
        try {
            myLog.set(relaunch, fmlOriginal);
            System.setOut(original);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
