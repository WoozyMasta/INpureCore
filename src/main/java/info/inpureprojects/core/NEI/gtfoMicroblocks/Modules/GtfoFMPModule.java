package info.inpureprojects.core.NEI.gtfoMicroblocks.Modules;

import info.inpureprojects.core.NEI.gtfoMicroblocks.IGtfoModule;

/**
 * Created by den on 8/1/2014.
 */
public abstract class GtfoFMPModule implements IGtfoModule {

    public String id;

    protected GtfoFMPModule(String id) {
        this.id = id;
    }

    @Override
    public abstract void run();
}
