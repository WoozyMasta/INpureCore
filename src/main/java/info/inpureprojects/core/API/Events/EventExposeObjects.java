package info.inpureprojects.core.API.Events;

import info.inpureprojects.core.Scripting.Objects.ExposedObject;

import java.util.ArrayList;

/**
 * Created by den on 7/16/2014.
 */
public class EventExposeObjects extends INpureEvent{

    private ArrayList<ExposedObject> exposedObjects;

    public EventExposeObjects(ArrayList<ExposedObject> exposedObjects) {
        this.exposedObjects = exposedObjects;
    }

    public ArrayList<ExposedObject> getExposedObjects() {
        return exposedObjects;
    }
}
