package info.inpureprojects.core.API.Scripting;

import info.inpureprojects.core.API.Events.INpureEventBus;
import info.inpureprojects.core.API.Scripting.Toc.TocManager;
import info.inpureprojects.core.API.Utils.LogWrapper;

import javax.script.ScriptEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 8/1/2014.
 */
public interface IScriptingCore {

    public void initialize(File workingDir, LogWrapper logger);

    @CanBeNull
    public void exposeObjects(ArrayList<ExposedObject> objects);

    @CanBeNull
    public void loadPackagesInternal(List<String> list) throws Exception;

    @CanBeNull
    public void loadSinglePackageInternal(String scriptFile) throws Exception;

    public void loadPackagesFromDir(File dir);

    public List<TocManager.TableofContents> getLoadedModules();

    public ScriptEngine getEngine();

    public INpureEventBus getBus();

    public boolean shutdown();

}
