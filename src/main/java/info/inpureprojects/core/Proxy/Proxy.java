package info.inpureprojects.core.Proxy;

import java.io.File;

/**
 * Created by den on 7/16/2014.
 */
public abstract class Proxy {

    public abstract void warning(String msg);

    public abstract void print(String msg);

    public abstract void severe(String msg);

    public abstract void registerOnAllBuses(Object o);

    public abstract void extractCore();

    public abstract void loadJar(File file);

    public abstract void client();

    public abstract void collectData();

    public abstract void devStuff();

}
