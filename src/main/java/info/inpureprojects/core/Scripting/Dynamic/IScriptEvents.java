package info.inpureprojects.core.Scripting.Dynamic;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.API.Events.EventLoad;
import info.inpureprojects.core.API.Events.EventReloadScripts;
import info.inpureprojects.core.API.Events.EventSave;

/**
 * Created by den on 7/18/2014.
 */
public interface IScriptEvents {

    @Subscribe
    public void onSave(EventSave evt);

    @Subscribe
    public void onLoad(EventLoad evt);

    @Subscribe
    public void onReload(EventReloadScripts evt);


}
