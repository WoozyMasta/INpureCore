package info.inpureprojects.core.Proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Utils.Downloader;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by den on 7/16/2014.
 */
public class ProxyCommon extends Proxy {

    @Override
    public void warning(String msg) {
        FMLLog.warning("[INpureCore]: " + msg);
    }

    @Override
    public void print(String msg) {
        FMLLog.info("[INpureCore]: " + msg);
    }

    @Override
    public void severe(String msg) {
        FMLLog.severe("[INpureCore]: " + msg);
    }

    @Override
    public void registerOnAllBuses(Object o) {
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
        INpureCore.core.bus.register(o);
    }

    @Override
    public void extractCore() {
        INpureCore.core.runInternalScript("scripts/core/extract_core.js");
    }

    @Override
    public void loadJar(File file) {
        try{
            LaunchClassLoader classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
            classLoader.addURL(file.toURI().toURL());
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

    @Override
    public void downloadLibs() {
        String partial = "https://raw.githubusercontent.com/INpureProjects/INpureCore/master/libs/";
        File list = new File(INpureCore.configFolder, "lib_list.txt");
        File lib = new File(INpureCore.configFolder, "libs");
        lib.mkdirs();
        Downloader.instance.download(partial + "lib_list.txt", list.getAbsolutePath());
        try{
            InputStream in = new FileInputStream(list);
            List<String> a = IOUtils.readLines(in);
            for (String s : a){
                File n = new File(lib, s);
                Downloader.instance.download(partial + s, n.getAbsolutePath());
                this.loadJar(n);
            }
            list.deleteOnExit();
            in.close();
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
}
