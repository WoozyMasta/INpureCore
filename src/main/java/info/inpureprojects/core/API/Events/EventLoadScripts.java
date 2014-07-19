package info.inpureprojects.core.API.Events;

import info.inpureprojects.core.Scripting.ScriptingCore;

/**
 * Created by den on 7/19/2014.
 */
public class EventLoadScripts {

    private ScriptingCore core;

    public EventLoadScripts(ScriptingCore core) {
        this.core = core;
    }

    public ScriptingCore getCore() {
        return core;
    }
}
