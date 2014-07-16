package info.inpureprojects.core.Proxy;

import cpw.mods.fml.common.FMLLog;

/**
 * Created by den on 7/16/2014.
 */
public class ProxyCommon extends Proxy{

    @Override
    public void warning(String msg) {
        FMLLog.warning("[INpureCore]: " + msg);
    }
}
