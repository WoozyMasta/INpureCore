package info.inpureprojects.core.Events;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.API.Events.EventExposeObjects;
import info.inpureprojects.core.Scripting.Objects.Exposed.Console;
import info.inpureprojects.core.Scripting.Objects.ExposedObject;

import java.util.ArrayList;

/**
 * Created by den on 7/16/2014.
 */
public class INpureHandler {

    private ArrayList<ExposedObject> objs = new ArrayList();

    public INpureHandler() {
        objs.add(new ExposedObject("out", new Console()));
    }

    @Subscribe
    public void onExposure(EventExposeObjects evt){
        evt.getExposedObjects().addAll(objs);
    }

}
