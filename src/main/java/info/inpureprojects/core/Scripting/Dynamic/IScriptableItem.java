package info.inpureprojects.core.Scripting.Dynamic;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by den on 7/21/2014.
 */
public interface IScriptableItem {

    public IIcon getIcon(ItemStack item);

    public String getUnlocalizedName();

}
