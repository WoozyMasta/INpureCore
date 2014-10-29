package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import cpw.mods.fml.common.Loader;

/**
 * Created by den on 10/29/2014.
 */
public class FMLObject {

    public boolean isModLoaded(String modid) {
        return Loader.isModLoaded(modid);
    }

}
