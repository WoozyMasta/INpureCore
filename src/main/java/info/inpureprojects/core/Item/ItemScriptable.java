package info.inpureprojects.core.Item;

import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Scripting.Dynamic.IScriptableItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by den on 7/21/2014.
 */
public class ItemScriptable extends Item {

    private IScriptableItem script;

    public ItemScriptable(IScriptableItem script) {
        super();
        this.script = script;
        this.setUnlocalizedName(this.script.getUnlocalizedName());
        INpureCore.proxy.print("Item " + this.script.getUnlocalizedName() + " constructed.");
    }

    @Override
    public IIcon getIconIndex(ItemStack par1ItemStack) {
        return script.getIcon(par1ItemStack);
    }

    @Override
    public IIcon getIconFromDamage(int p_77617_1_) {
        return script.getIcon(null);
    }

    @Override
    public void registerIcons(IIconRegister p_94581_1_) {

    }
}
