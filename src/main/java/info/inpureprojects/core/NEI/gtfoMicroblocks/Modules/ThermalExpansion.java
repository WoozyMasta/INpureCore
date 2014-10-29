package info.inpureprojects.core.NEI.gtfoMicroblocks.Modules;

import codechicken.nei.api.API;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.item.ItemStack;

/**
 * Created by den on 9/19/2014.
 */
@Deprecated
public class ThermalExpansion extends GtfoFMPModule{

    public ThermalExpansion(String id) {
        super(id);
    }

    @Override
    public void run() {
        INpureCore.proxy.print("Oh look, Florbs.");
        try{
            ItemStack florb = (ItemStack) Class.forName("thermalexpansion.item.TEFlorbs").getDeclaredField("florb").get(null);
            API.setItemListEntries(florb.getItem(), NEIINpureConfig.buildStackList(florb, new int[]{0, 1}));
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
}
