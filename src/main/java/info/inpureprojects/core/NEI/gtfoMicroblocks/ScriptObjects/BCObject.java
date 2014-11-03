package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import codechicken.nei.api.API;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by den on 10/29/2014.
 */
public class BCObject {

    public void obliterate_facades(int index) {
        NEIINpureConfig.logger.debug("obliterate_microblocks called (version in %s). Params: %s", this.getClass().getName(), String.valueOf(index));
        try {
            LinkedList<ItemStack> facades = (LinkedList) Class.forName("buildcraft.transport.ItemFacade").getDeclaredField("allFacades").get(null);
            API.setItemListEntries(facades.get(0).getItem(), Arrays.asList(new ItemStack[]{facades.get(index)}));
        } catch (Throwable t) {
            INpureCore.proxy.warning("Failed to hook bc!");
            t.printStackTrace();
        }
    }

    public int getFacadesSize() {
        try {
            LinkedList<ItemStack> facades = (LinkedList) Class.forName("buildcraft.transport.ItemFacade").getDeclaredField("allFacades").get(null);
            NEIINpureConfig.logger.debug("getFacadesSize called. Returned: %s", String.valueOf(facades.size()));
            return facades.size();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return 0;
    }

}
