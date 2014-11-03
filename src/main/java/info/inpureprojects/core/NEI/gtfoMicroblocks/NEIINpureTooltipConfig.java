package info.inpureprojects.core.NEI.gtfoMicroblocks;

import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.guihook.GuiContainerManager;
import codechicken.nei.guihook.IContainerTooltipHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.Utils.Events.EventNEIReady;
import info.inpureprojects.core.modInfo;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Created by den on 11/3/2014.
 */
public class NEIINpureTooltipConfig  implements IConfigureNEI {

    public static boolean tooltips_enabled = false;

    @Override
    public void loadConfig() {
        GuiContainerManager.addTooltipHandler(new TooltipHandler());
    }

    @Override
    public String getName() {
        return "INpure Tooltip Tweaks";
    }

    @Override
    public String getVersion() {
        return modInfo.version;
    }

    public static class TooltipHandler implements IContainerTooltipHandler{
        @Override
        public List<String> handleTooltip(GuiContainer guiContainer, int i, int i2, List<String> strings) {
            return strings;
        }

        @Override
        public List<String> handleItemDisplayName(GuiContainer guiContainer, ItemStack itemStack, List<String> strings) {
            if (tooltips_enabled){
                GameRegistry.UniqueIdentifier i = GameRegistry.findUniqueIdentifierFor(itemStack.getItem());
                strings.add(i.modId + ":" + i.name);
            }
            return strings;
        }

        @Override
        public List<String> handleItemTooltip(GuiContainer guiContainer, ItemStack itemStack, int i, int i2, List<String> strings) {
            return strings;
        }
    }
}
