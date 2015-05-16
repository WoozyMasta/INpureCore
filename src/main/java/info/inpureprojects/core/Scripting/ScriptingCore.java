package info.inpureprojects.core.Scripting;

import cpw.mods.fml.common.FMLCommonHandler;
import info.inpureprojects.core.API.Events.EventScriptError;
import info.inpureprojects.core.API.Events.INpureEventBus;
import info.inpureprojects.core.API.Scripting.CanBeNull;
import info.inpureprojects.core.API.Scripting.ExposedObject;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.IScriptingManager;
import info.inpureprojects.core.API.Scripting.Toc.TocManager;
import info.inpureprojects.core.API.Utils.Downloader;
import info.inpureprojects.core.API.Utils.LogWrapper;
import info.inpureprojects.core.API.Utils.Streams;
import info.inpureprojects.core.Client.ScriptModContainer;
import info.inpureprojects.core.Preloader.INpurePreLoader;
import info.inpureprojects.core.Scripting.Objects.Exposed.Console;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.script.ScriptEngine;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by den on 7/16/2014.
 */
public class ScriptingCore implements IScriptingCore {

    private static final ExposedObject[] bundled = new ExposedObject[]{new ExposedObject("out", new Console())};
    private static HashMap<String, LangSupport> supported = new HashMap<String, LangSupport>();
    private ScriptEngine engine;
    private ArrayList<TocManager.TableofContents> loaded = new ArrayList();
    private INpureEventBus bus = new INpureEventBus();
    private IScriptingManager.SupportedLanguages lang;
    private Configuration config;
    private LogWrapper logger;

    public ScriptingCore(IScriptingManager.SupportedLanguages lang) {
        this.lang = lang;
    }

    @Override
    public boolean shutdown() {
        logger.info("Shutdown request received!");
        this.engine = null;
        this.loaded.clear();
        this.loaded.trimToSize();
        this.loaded = null;
        this.bus = null;
        this.lang = null;
        this.config = null;
        logger.info("Shutdown complete!");
        this.logger = null;
        return true;
    }

    private void load_lang(String file, String url) {
        File f = new File(INpurePreLoader.versionFolder, file);
        if (!f.exists()) {
            Downloader.instance.download(url, f);
            INpurePreLoader.forceLoad(f);
        }
    }

    @Override
    public void initialize(File workingDir, LogWrapper logger) {
        this.logger = logger;
        workingDir.mkdirs();
        for (EnumScripting s : EnumScripting.values()) {
            if (s.toString().equals(lang.toString())) {
                if (supported.containsKey(s.toString().toLowerCase())) {
                    LangSupport l = supported.get(s.toString().toLowerCase());
                    load_lang(l.getFileName(), l.getUrl().toString());
                    if (l.isHasSecondaryDep()) {
                        load_lang(l.getSecondaryFileName(), l.getSecondaryUrl().toString());
                    }
                }
                logger.info("Engine: %s", s.toString());
                engine = s.getScriptEngine();
                break;
            }
        }
        engine.put("workingDir", workingDir);
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
    @CanBeNull
    public void loadSinglePackageInternal(String scriptFile) throws Exception {
        if (scriptFile != null) {
            this.loadPackagesInternal(Arrays.asList(new String[]{scriptFile}));
        }
    }

    @Override
    public void loadScriptFromURL(URL url) {
        InputStream in = null;
        try {
            in = url.openStream();
            this.engine.eval(IOUtils.toString(in));
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    @Override
    public void loadPackagesFromDir(File dir) {
        try {
            for (File f : FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
                if (!f.isDirectory()) {
                    if (f.getName().contains(".toc")) {
                        TocManager.TableofContents c = TocManager.instance.read(f);
                        logger.info("Loading table of contents for module: %s, %s, Author: %s", c.getTitle(), c.getVersion(), c.getAuthor());
                        FMLCommonHandler.instance().addModToResourcePack(new ScriptModContainer(c, dir, this));
                        this.getEngine().put(c.getTitle() + "_version", c.getVersion());
                        if (c.getBootstrap() != null) {
                            logger.info("Bootstrap setting found. Loading: %s", c.getBootstrap());
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
                            logger.info("Loading: %s", s);
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
    public INpureEventBus getBus() {
        return this.bus;
    }
}
