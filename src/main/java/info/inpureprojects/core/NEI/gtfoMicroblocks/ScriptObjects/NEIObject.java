package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import codechicken.nei.api.API;
import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by den on 10/28/2014.
 */
public class NEIObject {

    public void override_item(String modid, String name, int[] metas) {
        Item i = GameRegistry.findItem(modid, name);
        if (i == null) {
            INpureCore.proxy.warning("Cannot find item " + modid + ":" + name);
            return;
        }
        ArrayList<ItemStack> stacks = NEIINpureConfig.buildStackList(new ItemStack(i), metas);
        API.setItemListEntries(i, stacks);
    }

    public void hide_block(Block b){
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(b);
        this.hide_block(id.modId, id.name);
    }

    public void override_block(Block b, int[] metas){
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(b);
        this.override_block(id.modId, id.name, metas);
    }

    public void hide_item(Item i){
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(i);
        this.hide_item(id.modId, id.name);
    }

    public void override_item(Item i, int[] metas){
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(i);
        this.override_item(id.modId, id.name, metas);
    }

    public void hide_block(String modid, String name) {
        Block b = GameRegistry.findBlock(modid, name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + modid + ":" + name);
            return;
        }
        API.hideItem(new ItemStack(b));
    }

    public void override_block(String modid, String name, int[] metas) {
        Block b = GameRegistry.findBlock(modid, name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + modid + ":" + name);
            return;
        }
        ItemStack B = new ItemStack(b);
        ArrayList<ItemStack> stacks = NEIINpureConfig.buildStackList(B, metas);
        API.setItemListEntries(B.getItem(), stacks);
    }

    public void hide_item(String modid, String name) {
        Item i = GameRegistry.findItem(modid, name);
        if (i == null) {
            INpureCore.proxy.warning("Cannot find item " + modid + ":" + name);
            return;
        }
        API.hideItem(new ItemStack(i));
    }

    public void hide_block_vanilla(String name) {
        Block b = Block.getBlockFromName(name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + "Minecraft" + ":" + name);
        }
        API.hideItem(new ItemStack(b));
    }

}
