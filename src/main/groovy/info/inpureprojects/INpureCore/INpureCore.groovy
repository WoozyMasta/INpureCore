package info.inpureprojects.INpureCore

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

    static LogWrapper log;

    void preinit(FMLPreInitializationEvent evt){
        log = new LogWrapper(evt.getModLog())
        INpureAPI.manager = new ScriptManager()
    }

    void init(FMLInitializationEvent evt){

    }

    void postinit(FMLPostInitializationEvent evt){

    }

}
