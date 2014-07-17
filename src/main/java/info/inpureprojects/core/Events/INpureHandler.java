package info.inpureprojects.core.Events;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.API.Events.EventExposeObjects;
import info.inpureprojects.core.API.Events.EventSetScriptFolder;
import info.inpureprojects.core.Scripting.Objects.Exposed.Console;
import info.inpureprojects.core.Scripting.Objects.Exposed.FileIO;
import info.inpureprojects.core.Scripting.Objects.ExposedObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by den on 7/16/2014.
 */
public class INpureHandler {

    private ArrayList<ExposedObject> objs = new ArrayList();
    private File folder;

    public INpureHandler(File folder) {

        this.folder = new File(folder, "Script_Cache");
        objs.add(new ExposedObject("out", new Console()));
        objs.add(new ExposedObject("io", new FileIO()));
        objs.add(new ExposedObject("scriptFolder", this.folder));
    }

    @Subscribe
    public void onExposure(EventExposeObjects evt) {
        evt.getExposedObjects().addAll(objs);
    }

    @Subscribe
    public void setScriptFolder(EventSetScriptFolder evt) {
        folder.mkdirs();
        evt.setFolder(folder);
    }

}
