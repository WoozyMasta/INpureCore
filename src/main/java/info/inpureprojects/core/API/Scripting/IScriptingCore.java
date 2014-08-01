package info.inpureprojects.core.API.Scripting;

import info.inpureprojects.core.API.Scripting.Toc.TocManager;

import javax.script.ScriptEngine;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 8/1/2014.
 */
public interface IScriptingCore {

    public void initialize(File workingDir);

    @CanBeNull
    public void exposeObjects(ArrayList<ExposedObject> objects);

    @CanBeNull
    public void loadPackagesInternal(List<String> list) throws Exception;

    public void loadPackagesFromDir(File dir) throws Exception;

    public ScriptEngine getEngine(int ordinal);

    public List<TocManager.TableofContents> getLoadedModules();

}
