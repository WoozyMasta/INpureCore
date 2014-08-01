package info.inpureprojects.core.Config;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by den on 7/26/2014.
 */
public class PropertiesHolder {

    public boolean textureLoggerOverride;

    public PropertiesHolder(Configuration config) {
        config.load();
        textureLoggerOverride = config.get("tweaks", "Shut_Up_Missing_Texture_Spam", true).getBoolean();
        config.save();
    }

}
