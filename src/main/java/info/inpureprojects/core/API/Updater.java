package info.inpureprojects.core.API;

/**
 * Created by den on 9/19/2014.
 */
public class Updater {

    public static void register(IUpdateCheck update){
        try{
            Class.forName("info.inpureprojects.core.INpureCore").getDeclaredMethod("registerManager", new Class[]{IUpdateCheck.class}).invoke(null, new Object[]{update});
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

}
