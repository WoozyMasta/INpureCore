package info.inpureprojects.core.Minecraft;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.HashMap;

/**
 * Created by den on 7/21/2014.
 */
public class DimManager {

    private HashMap<String, Integer> map = new HashMap();

    public DimManager() {
        map.put("overworld", 0);
        map.put("nether", -1);
        map.put("end", 1);
    }

    public World getWorldByName(String name) {
        return this.getWorldById(map.get(name));
    }

    public World getWorldById(int id) {
        return DimensionManager.getWorld(id);
    }
}
