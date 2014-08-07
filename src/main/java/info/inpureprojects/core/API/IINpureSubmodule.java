package info.inpureprojects.core.API;

import java.io.File;

/**
 * Created by den on 8/1/2014.
 */
public interface IINpureSubmodule {

    public void pre(File configFolder);

    public void init();

    public void post();

}
