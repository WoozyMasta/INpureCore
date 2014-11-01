package info.inpureprojects.core.Proxy;

import cpw.mods.fml.common.FMLLog;
import info.inpureprojects.core.API.INpureAPI;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.IScriptingManager;
import info.inpureprojects.core.Scripting.Dynamic.DynamicFactory;
import info.inpureprojects.core.Scripting.ScriptingCore;

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

    }

    @Override
    public void client() {

    }

    @Override
    public void sendMessageToPlayer(String msg) {
        this.print(msg);
    }
}
