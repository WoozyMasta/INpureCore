package info.inpureprojects.core.Minecraft;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.API.Events.EventExposeObjects;
import info.inpureprojects.core.Scripting.Objects.ExposedObject;

import java.util.ArrayList;

/**
 * Created by den on 7/17/2014.
 */
public class MinecraftHandler {

    private ArrayList<ExposedObject> objs = new ArrayList();

    public MinecraftHandler() {
        objs.add(new ExposedObject("fml", new FMLWrapper()));
    }

    @Subscribe
    public void onExpose(EventExposeObjects evt) {
        evt.getExposedObjects().addAll(objs);
    }

}
