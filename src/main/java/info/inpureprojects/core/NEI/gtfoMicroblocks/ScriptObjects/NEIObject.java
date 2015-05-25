package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import codechicken.nei.api.API;
import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import info.inpureprojects.core.Utils.Regex.RegxEngine;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 10/28/2014.
 */
public class NEIObject {

    private static final ArrayList<String> modids_with_bad_names = new ArrayList<String>();

    public NEIObject() {
        NEIINpureConfig.logger.debug("Setting up NEI Library...");
    }

    // Generic Section

    public static UniqueIDSettable getUniqueID(String domain) {
        GameRegistry.UniqueIdentifier id = new GameRegistry.UniqueIdentifier(domain);
        if (StringUtils.countMatches(domain, ":") > 1) {
            NEIINpureConfig.logger.debug("%s has a bad registry name! Attempting to parse and reassemble correctly...", domain);
            String d = domain.replace(String.format("%s:", id.modId), "");
            NEIINpureConfig.logger.debug("Parsed bad name into: modid: %s, name: %s. If this is not correct please report it as a bug!", id.modId, d);
            if (INpureCore.properties.complain_about_bad_names && !modids_with_bad_names.contains(id.modId)) {
                NEIINpureConfig.logger.warn("The mod %s is registering items or blocks with bad registry names. Names should not contain multiple colons as it breaks the ability to do reverse-lookups through FML. Please tell whoever made this mod that this should be changed!", id.modId);
                modids_with_bad_names.add(id.modId);
            }
            return new UniqueIDSettable(id.modId, d);
        } else {
            return new UniqueIDSettable(id);
        }
    }

    public ArrayList<String> find(Object... data) {
        ArrayList<String> stacks = new ArrayList();
        String modid = data[0].toString();
        String name = data[1].toString();
        String recombine = String.format("%s:%s", modid, name);
        for (String s : NEIINpureConfig.reg) {
            if (RegxEngine.match(recombine, s)) {
                NEIINpureConfig.logger.debug("Regex match found! %s matches %s.", recombine, s);
                stacks.add(s);
            }
        }
        return stacks;
    }

    public ItemStack getStack(String domain) {
        UniqueIDSettable id = getUniqueID(domain);
        return this.getStack(id.modId, id.name);
    }

    public ItemStack getStack(String modid, String name) {
        ItemStack i = GameRegistry.findItemStack(modid, name, OreDictionary.WILDCARD_VALUE);
        if (i == null) {
            NEIINpureConfig.logger.debug("Cannot find ItemStack %s:%s", modid, name);
            return null;
        } else {
            NEIINpureConfig.logger.debug("Found ItemStack %s:%s. %s", modid, name, i.toString());
        }
        return i;
    }

    public void override(String modid, String name, int[] metas) {
        NEIINpureConfig.logger.debug("%s called. Params: %s, %s, %s", "override", modid, name, NEIINpureConfig.logger.IntArrayToString(metas));
        for (String s : find(modid, name)) {
            this.override_impl(this.getStack(s), metas);
        }
    }

    private void override_impl(ItemStack i, int[] metas) {
        if (i == null || metas == null) {
            return;
        }
        API.setItemListEntries(i.getItem(), NEIINpureConfig.buildStackList(i, metas));
    }

    public void override_with_nbt(String modid, String name) {
        NEIINpureConfig.logger.debug("%s called. Params: %s, %s", "override_with_nbt", modid, name);
        for (String s : this.find(modid, name)) {
            this.override_with_nbt_impl(this.getStack(s));
        }
    }

    private void override_with_nbt_impl(ItemStack i) {
        if (i == null) {
            return;
        }
        List<ItemStack> stacks = new ArrayList<ItemStack>();
        i.getItem().getSubItems(i.getItem(), null, stacks);
        ArrayList<ItemStack> stackList = new ArrayList<ItemStack>();
        stackList.add(stacks.get(0));
        API.setItemListEntries(i.getItem(), stackList);
    }

    private void hide_impl(ItemStack i) {
        if (i == null) {
            return;
        }
        API.setItemListEntries(i.getItem(), NEIINpureConfig.buildStackList(i, new int[]{0}));
        API.hideItem(i);
    }

    public void override(String domain, int[] metas) {
        UniqueIDSettable id = getUniqueID(domain);
        this.override(id.modId, id.name, metas);
    }

    public void hide(String modid, String name) {
        NEIINpureConfig.logger.debug("%s called. Params: %s, %s", "hide", modid, name);
        for (String s : this.find(modid, name)) {
            this.hide_impl(this.getStack(s));
        }
    }

    public void hide(String domain) {
        UniqueIDSettable id = getUniqueID(domain);
        this.hide(id.modId, id.name);
    }

    // Item Section
    @Deprecated
    public void override_item(String modid, String name, int[] metas) {
        this.deprecationWarning("override_item", "override");
        this.override(modid, name, metas);
    }

    // Deprecated shit below

    //

    @Deprecated
    public void override_item(Item i, int[] metas) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(i);
        this.override_item(id.modId, id.name, metas);
    }

    @Deprecated
    public void override_item(String domain, int[] metas) {
        UniqueIDSettable id = getUniqueID(domain);
        this.override_item(id.modId, id.name, metas);
    }

    @Deprecated
    public void hide_item(Item i) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(i);
        this.hide_item(id.modId, id.name);
    }

    @Deprecated
    public void hide_item(String modid, String name) {
        this.deprecationWarning("hide_item", "hide");
        this.hide(modid, name);
    }

    @Deprecated
    public void hide_item(String domain) {
        UniqueIDSettable id = getUniqueID(domain);
        this.hide_item(id.modId, id.modId);
    }

    // Block Section
    @Deprecated
    public void hide_block(Block b) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(b);
        this.hide_block(id.modId, id.name);
    }
    //

    @Deprecated
    public void hide_block(String modid, String name) {
        this.deprecationWarning("hide_block", "hide");
        this.hide(modid, name);
    }

    @Deprecated
    public void hide_block(String domain) {
        UniqueIDSettable id = getUniqueID(domain);
        this.hide_block(id.modId, id.name);
    }

    @Deprecated
    public void override_block(Block b, int[] metas) {
        GameRegistry.UniqueIdentifier id = GameRegistry.findUniqueIdentifierFor(b);
        this.override_block(id.modId, id.name, metas);
    }

    @Deprecated
    public void override_block(String modid, String name, int[] metas) {
        this.deprecationWarning("override_block", "override");
        this.override(modid, name, metas);
    }

    @Deprecated
    public void override_block(String domain, int[] metas) {
        UniqueIDSettable id = getUniqueID(domain);
        this.override_block(id.modId, id.modId, metas);
    }

    // TODO: Remove these eventually. No longer needed.
    @Deprecated
    public void hide_block_vanilla(String name) {
        /*Block b = Block.getBlockFromName(name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + "minecraft" + ":" + name);
        }
        API.hideItem(new ItemStack(b));*/
        this.hide_block("minecraft:".concat(name));
        this.deprecationWarning("hide_block_vanilla", "hide");
    }
    //

    @Deprecated
    public void override_block_vanilla(String name, int[] metas) {
        /*Block b = Block.getBlockFromName(name);
        if (b == null) {
            INpureCore.proxy.warning("Cannot find block " + "minecraft" + ":" + name);
        }
        ItemStack i = new ItemStack(b);
        API.setItemListEntries(i.getItem(), NEIINpureConfig.buildStackList(i, metas));*/
        this.override_block("minecraft:".concat(name), metas);
        this.deprecationWarning("override_block_vanilla", "override");
    }
    //

    public void deprecationWarning(String func, String n) {
        NEIINpureConfig.logger.warn("The function %s is deprecated. Please use %s instead! This function will be removed in a future version!", func, n);
    }

    @Deprecated
    public static enum METHOD {

        OVERRIDE,
        HIDE;

    }

    public static class UniqueIDSettable {

        private String modId;
        private String name;

        public UniqueIDSettable(String modid, String name) {
            this.modId = modid;
            this.name = name;
        }

        public UniqueIDSettable(GameRegistry.UniqueIdentifier id) {
            this.modId = id.modId;
            this.name = id.name;
        }

        public String getModId() {
            return modId;
        }

        public void setModId(String modId) {
            this.modId = modId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("%s:%s", this.modId, this.name);
        }
    }

}
