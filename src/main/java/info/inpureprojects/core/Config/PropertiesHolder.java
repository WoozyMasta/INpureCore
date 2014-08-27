package info.inpureprojects.core.Config;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by den on 7/26/2014.
 */
public class PropertiesHolder {

    public boolean textureLoggerOverride;
    public boolean hideCrapFromNEI;
    public boolean hideVanillaCrapFromNEI;
    public boolean updateCheck;

    public PropertiesHolder(Configuration config) {
        config.load();
        textureLoggerOverride = config.get("tweaks", "Shut_Up_Missing_Texture_Spam", true).getBoolean();
        hideCrapFromNEI = config.get("tweaks", "Hide_Microblocks_From_NEI", true).getBoolean();
        config.get("tweaks", "Hide_Microblocks_From_NEI", true).comment = "This includes ForgeMicroblocks, ExtraUtils microblocks, BC Facades, and AE Facades.";
        hideVanillaCrapFromNEI = config.get("tweaks", "Hide_VanillaCrap_From_NEI", true).getBoolean();
        config.get("tweaks", "Hide_VanillaCrap_From_NEI", true).comment = "Mob spawners, portals, fire, and still liquid source blocks.";
        updateCheck = config.get("updater", "check", true).getBoolean();
        config.save();
    }

}
