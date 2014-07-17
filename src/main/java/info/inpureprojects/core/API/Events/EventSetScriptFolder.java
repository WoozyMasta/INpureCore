package info.inpureprojects.core.API.Events;

import java.io.File;

/**
 * Created by den on 7/17/2014.
 */
public class EventSetScriptFolder extends INpureEvent {

    private File folder;

    public EventSetScriptFolder() {
    }

    public File getFolder() {
        return folder;
    }

    public void setFolder(File folder) {
        this.folder = folder;
    }
}
