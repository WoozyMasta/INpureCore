package info.inpureprojects.core.Client;

import com.google.common.eventbus.Subscribe;
import info.inpureprojects.core.Utils.Events.EventFMLMessage;

/**
 * Created by den on 11/7/2014.
 */
public class ClientLogListener {

    @Subscribe
    public void onEvent(EventFMLMessage evt) {
        evt.setCanceled(true);
    }

}
