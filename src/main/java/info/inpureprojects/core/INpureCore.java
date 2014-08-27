package info.inpureprojects.core;

import cofh.mod.updater.IUpdatableMod;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import info.inpureprojects.core.API.Events.EventPreloaderRegister;
import info.inpureprojects.core.API.IINpureSubmodule;
import info.inpureprojects.core.API.IINpureSubmoduleExpanded;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.Config.PropertiesHolder;
import info.inpureprojects.core.Preloader.INpurePreLoader;
import info.inpureprojects.core.Preloader.ModuleManager;
import info.inpureprojects.core.Proxy.Proxy;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by den on 7/16/2014.
 */
@Mod(modid = modInfo.modid, name = modInfo.name, version = modInfo.version, dependencies = modInfo.deps)
@Optional.Interface(iface = "cofh.mod.updater.IUpdatableMod", modid = "CoFHCore")
public class INpureCore implements IUpdatableMod{

    @Mod.Instance
    public static INpureCore instance;
    @SidedProxy(clientSide = modInfo.proxyClient, serverSide = modInfo.proxyCommon)
    public static Proxy proxy;
    public static PropertiesHolder properties;
    public static ArrayList<IINpureSubmodule> modules = new ArrayList();
    public static Logger log;

    @Optional.Method(modid = "CoFHCore")
    @Override
    public String getModId() {
        return modInfo.modid;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public String getModName() {
        return modInfo.name;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public String getModVersion() {
        return modInfo.version;
    }

    @Optional.Method(modid = "CoFHCore")
    @Override
    public Logger getLogger() {
        return log;
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        instance = this;
        PreloaderAPI.preLoaderEvents.register(this);
        log = evt.getModLog();
        for (File f : INpurePreLoader.toInject){
            INpurePreLoader.forceLoad(f);
        }
        INpurePreLoader.toInject = null;
        properties = new PropertiesHolder(new Configuration(new File(new File(evt.getModConfigurationDirectory(), "INpureProjects/INpureCore"), "INpureCore.cfg")));
        proxy.client();
        proxy.setupAPI();
        PreloaderAPI.preLoaderEvents.post(new EventPreloaderRegister());
        for (String s : ModuleManager.modules) {
            proxy.print("Constructing submodule " + s);
            try {
                IINpureSubmodule m = (IINpureSubmodule) Class.forName(s).newInstance();
                modules.add(m);
            } catch (Throwable t) {
                proxy.severe("Failed to load submodule " + s + "!");
                t.printStackTrace();
            }
        }
        for (IINpureSubmodule s : modules) {
            proxy.print("Processing preinit event for submodule " + s.getClass().getName());
            s.pre(new File(evt.getModConfigurationDirectory(), "INpureProjects"));
        }
    }

    @Subscribe
    public void registerModules(EventPreloaderRegister evt){
        if (Loader.isModLoaded("CoFHCore") && properties.updateCheck){
            PreloaderAPI.modules.register("info.inpureprojects.core.CoFH.ModuleUpdater");
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        for (IINpureSubmodule s : modules) {
            proxy.print("Processing init event for submodule " + s.getClass().getName());
            s.init();
        }
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent evt) {
        for (IINpureSubmodule s : modules) {
            proxy.print("Processing postinit event for submodule " + s.getClass().getName());
            s.post();
        }
    }

    @Mod.EventHandler
    public void onServer(FMLServerAboutToStartEvent evt) {
        for (IINpureSubmodule s : modules) {
            if (s instanceof IINpureSubmoduleExpanded) {
                proxy.print("Processing ServerAboutToStart event for submodule " + s.getClass().getName());
                ((IINpureSubmoduleExpanded) s).onServerAboutToStart();
            }
        }
    }

}
