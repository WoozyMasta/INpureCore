package info.inpureprojects.core.Preloader;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.API.Utils.Downloader;
import info.inpureprojects.core.Preloader.DepHandler.INpureDepHandler;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Map;

/**
 * Created by den on 7/25/2014.
 */
@IFMLLoadingPlugin.MCVersion(value = "1.7.10")
@IFMLLoadingPlugin.Name(value = "INpurePreLoader")
public class INpurePreLoader implements IFMLLoadingPlugin {

    public static boolean isDev;
    public static File mc;
    public static File source;
    private INpureDepHandler dep = new INpureDepHandler();

    public static void print(String msg) {
        System.out.println("[INpureCore|Preloader]: " + msg);
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        isDev = !((Boolean) data.get("runtimeDeobfuscationEnabled"));
        if (isDev) {
            print("We are in dev mode!");
        }
        mc = (File) data.get("mcLocation");
        File mods = new File(mc, "mods");
        File ver = new File(mods, Loader.MC_VERSION);
        if (!ver.exists()) {
            ver.mkdirs();
        }
        print("Starting library configuration...");
        for (String s : dep.readStream(this.getClass().getClassLoader().getResourceAsStream("resources.inpure"))) {
            Downloader.instance.download(s, new File(ver, FilenameUtils.getName(s)));
        }
        source = (File) data.get("coremodLocation");
        PreloaderAPI.modules = new ModuleManager();
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
