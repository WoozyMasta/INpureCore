package info.inpureprojects.core.Proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Preloader.INpurePreLoader;
import info.inpureprojects.core.Scripting.Objects.Exposed.FileIO;
import info.inpureprojects.core.Utils.Streams;
import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
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
        FileIO io = new FileIO();
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        Reader r = null;
        if (INpurePreLoader.isDev) {
            File in = new File(INpurePreLoader.mc, "coreFiles.json");
            r = Streams.instance.getFileReader(in);
        } else {
            r = Streams.instance.getReader(this.getClass().getClassLoader().getResourceAsStream("coreFiles.json"));
        }
        HashMap<String, List<String>> map = json.fromJson(r, HashMap.class);
        for (String s : map.get("folders")) {
            File d = new File(INpureCore.core.getScriptFolder(), s);
            d.mkdirs();
        }
        for (String s : map.get("files")) {
            String path = s.substring(8);
            File f = new File(INpureCore.core.getScriptFolder(), path);
            io.extractFileFromJar(s, f.getAbsolutePath());
        }
    }

    @Override
    public void devStuff() {
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, List<String>> map = new HashMap();
        ArrayList<String> list = new ArrayList();
        String basePath = "scripts/inpurecore_scripts/";
        list.add(basePath + "INpureCore.toc");
        list.add(basePath + "inpurecore_main.ts");
        list.add(basePath + "resources/lang/en_US.lang");
        list.add(basePath + "resources/textures/items/radical_edward.png");
        map.put("files", list);
        ArrayList<String> dir = new ArrayList();
        dir.add("inpurecore_scripts");
        dir.add("inpurecore_scripts/resources");
        dir.add("inpurecore_scripts/resources/textures");
        dir.add("inpurecore_scripts/resources/textures/items");
        dir.add("inpurecore_scripts/resources/textures/blocks");
        dir.add("inpurecore_scripts/resources/lang");
        map.put("folders", dir);
        Writer w = Streams.instance.getWriter(new File(INpurePreLoader.mc, "coreFiles.json"));
        json.toJson(map, w);
        Streams.instance.close(w);
    }

    @Override
    public void loadJar(File file) {
        try {
            LaunchClassLoader classLoader = (LaunchClassLoader) this.getClass().getClassLoader();
            classLoader.addURL(file.toURI().toURL());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void client() {

    }

    @Override
    public void collectData() {

    }
}
