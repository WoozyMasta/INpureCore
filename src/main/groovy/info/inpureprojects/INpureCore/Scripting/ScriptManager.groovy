package info.inpureprojects.INpureCore.Scripting

import info.inpureprojects.INpureCore.API.IScriptManager
import info.inpureprojects.INpureCore.API.IScriptingCore

/**
 * Created by den on 1/6/2015.
 */
class ScriptManager implements IScriptManager{

    @Override
    IScriptingCore createNewCore() {
        return new ScriptCore();
    }
}
