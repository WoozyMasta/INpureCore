package info.inpureprojects.core.API;

import com.google.common.eventbus.EventBus;

/**
 * Created by den on 8/2/2014.
 */
public class PreloaderAPI {

    public static IModuleManager modules;
    public static EventBus preLoaderEvents = new EventBus();

    public static boolean isDev() {
        try {
            return (Boolean) Class.forName("info.inpureprojects.core.Preloader.INpurePreLoader").getDeclaredField("isDev").get(null);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return false;
    }

}
