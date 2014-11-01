package info.inpureprojects.core.Scripting;

import com.google.common.eventbus.EventBus;
import cpw.mods.fml.common.FMLCommonHandler;
import info.inpureprojects.core.API.Events.EventScriptError;
import info.inpureprojects.core.API.Scripting.CanBeNull;
import info.inpureprojects.core.API.Scripting.ExposedObject;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.IScriptingManager;
import info.inpureprojects.core.API.Scripting.Toc.TocManager;
import info.inpureprojects.core.API.Utils.Downloader;
import info.inpureprojects.core.API.Utils.Streams;
import info.inpureprojects.core.Client.ScriptModContainer;
import info.inpureprojects.core.Preloader.INpurePreLoader;
import info.inpureprojects.core.Scripting.Objects.Exposed.Console;
import net.minecraftforge.common.config.Configuration;
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
    private static final boolean[] injected = new boolean[2];
    private ScriptEngine engine;
    private ArrayList<TocManager.TableofContents> loaded = new ArrayList();
    private EventBus bus = new EventBus();
    private IScriptingManager.SupportedLanguages lang;
    private Configuration config;

    static {
        injected[0] = false;
        injected[1] = false;
    }

    public ScriptingCore(IScriptingManager.SupportedLanguages lang) {
        this.lang = lang;
        if (this.lang.equals(IScriptingManager.SupportedLanguages.LUA) && !injected[0]) {
            this.load_lang("luaj-jse-3.0.jar", "https://raw.githubusercontent.com/INpureProjects/INpureCore/master/libs/luaj-jse-3.0.jar", 0);
        } else if (this.lang.equals(IScriptingManager.SupportedLanguages.RUBY) && !injected[1]) {
            this.load_lang("jruby.jar", "https://raw.githubusercontent.com/INpureProjects/INpureCore/master/libs/jruby.jar", 1);
        }
    }

    private void load_lang(String file, String url, int index) {
        File f = new File(INpurePreLoader.versionFolder, file);
        if (!f.exists()) {
            Downloader.instance.download(url, f);
            INpurePreLoader.forceLoad(f);
        }
        injected[index] = true;
    }

    @Override
    public void initialize(File workingDir) {
        workingDir.mkdirs();
        for (EnumScripting s : EnumScripting.values()) {
            if (s.toString().equals(lang.toString())) {
                engine = s.getScriptEngine();
                break;
            }
        }
        engine.put("workingDir", workingDir);
        if (lang.equals(IScriptingManager.SupportedLanguages.LUA)) {
            File m = new File(workingDir, "middleclass.lua");
            Downloader.instance.download("https://raw.githubusercontent.com/kikito/middleclass/master/middleclass.lua", m);
            try {
                this.loadFile(m);
                this.loadPackagesInternal(Arrays.asList(globals));
            } catch (Throwable t) {
                System.out.println("MAJOR ERROR: Internal libraries failed to load!");
            }
        }
    }

    @Override
    @CanBeNull
    public void exposeObjects(ArrayList<ExposedObject> objects) {
        for (ExposedObject o : bundled) {
            engine.put(o.getIdentifier(), o.getObj());
        }
        if (objects != null) {
            for (ExposedObject o : objects) {
                engine.put(o.getIdentifier(), o.getObj());
            }
        }
    }

    private void loadFile(File file) {
        try {
            this.loadStream(Streams.instance.getStream(file), file.getName());
        } catch (Throwable t) {
            this.throwScriptError(t);
        }
    }

    private void loadStream(InputStream stream, String fileName) throws Exception {
        try {
            for (EnumScripting s : EnumScripting.values()) {
                if (s.isCompatible(fileName)) {
                    String script = s.getHandler().Import(stream);
                    engine.eval(script);
                    break;
                }
            }
        } catch (Throwable t) {
            this.throwScriptError(t);
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
    public void loadPackagesFromDir(File dir) {
        try {
            for (File f : FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
                if (!f.isDirectory()) {
                    if (f.getName().contains(".toc")) {
                        TocManager.TableofContents c = TocManager.instance.read(f);
                        System.out.println("Loading table of contents for module: " + c.getTitle() + ". version: " + c.getVersion());
                        FMLCommonHandler.instance().addModToResourcePack(new ScriptModContainer(c, dir, this));
                        this.getEngine().put(c.getTitle() + "_version", c.getVersion());
                        if (c.getBootstrap() != null) {
                            System.out.println("Bootstrap setting found. Loading: " + c.getBootstrap());
                            this.loadFile(new File(f.getParent() + "/" + c.getBootstrap()));
                        }
                        if (c.getSavedVariables() != null) {
                            config = new Configuration(new File(dir, c.getTitle() + ".cfg"));
                            config.load();
                            for (String s : c.getSavedVariables()) {
                                if (config.hasKey("scripting", s)) {
                                    this.getEngine().put(s, config.get("scripting", s, ""));
                                } else {
                                    this.config.get("scripting", s, this.getEngine().get(s).toString());
                                }
                            }
                            config.save();
                        }
                        for (String s : c.getScripts()) {
                            System.out.println("Loading: " + s);
                            this.loadFile(new File(f.getParent() + "/" + s));
                            loaded.add(c);
                        }
                    }
                }
            }
        } catch (Throwable t) {
            this.throwScriptError(t);
        }
    }

    private void throwScriptError(Throwable t) {
        this.bus.post(new EventScriptError(t));
    }

    @Override
    public List<TocManager.TableofContents> getLoadedModules() {
        return this.loaded;
    }

    @Override
    public ScriptEngine getEngine() {
        return engine;
    }

    @Override
    public EventBus getBus() {
        return this.bus;
    }
}
