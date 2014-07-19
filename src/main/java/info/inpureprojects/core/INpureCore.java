package info.inpureprojects.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import info.inpureprojects.core.Events.INpureHandler;
import info.inpureprojects.core.Events.MinecraftHandler;
import info.inpureprojects.core.Proxy.Proxy;
import info.inpureprojects.core.Scripting.ScriptingCore;
import net.minecraftforge.common.MinecraftForge;

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
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        MinecraftForge.EVENT_BUS.register(new MinecraftHandler());
        core = new ScriptingCore();
        core.bus.register(new INpureHandler(configFolder));
        core.doSetup();
        // Put script init here. TODO
        core.doLoad();
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent evt) {
    }

}
