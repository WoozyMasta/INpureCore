package info.inpureprojects.core.CoFH;

import cofh.mod.updater.UpdateManager;
import info.inpureprojects.core.API.IINpureSubmodule;
import info.inpureprojects.core.INpureCore;

import java.io.File;

/**
 * Created by den on 8/27/2014.
 */
public class ModuleUpdater implements IINpureSubmodule{

    @Override
    public void pre(File configFolder) {
        UpdateManager.registerUpdater(new UpdateManager(INpureCore.instance, "https://raw.githubusercontent.com/INpureProjects/INpureCore/master/VERSION"));
    }

    @Override
    public void init() {

    }

    @Override
    public void post() {

    }
}
