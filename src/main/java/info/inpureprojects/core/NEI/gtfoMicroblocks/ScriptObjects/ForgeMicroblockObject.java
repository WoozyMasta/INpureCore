package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import codechicken.microblock.MicroMaterialRegistry;
import codechicken.nei.api.API;
import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by den on 10/29/2014.
 */
public class ForgeMicroblockObject {

    public String getRandomMaterial() {
        try {
            return MicroMaterialRegistry.materialName(new Random().nextInt(20));
        } catch (Throwable t) {
            return "";
        }
    }

    public void obliterate_microblocks(int[] metas, String id) {
        ArrayList<ItemStack> stacks = new ArrayList();
        Item i = GameRegistry.findItem("ForgeMicroblock", "microblock");
        for (ItemStack s : NEIINpureConfig.buildStackList(new ItemStack(i), metas)) {
            try {
                stacks.add((ItemStack) i.getClass().getDeclaredMethod("create", new Class[]{int.class, String.class}).invoke(null, new Object[]{s.getItemDamage(), id}));
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        API.setItemListEntries(i, stacks);
    }

}
