package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import codechicken.nei.api.API;
import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * Created by den on 10/28/2014.
 */
public class NEIObject {

    // Item Section
    public void override_item(String modid, String name, int[] metas) {
        NEIINpureConfig.logger.debug("override_item called. Params: %s, %s, %s", modid, name, NEIINpureConfig.logger.IntArrayToString(metas));
        Item i = GameRegistry.findItem(modid, name);
        if (i == null) {
            NEIINpureConfig.logger.warn("Cannot find item %s:%s", modid, name);
            return;
        }
        ArrayList<ItemStack> stacks = NEIINpureConfig.buildStackList(new ItemStack(i), metas);
        API.setItemListEntries(i, stacks);
    }

    public void override_item(Item i, int[] metas) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(i);
        this.override_item(id.modId, id.name, metas);
    }

    public void override_item(String domain, int[] metas) {
        this.override_item(this.getTargetMod(domain), this.getTarget(domain), metas);
    }

    public void hide_item(Item i) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(i);
        this.hide_item(id.modId, id.name);
    }

    public void hide_item(String modid, String name) {
        NEIINpureConfig.logger.debug("hide_item called. Params: %s, %s", modid, name);
        Item i = GameRegistry.findItem(modid, name);
        if (i == null) {
            NEIINpureConfig.logger.warn("Cannot find item %s:%s", modid, name);
            return;
        }
        API.hideItem(new ItemStack(i));
    }

    public void hide_item(String domain) {
        this.hide_item(this.getTargetMod(domain), this.getTarget(domain));
    }
    //

    // Block Section
    public void hide_block(Block b) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(b);
        this.hide_block(id.modId, id.name);
    }

    public void hide_block(String modid, String name) {
        NEIINpureConfig.logger.debug("hide_block called. Params: %s, %s", modid, name);
        Block b = GameRegistry.findBlock(modid, name);
        if (b == null) {
            NEIINpureConfig.logger.debug("Cannot find block %s:%s", modid, name);
            return;
        }
        API.hideItem(new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE));
    }

    public void hide_block(String domain) {
        this.hide_block(this.getTargetMod(domain), this.getTarget(domain));
    }

    public void override_block(Block b, int[] metas) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(b);
        this.override_block(id.modId, id.name, metas);
    }

    public void override_block(String modid, String name, int[] metas) {
        NEIINpureConfig.logger.debug("override_block called. Params: %s, %s, %s", modid, name, NEIINpureConfig.logger.IntArrayToString(metas));
        Block b = GameRegistry.findBlock(modid, name);
        if (b == null) {
            NEIINpureConfig.logger.debug("Cannot find block %s:%s", modid, name);
            return;
        }
        ItemStack B = new ItemStack(b);
        ArrayList<ItemStack> stacks = NEIINpureConfig.buildStackList(B, metas);
        API.setItemListEntries(B.getItem(), stacks);
    }

    public void override_block(String domain, int[] metas) {
        this.override_block(this.getTargetMod(domain), this.getTarget(domain), metas);
    }
    //

    // Utility methods
    private String getTargetMod(String domain) {
        return domain.split(":")[0];
    }

    private String getTarget(String domain) {
        return domain.split(":")[1];
    }
    //

    // TODO: Remove these eventually. No longer needed.
    @Deprecated
    public void hide_block_vanilla(String name) {
        /*Block b = Block.getBlockFromName(name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + "minecraft" + ":" + name);
        }
        API.hideItem(new ItemStack(b));*/
        this.hide_block("minecraft:".concat(name));
    }

    @Deprecated
    public void override_block_vanilla(String name, int[] metas) {
        /*Block b = Block.getBlockFromName(name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + "minecraft" + ":" + name);
        }
        ItemStack i = new ItemStack(b);
        API.setItemListEntries(i.getItem(), NEIINpureConfig.buildStackList(i, metas));*/
        this.override_block("minecraft:".concat(name), metas);
    }

}
