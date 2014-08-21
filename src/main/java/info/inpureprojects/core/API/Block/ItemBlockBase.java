package info.inpureprojects.core.API.Block;

import info.inpureprojects.core.API.MovedFrom;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * Created by den on 8/11/2014.
 */
@MovedFrom(mod = "OpenBees")
public abstract class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.field_150939_a.getUnlocalizedName() + "." + stack.getItemDamage();
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
