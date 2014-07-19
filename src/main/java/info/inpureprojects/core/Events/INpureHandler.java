package info.inpureprojects.core.Events;

import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import info.inpureprojects.core.API.Events.*;
import info.inpureprojects.core.Scripting.Objects.Exposed.Console;
import info.inpureprojects.core.Scripting.Objects.Exposed.DataTypes;
import info.inpureprojects.core.Scripting.Objects.Exposed.EventBus;
import info.inpureprojects.core.Scripting.Objects.Exposed.FileIO;
import info.inpureprojects.core.Scripting.Objects.ExposedObject;
import info.inpureprojects.core.Scripting.Toc.TocManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by den on 7/16/2014.
 */
public class INpureHandler {

    private ArrayList<ExposedObject> objs = new ArrayList();
    private File scriptFolder;
    private File saveFolder;
    private Gson json = new GsonBuilder().setPrettyPrinting().create();

    public INpureHandler(File scriptFolder) {
        this.scriptFolder = new File(scriptFolder, "Scripts");
        this.saveFolder = new File(this.scriptFolder, "Saves");
        objs.add(new ExposedObject("out", new Console()));
        objs.add(new ExposedObject("io", new FileIO()));
        objs.add(new ExposedObject("scriptFolder", this.scriptFolder));
        objs.add(new ExposedObject("saveFolder", this.saveFolder));
        objs.add(new ExposedObject("utils", new DataTypes()));
        objs.add(new ExposedObject("bus", new EventBus()));
    }

    @Subscribe
    public void onExposure(EventExposeObjects evt) {
        evt.getExposedObjects().addAll(objs);
    }

    @Subscribe
    public void setScriptFolder(EventSetScriptFolder evt) {
        scriptFolder.mkdirs();
        evt.setFolder(scriptFolder);
    }

    @Subscribe
    public void setSaveFolder(EventSetSaveFolder evt) {
        saveFolder.mkdirs();
        evt.setFolder(saveFolder);
    }

    @Subscribe
    public void saveToFile(EventSaveComplete evt) {
        for (String s : evt.getMap().keySet()) {
            String fileName = s + ".json";
            try {
                FileWriter w = new FileWriter(new File(this.saveFolder, fileName));
                json.toJson(evt.getMap().get(s), w);
                w.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    @Subscribe
    public void startLoad(EventStartLoad evt) {
        for (File f : saveFolder.listFiles()) {
            if (!f.isDirectory()) {
                if (f.getName().contains(".json")) {
                    try {
                        FileReader r = new FileReader(f);
                        HashMap map = json.fromJson(r, HashMap.class);
                        String name = f.getName().replace(".json", "");
                        evt.getMap().put(name, map);
                        r.close();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        }
    }

    @Subscribe
    public void loadScripts(EventLoadScripts evt) {
        for (File f : FileUtils.listFiles(this.scriptFolder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            if (!f.isDirectory()) {
                if (f.getName().contains(".toc")) {
                    TocManager.TableofContents c = TocManager.instance.read(f);
                    System.out.println("Loading table of contents for module: " + c.getTitle() + ". version: " + c.getVersion());
                    for (String s : c.getScripts()) {
                        System.out.println("Loading: " + s);
                        File file = new File(f.getParent() + "/" + s);
                        evt.getCore().manuallyExposeObjectToAll(new ExposedObject(c.getTitle() + "_workspace", new File(f.getParent())));
                        evt.getCore().importFile(file);
                    }
                }
            }
        }
    }
}
