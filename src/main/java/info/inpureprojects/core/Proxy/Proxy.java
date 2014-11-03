package info.inpureprojects.core.Proxy;

/**
 * Created by den on 7/16/2014.
 */
public abstract class Proxy {

    public abstract void warning(String msg);

    public abstract void print(String msg);

    public abstract void severe(String msg);

    public abstract void setupAPI();

    public abstract void client();

    public abstract void sendMessageToPlayer(String msg);

    public abstract void onServerStartClient();

}
