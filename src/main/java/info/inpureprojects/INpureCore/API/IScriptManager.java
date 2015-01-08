package info.inpureprojects.INpureCore.API;

/**
 * Created by den on 1/6/2015.
 */
public interface IScriptManager {

    public IScriptingCore createNewCore();

    // Used for pure asset loaders.
    public IScriptingCore createFakeCore();

}
