package info.inpureprojects.core.Common;

import info.inpureprojects.core.API.Events.INpureEventBus;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;

/**
 * Created by den on 3/29/2015.
 */
public class CommonLogListener {

    @INpureEventBus.INpureSubscribe
    public void onEvent(EventFMLMessage evt) {
        if (evt.getMessage().contains("keep up")){
            evt.setCanceled(true);
        }
    }

}
