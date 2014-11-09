package info.inpureprojects.core.Client;

import info.inpureprojects.core.API.Events.INpureEventBus;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;

/**
 * Created by den on 11/7/2014.
 */
public class ClientLogListener {

    @INpureEventBus.INpureSubscribe
    public void onEvent(EventFMLMessage evt) {
        evt.setCanceled(true);
    }

}
