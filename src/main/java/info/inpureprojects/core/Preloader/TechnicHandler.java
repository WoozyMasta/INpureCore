package info.inpureprojects.core.Preloader;

import cpw.mods.fml.common.registry.GameData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by den on 2/16/2015.
 */
public class TechnicHandler {

    public static final TechnicHandler instance = new TechnicHandler();

    public void reparse(ArrayList<String> list) {
        Set<String> set = new HashSet<String>();
        set.addAll((Set<String>) GameData.getItemRegistry().getKeys());
        set.addAll((Set<String>) GameData.getBlockRegistry().getKeys());
        for (String key : set) {
            list.add(key);
        }
    }

}
