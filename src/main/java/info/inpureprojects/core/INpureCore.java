package info.inpureprojects.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import info.inpureprojects.core.Config.PropertiesHolder;
import info.inpureprojects.core.Proxy.Proxy;
import net.minecraftforge.common.config.Configuration;

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
    public static PropertiesHolder properties;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        instance = this;
        properties = new PropertiesHolder(new Configuration(new File(new File(evt.getModConfigurationDirectory(), "INpureCore"), "INpureCore.cfg")));
        proxy.client();
        proxy.setupAPI();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent evt) {
    }

}
