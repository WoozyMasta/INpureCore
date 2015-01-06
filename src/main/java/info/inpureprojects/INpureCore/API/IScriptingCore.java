package info.inpureprojects.INpureCore.API;

import java.io.File;

/**
 * Created by den on 1/6/2015.
 */
public interface IScriptingCore {

    public void loadTocs(File folder);

    public void loadInternalScript(String path);

    public void loadInternalToc(String path);

}
