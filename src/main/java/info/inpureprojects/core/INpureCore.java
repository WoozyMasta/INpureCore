package info.inpureprojects.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import info.inpureprojects.core.API.IINpureSubmodule;
import info.inpureprojects.core.Config.PropertiesHolder;
import info.inpureprojects.core.Proxy.Proxy;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by den on 7/16/2014.
 */
@Mod(modid = modInfo.modid, name = modInfo.name, version = modInfo.version, dependencies = modInfo.deps)
public class INpureCore {

    @Mod.Instance
    public static INpureCore instance;
    @SidedProxy(clientSide = modInfo.proxyClient, serverSide = modInfo.proxyCommon)
    public static Proxy proxy;
    public static PropertiesHolder properties;
    public static ArrayList<IINpureSubmodule> modules = new ArrayList();

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent evt) {
        instance = this;
        properties = new PropertiesHolder(new Configuration(new File(new File(evt.getModConfigurationDirectory(), "INpureCore"), "INpureCore.cfg")));
        proxy.client();
        proxy.setupAPI();
        for (String s : subModules.Classes) {
            try {
                IINpureSubmodule m = (IINpureSubmodule) Class.forName(s).newInstance();
                modules.add(m);
            } catch (Throwable t) {
                proxy.severe("Failed to load submodule " + s + "!");
            }
        }
        //
        for (IINpureSubmodule s : modules) {
            s.pre();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent evt) {
        for (IINpureSubmodule s : modules) {
            s.init();
        }
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent evt) {
        for (IINpureSubmodule s : modules) {
            s.post();
        }
    }

}
