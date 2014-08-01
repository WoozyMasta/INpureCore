package info.inpureprojects.core.API.Scripting;

/**
 * Created by den on 8/1/2014.
 */
public interface IScriptingManager {

    public IScriptingCore create();

    /**
     * This method takes the parameters and returns an object of the interface type.
     * The object represents the script. We're assuming the object has functions that match the method names of the interface.
     *
     * @Param core reference to your script core
     * @Param engine id of the engine type. 0 = js. 1 = lua.
     * @Param obj Script generated object. Should be a class/singleton. Lua classes are tables.
     * @Return Wrapped script object. You'll need to cast it to your interface.
     */
    public Object WrapScript(IScriptingCore core, int engine, Object obj, Class Interface);

}
