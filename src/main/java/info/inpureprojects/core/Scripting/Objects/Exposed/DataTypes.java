package info.inpureprojects.core.Scripting.Objects.Exposed;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by den on 7/18/2014.
 */
public class DataTypes {

    public Object[] newArray(int size) {
        return new Object[size];
    }

    public HashMap<Object, Object> newMap() {
        return new HashMap();
    }

    public ArrayList<Object> newList() {
        return new ArrayList();
    }

}
