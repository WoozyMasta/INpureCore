package info.inpureprojects.core.Utils.Events;

import cpw.mods.fml.common.eventhandler.Event;

import java.util.List;

/**
 * Created by den on 11/3/2014.
 */
public class EventNEIReady extends Event {

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
