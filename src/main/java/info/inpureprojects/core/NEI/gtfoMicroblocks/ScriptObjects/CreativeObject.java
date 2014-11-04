package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import cpw.mods.fml.common.registry.GameRegistry;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

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
        for (String s : NEIINpureConfig.reg) {
            GameRegistry.UniqueIdentifier id = NEIObject.getUniqueID(s);
            ItemStack i = null;
            try {
                i = GameRegistry.findItemStack(id.modId, id.name, 1);
            } catch (Throwable t) {
                continue;
            }
            if (i != null) {
                CreativeTabs t = i.getItem().getCreativeTab();
                if (t != null) {
                    tabs.put(t.getTabLabel(), t);
                }
            }
        }
        for (CreativeTabs t : tabs.values()) {
            NEIINpureConfig.logger.debug("Found creative tab: " + t.getTabLabel());
        }
    }

    public String[] getTabList() {
        ArrayList<String> list = new ArrayList();
        Collections.sort(list);
        return list.toArray(new String[list.size()]);
    }

    public void set_tab(String domain, String tab) {
        ItemStack i = NEIINpureConfig.NEILib.getStack(domain);
        if (tabs.get(tab) != null && i != null) {
            i.getItem().setCreativeTab(tabs.get(tab));
        }
    }

    public void remove_tab(String domain) {
        ItemStack i = NEIINpureConfig.NEILib.getStack(domain);
        if (i == null) {
            return;
        }
        i.getItem().setCreativeTab(null);
    }
}
