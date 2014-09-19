package info.inpureprojects.core.Updater;

import info.inpureprojects.core.API.IINpureSubmodule;
import info.inpureprojects.core.API.IUpdateCheck;
import info.inpureprojects.core.API.Updater;
import info.inpureprojects.core.modInfo;

import java.io.File;

/**
 * Created by den on 9/18/2014.
 */
public class UpdateModule implements IINpureSubmodule, IUpdateCheck {

    @Override
    public void pre(File configFolder) {
        Updater.register(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void post() {

    }

    @Override
    public String getVersion() {
        return modInfo.version;
    }

    @Override
    public String getModId() {
        return modInfo.modid;
    }

    @Override
    public String getModName() {
        return modInfo.name;
    }

    @Override
    public String getUpdateUrl() {
        return "https://raw.githubusercontent.com/INpureProjects/INpureCore/master/VERSION";
    }
}
