package info.inpureprojects.core.API.Scripting;

/**
 * Created by den on 7/16/2014.
 */
public class ExposedObject {

    private String identifier;
    private Object obj;

    public ExposedObject(String identifier, Object obj) {
        this.identifier = identifier;
        this.obj = obj;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Object getObj() {
        return obj;
    }
}
