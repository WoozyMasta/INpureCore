package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Created by den on 11/4/2014.
 */
public class CreativeObject {

    private LinkedHashMap<String, CreativeTabs> tabs = new LinkedHashMap<String, CreativeTabs>();

    public CreativeObject() {
        NEIINpureConfig.logger.debug("Setting up CreativeTab library...");
        for (String s : NEIINpureConfig.reg){
            GameRegistry.UniqueIdentifier id = NEIObject.getUniqueID(s);
            ItemStack i = GameRegistry.findItemStack(id.modId, id.name, OreDictionary.WILDCARD_VALUE);
            if (i != null){
                CreativeTabs t = i.getItem().getCreativeTab();
                if (t != null){
                    tabs.put(t.getTabLabel(), t);
                }
            }
        }
        for (CreativeTabs t : tabs.values()){
            NEIINpureConfig.logger.debug("Found creative tab: " + t.getTabLabel());
        }
    }

    public String[] getTabList(){
        ArrayList<String> list = new ArrayList();
        Collections.sort(list);
        return list.toArray(new String[list.size()]);
    }

    public void set_tab(String domain, String tab){
        try{
            // Call a scripting command from inside a scripting command? Sure.
            ItemStack i = (ItemStack) NEIINpureConfig.scripting.getEngine().eval(String.format("NEI.getStack(%s);", domain));
            if (tabs.get(tab) != null){
                i.getItem().setCreativeTab(tabs.get(tab));
            }
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

    public void remove_tab(String domain){
        try{
            // Call a scripting command from inside a scripting command? Sure.
            ItemStack i = (ItemStack) NEIINpureConfig.scripting.getEngine().eval(String.format("NEI.getStack(%s);", domain));
            i.getItem().setCreativeTab(null);
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
}
