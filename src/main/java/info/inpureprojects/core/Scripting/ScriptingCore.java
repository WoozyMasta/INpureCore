package info.inpureprojects.core.Scripting;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.FMLCommonHandler;
import info.inpureprojects.core.API.Scripting.CanBeNull;
import info.inpureprojects.core.API.Scripting.ExposedObject;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.Toc.TocManager;
import info.inpureprojects.core.API.Utils.Downloader;
import info.inpureprojects.core.API.Utils.Streams;
import info.inpureprojects.core.Client.ScriptModContainer;
import info.inpureprojects.core.Scripting.Objects.Exposed.Console;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.script.ScriptEngine;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by den on 7/16/2014.
 */
public class ScriptingCore implements IScriptingCore {

    private static final String[] globals = new String[]{"scripts/env.lua"};
    private static final ExposedObject[] bundled = new ExposedObject[]{new ExposedObject("out", new Console())};
    private ScriptEngine[] engines = new ScriptEngine[2];
    private ArrayList<TocManager.TableofContents> loaded = new ArrayList();
    private EventBus bus = new EventBus();

    @Override
    public void initialize(File workingDir) {
        workingDir.mkdirs();
        for (EnumScripting s : EnumScripting.values()) {
            engines[s.ordinal()] = s.getScriptEngine();
            engines[s.ordinal()].put("workingDir", workingDir);
        }
        File m = new File(workingDir, "middleclass.lua");
        Downloader.instance.download("https://raw.githubusercontent.com/kikito/middleclass/master/middleclass.lua", m);
        try {
            this.loadFile(m);
            this.loadPackagesInternal(Arrays.asList(globals));
        } catch (Throwable t) {
            System.out.println("MAJOR ERROR: Internal libraries failed to load!");
        }
    }

    @Override
    @CanBeNull
    public void exposeObjects(ArrayList<ExposedObject> objects) {
        for (ScriptEngine s : engines) {
            for (ExposedObject o : bundled) {
                s.put(o.getIdentifier(), o.getObj());
            }
            if (objects != null) {
                for (ExposedObject o : objects) {
                    s.put(o.getIdentifier(), o.getObj());
                }
            }
        }
    }

    private void loadFile(File file) throws Exception {
        this.loadStream(Streams.instance.getStream(file), file.getName());
    }

    private void loadStream(InputStream stream, String fileName) throws Exception {
        for (EnumScripting s : EnumScripting.values()) {
            if (s.isCompatible(fileName)) {
                String script = s.getHandler().Import(stream);
                engines[s.ordinal()].eval(script);
                break;
            }
        }
    }

    @Override
    @CanBeNull
    public void loadPackagesInternal(List<String> list) throws Exception {
        if (list != null) {
            for (String str : list) {
                this.loadStream(this.getClass().getClassLoader().getResourceAsStream(str), str);
            }
        }
    }

    @Override
    public void loadPackagesFromDir(File dir) throws Exception {
        for (File f : FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            if (!f.isDirectory()) {
                if (f.getName().contains(".toc")) {
                    TocManager.TableofContents c = TocManager.instance.read(f);
                    System.out.println("Loading table of contents for module: " + c.getTitle() + ". version: " + c.getVersion());
                    for (String s : c.getScripts()) {
                        System.out.println("Loading: " + s);
                        File file = new File(f.getParent() + "/" + s);
                        this.loadFile(file);
                        loaded.add(c);
                        try {
                            FMLCommonHandler.instance().addModToResourcePack(new ScriptModContainer(c, dir, this));
                            System.out.println("Resource loading configured for script pack: " + c.getTitle());
                        } catch (Throwable t) {
                            // This is for test cases.
                        }
                    }
                }
            }
        }
    }

    @Override
    public ScriptEngine getEngine(int ordinal) {
        return engines[ordinal];
    }

    @Override
    public List<TocManager.TableofContents> getLoadedModules() {
        return this.loaded;
    }
}
