package info.inpureprojects.core.Proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import info.inpureprojects.core.INpureCore;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

/**
 * Created by den on 7/16/2014.
 */
public class ProxyCommon extends Proxy {

    @Override
    public void warning(String msg) {
        FMLLog.warning("[INpureCore]: " + msg);
    }

    @Override
    public void print(String msg) {
        FMLLog.info("[INpureCore]: " + msg);
    }

    @Override
    public void severe(String msg) {
        FMLLog.severe("[INpureCore]: " + msg);
    }

    @Override
    public void registerOnAllBuses(Object o) {
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
        INpureCore.core.bus.register(o);
    }

    @Override
    public void extractCore() {
        INpureCore.core.runInternalScript("scripts/core/extract_core.js");
    }

    @Override
    public void loadJar(File file) {
        try {
            LaunchClassLoader classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
            classLoader.addURL(file.toURI().toURL());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void client() {

    }

    @Override
    public void collectData() {

    }
}
