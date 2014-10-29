package info.inpureprojects.core.NEI.gtfoMicroblocks.Modules;

import info.inpureprojects.core.NEI.gtfoMicroblocks.IGtfoModule;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.init.Blocks;

/**
 * Created by den on 8/1/2014.
 */
@Deprecated
public class Vanilla implements IGtfoModule {

    @Override
    public void run() {
        NEIINpureConfig.hideBlock(Blocks.mob_spawner);
        NEIINpureConfig.hideBlock(Blocks.water);
        NEIINpureConfig.hideBlock(Blocks.lava);
        NEIINpureConfig.hideBlock(Blocks.portal);
        NEIINpureConfig.hideBlock(Blocks.end_portal);
        NEIINpureConfig.hideBlock(Blocks.fire);
    }
}
