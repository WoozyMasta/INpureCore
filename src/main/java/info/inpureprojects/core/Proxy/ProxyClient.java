package info.inpureprojects.core.Proxy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.FMLCommonHandler;
import info.inpureprojects.core.API.Events.EventRegisterTexture;
import info.inpureprojects.core.Client.FakeLogger;
import info.inpureprojects.core.Client.ScriptModContainer;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Scripting.Toc.TocManager;
import net.minecraft.client.renderer.texture.TextureMap;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.HashMap;


/**
 * Created by den on 7/16/2014.
 */
public class ProxyClient extends ProxyCommon {

    @Override
    public void client() {
        EventRegisterTexture.setupManager();
        this.print("Beating Minecraft's resource loading system with a shovel. Please stand by...");
        // This causes all those missing texture errors to vanish from the console. Woo!
        if (INpureCore.properties.textureLoggerOverride) {
            TextureMap.logger = new FakeLogger();
        }
        for (TocManager.TableofContents toc : INpureCore.scriptHandler.getTocs().values()) {
            this.print("Resource loading configured for script pack: " + toc.getTitle());
            FMLCommonHandler.instance().addModToResourcePack(new ScriptModContainer(toc));
        }
    }

    @Override
    public void collectData() {
        super.collectData();
        this.print("Collecting some client data...");
        HashMap<String, HashMap> bigMap = new HashMap();
        HashMap<String, String> map = new HashMap();
        try {
            for (Field f : Class.forName(net.minecraft.client.renderer.texture.TextureMap.class.getName()).getDeclaredFields()) {
                map.put(f.getName(), f.getType().getName());
            }
            Gson json = new GsonBuilder().setPrettyPrinting().create();
            Writer w = new FileWriter(new File(INpureCore.configFolder, "INpure_clientData.json"));
            bigMap.put(net.minecraft.client.renderer.texture.TextureMap.class.getName(), map);
            json.toJson(bigMap, w);
            w.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
