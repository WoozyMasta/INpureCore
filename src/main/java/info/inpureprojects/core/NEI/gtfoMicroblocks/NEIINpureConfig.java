package info.inpureprojects.core.NEI.gtfoMicroblocks;

import codechicken.microblock.MicroMaterialRegistry;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Loader;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.Modules.Vanilla;
import info.inpureprojects.core.modInfo;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by den on 8/1/2014.
 */
public class NEIINpureConfig implements IConfigureNEI {

    public static final String[] supported = new String[]{"ForgeMicroblock", "ExtraUtilities", "BuildCraft|Transport", "appliedenergistics2", "BiblioCraft", "ThermalExpansion", "Mekanism"};

    public static ArrayList<ItemStack> buildStackList(ItemStack stack, int[] metas) {
        ArrayList<ItemStack> stacks = new ArrayList();
        for (int i : metas) {
            stacks.add(new ItemStack(stack.getItem(), 1, i));
        }
        return stacks;
    }

    public static void hideBlock(Block b) {
        INpureCore.proxy.print("Get off my curb you good for nothin! (Hiding " + b.getUnlocalizedName() + ")");
        ItemStack block = new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE);
        API.hideItem(block);
    }

    @Override
    public void loadConfig() {
        if (INpureCore.properties.hideVanillaCrapFromNEI) {
            IGtfoModule vanilla = new Vanilla();
            vanilla.run();
        }
        if (INpureCore.properties.hideCrapFromNEI) {
            for (String s : supported) {
                try {
                    if (Loader.isModLoaded(s)) {
                        IGtfoModule module = (IGtfoModule) Class.forName("info.inpureprojects.core.NEI.gtfoMicroblocks.Modules.".concat(s.replace("|", ""))).getConstructor(new Class[]{String.class}).newInstance(new Object[]{this.getRandomMaterial()});
                        module.run();
                    }
                } catch (Throwable t) {
                    INpureCore.proxy.warning("Failed to load NEI Cleanup Module: " + s);
                }
            }
        }
    }

    private String getRandomMaterial() {
        try {
            return MicroMaterialRegistry.materialName(new Random().nextInt(20));
        } catch (Throwable t) {
            return "";
        }
    }

    @Override
    public String getName() {
        return modInfo.name;
    }

    @Override
    public String getVersion() {
        return modInfo.version;
    }

}
