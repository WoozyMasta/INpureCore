package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.API.IINpureSubmodule;
import info.inpureprojects.core.API.Utils.Streams;
import info.inpureprojects.core.INpureCore;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.Reader;
import java.io.Writer;

/**
 * Created by den on 10/31/2014.
 */
public class ScriptExtractor implements IINpureSubmodule {

    private String[] files = new String[]{"AppliedEnergistics2.js", "Bibliocraft.js", "Bootstrap.js", "BuildCraft.js", "ExtraUtilities.js", "ForgeMicroblock.js", "Mekanism.js", "nei_filters.toc", "ThermalExpansion.js", "vanilla.js", "Tcon.js", "MFR.js"};

    @Override
    public void pre(File configFolder) {
        File target = new File(configFolder, "custom_nei_filters");
        if (!target.exists()) {
            target.mkdirs();
            // Begin extraction.
            INpureCore.proxy.print("Extracting script data...");
            for (String s : files) {
                INpureCore.proxy.print("Extracting: " + s + " to " + target.getAbsolutePath() + System.getProperty("file.separator") + s + ".");
                Reader r = Streams.instance.getReader(this.getClass().getClassLoader().getResourceAsStream("scripts/custom_nei_filters/" + s));
                Writer w = Streams.instance.getWriter(new File(target, s));
                try {
                    IOUtils.copy(r, w);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                Streams.instance.close(r);
                Streams.instance.close(w);
            }
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void post() {

    }
}
