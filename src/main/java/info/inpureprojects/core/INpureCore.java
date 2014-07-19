package info.inpureprojects.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import info.inpureprojects.core.Events.INpureHandler;
import info.inpureprojects.core.Minecraft.MinecraftHandler;
import info.inpureprojects.core.Proxy.Proxy;
import info.inpureprojects.core.Scripting.ScriptingCore;

import java.io.File;

/**
 * Created by den on 7/16/2014.
 */
@Mod(modid = modInfo.modid, name = modInfo.name, version = modInfo.version)
public class INpureCore {

    @Mod.Instance
    public static INpureCore instance;
    @SidedProxy(clientSide = modInfo.proxyClient, serverSide = modInfo.proxyCommon)
    public static Proxy proxy;
    public static ScriptingCore core;
    public static File configFolder;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        instance = this;
        configFolder = new File(evt.getModConfigurationDirectory(), "INpureCore");
        proxy.downloadLibs();
        core = new ScriptingCore();
        proxy.registerOnAllBuses(new INpureHandler(configFolder));
        proxy.registerOnAllBuses(new MinecraftHandler());
        core.doSetup();
        proxy.extractCore();
        core.loadScripts();
        core.doLoad();
        core.bus.post(evt);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        core.bus.post(evt);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent evt) {
        core.bus.post(evt);
        core.doSave();
    }

}
