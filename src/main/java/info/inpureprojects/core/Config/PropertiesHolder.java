package info.inpureprojects.core.Config;

import net.minecraftforge.common.config.Configuration;

/**
 * Created by den on 7/26/2014.
 */
public class PropertiesHolder {

    public boolean textureLoggerOverride;
    public boolean extractExamples;

    public PropertiesHolder(Configuration config) {
        config.load();
        textureLoggerOverride = config.get("tweaks", "Shut_Up_Missing_Texture_Spam", true).getBoolean();
        extractExamples = config.get("toggles", "extractExamples", true).getBoolean();
        config.get("toggles", "extractExamples", true).comment = "This controls the extraction of inpurecore_scripts.";
        config.save();
    }

}
