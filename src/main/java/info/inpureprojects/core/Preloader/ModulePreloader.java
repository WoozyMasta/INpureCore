package info.inpureprojects.core.Preloader;

import info.inpureprojects.core.API.IINpureSubmoduleExpanded;

import java.io.File;

/**
 * Created by den on 11/7/2014.
 */
public class ModulePreloader implements IINpureSubmoduleExpanded {

    @Override
    public void onServerAboutToStart() {
        INpurePreLoader.fmlLogInterceptor.unhook();
    }

    @Override
    public void pre(File configFolder) {

    }

    @Override
    public void init() {

    }

    @Override
    public void post() {

    }
}
