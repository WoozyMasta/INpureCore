package info.inpureprojects.INpureCore

import info.inpureprojects.INpureCore.API.Downloader
import info.inpureprojects.INpureCore.API.INpureAPI
import info.inpureprojects.INpureCore.API.LogWrapper
import info.inpureprojects.INpureCore.Scripting.ScriptManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
// This import shows as unused, but it has to be here because Groovy is silly with annotation constants.
import info.inpureprojects.INpureCore.modInfo;

/**
 * Created by den on 1/6/2015.
 */
@Mod(modid = modInfo.modid, name = modInfo.modname, version = modInfo.version)
class INpureCore {

    static final MCVersion = "1.8"
    static final libs_url = "http://files.inpureprojects.info/libs"
    static LogWrapper log;

    @Mod.EventHandler
    void preinit(FMLPreInitializationEvent evt){
        log = new LogWrapper(evt.getModLog(), null)
        for (String s : ["commons-codec-1.9.jar", "commons-compress-1.8.1.jar", "commons-io-2.4.jar", "groovy-2.3.9.jar", "groovy-jsr223-2.3.9.jar"]){
            Downloader.instance.download("${libs_url}/${s}", new File(evt.getModConfigurationDirectory().getParentFile(), "mods/${MCVersion}/${s}"))
        }
        INpureAPI.manager = new ScriptManager()
    }

    @Mod.EventHandler
    void init(FMLInitializationEvent evt){

    }

    @Mod.EventHandler
    void postinit(FMLPostInitializationEvent evt){

    }

}
