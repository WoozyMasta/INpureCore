package info.inpureprojects.core.NEI.gtfoMicroblocks.Modules;

import appeng.api.AEApi;
import codechicken.nei.api.API;
import info.inpureprojects.core.INpureCore;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Random;

/**
 * Created by den on 8/1/2014.
 */
public class appliedenergistics2 extends GtfoFMPModule {

    public appliedenergistics2(String id) {
        super(id);
    }

    @Override
    public void run() {
        INpureCore.proxy.print("Oh look, more facades. Did we really need another version of these? (Hiding AE facades)");
        try {
            Field f = AEApi.instance().items().itemFacade.item().getClass().getDeclaredField("subTypes");
            f.setAccessible(true);
            List<ItemStack> list = (List) f.get(AEApi.instance().items().itemFacade.item());
            API.setItemListEntries(AEApi.instance().items().itemFacade.item(), Arrays.asList(new ItemStack[]{list.get(new Random().nextInt(list.size()))}));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
