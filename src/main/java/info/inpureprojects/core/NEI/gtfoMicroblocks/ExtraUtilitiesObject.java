package info.inpureprojects.core.NEI.gtfoMicroblocks;

import codechicken.nei.api.API;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

/**
 * Created by den on 10/29/2014.
 */
public class ExtraUtilitiesObject {

    public void obliterate_microblocks(int[] metas, String id){
        Item i = GameRegistry.findItem("ExtraUtilities", "microblocks");
        ItemStack pipeJacket = new ItemStack(i, 1, 0);
        ArrayList<ItemStack> stacks = new ArrayList();
        for (ItemStack s : NEIINpureConfig.buildStackList(pipeJacket, metas)) {
            try {
                stacks.add((ItemStack) i.getClass().getDeclaredMethod("getStack", new Class[]{ItemStack.class, String.class}).invoke(null, new Object[]{s, id}));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        API.setItemListEntries(i, stacks);
    }

}
