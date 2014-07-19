package info.inpureprojects.core.Scripting.Dynamic;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.Events.EventLoad;
import info.inpureprojects.core.Events.EventSave;

/**
 * Created by den on 7/18/2014.
 */
public interface IEvents {

    @Subscribe
    public void onSave(EventSave evt);

    @Subscribe
    public void onLoad(EventLoad evt);

}
