package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import appeng.api.AEApi;
import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by den on 10/29/2014.
 */
public class AEObject {

    public AEObject() {
        NEIINpureConfig.logger.debug("Setting up Applied Energistics 2 Library...");
    }

    public List<ItemStack> getSubTypes() {
        NEIINpureConfig.logger.debug("getSubTypes called.");
        try {
            Field f = AEApi.instance().items().itemFacade.item().getClass().getDeclaredField("subTypes");
            f.setAccessible(true);
            List<ItemStack> list = (List) f.get(AEApi.instance().items().itemFacade.item());
            return list;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public int getNumberOfTypes() {
        int i = this.getSubTypes().size();
        NEIINpureConfig.logger.debug("getNumberOfTypes called. Returned: %s", String.valueOf(i));
        return i;
    }

    public String getFacadeItem() {
        NEIObject.UniqueIDSettable id = new NEIObject.UniqueIDSettable(GameRegistry.findUniqueIdentifierFor(AEApi.instance().items().itemFacade.item()));
        NEIINpureConfig.logger.debug("getFacadeItem called. Returned: %s:%s", id.getModId(), id.getName());
        return String.format("%s:%s", id.getModId(), id.getName());
    }

}
