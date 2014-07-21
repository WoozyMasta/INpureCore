package info.inpureprojects.core.Scripting.Objects.Exposed;

import info.inpureprojects.core.Scripting.Dynamic.DynamicFactory;
import info.inpureprojects.core.Scripting.Dynamic.IScriptEvents;
import info.inpureprojects.core.Scripting.ScriptingCore;

/**
 * Created by den on 7/18/2014.
 */
public class EventBus {

    public void register(ScriptingCore core, String engine, Object o) {
        core.forwardingBus.register(DynamicFactory.instance.create(core, engine, o, IScriptEvents.class));
    }

}
