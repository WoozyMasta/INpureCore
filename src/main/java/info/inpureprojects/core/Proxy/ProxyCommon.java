package info.inpureprojects.core.Proxy;

import info.inpureprojects.core.API.Events.EventScriptAPIReady;
import info.inpureprojects.core.API.INpureAPI;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.IScriptingManager;
import info.inpureprojects.core.Common.CommonLogListener;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Scripting.Dynamic.DynamicFactory;
import info.inpureprojects.core.Scripting.ScriptingCore;
import info.inpureprojects.core.Utils.Loggers.EventFilter;
import org.apache.logging.log4j.core.Logger;

import java.lang.reflect.Field;

/**
 * Created by den on 7/16/2014.
 */
public class ProxyCommon extends Proxy {

    @Override
    public void onServerStartClient() {

    }

    @Override
    public void warning(String msg) {
        INpureCore.log.warn(msg);
    }

    @Override
    public void print(String msg) {
        INpureCore.log.info(msg);
    }

    @Override
    public void severe(String msg) {
        INpureCore.log.bigWarning(msg);
    }

    @Override
    public void setupAPI() {
        INpureAPI.manager = new IScriptingManager() {
            @Override
            public IScriptingCore create(SupportedLanguages lang) {
                return new ScriptingCore(lang);
            }

            @Override
            public Object WrapScript(IScriptingCore core, Object obj, Class Interface) {
                return DynamicFactory.instance.create(core, obj, Interface);
            }
        };
        PreloaderAPI.preLoaderEvents.post(new EventScriptAPIReady());
    }

    @Override
    public void client() {
        if (INpureCore.properties.silence_cant_keep_up){
            try{
                Class c = Class.forName("net.minecraft.server.MinecraftServer");
                Field f = c.getDeclaredField("logger");
                f.setAccessible(true);
                Logger mcLogger = (Logger) f.get(null);
                EventFilter filter = new EventFilter();
                filter.getBus().register(new CommonLogListener());
                mcLogger.addFilter(filter);
                f.setAccessible(false);
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

    @Override
    public void sendMessageToPlayer(String msg) {
        this.print(msg);
    }
}
