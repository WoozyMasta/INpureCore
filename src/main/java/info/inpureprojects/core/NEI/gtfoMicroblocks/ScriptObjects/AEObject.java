package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import appeng.api.AEApi;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by den on 10/29/2014.
 */
public class AEObject {

    public List<ItemStack> getSubTypes(){
        try{
            Field f = AEApi.instance().items().itemFacade.item().getClass().getDeclaredField("subTypes");
            f.setAccessible(true);
            List<ItemStack> list = (List) f.get(AEApi.instance().items().itemFacade.item());
            return list;
        }catch(Throwable t){
            t.printStackTrace();
        }
        return null;
    }

    public int getNumberOfTypes(){
        return this.getSubTypes().size();
    }

    public Item getFacadeItem(){
        return AEApi.instance().items().itemFacade.item();
    }

}
