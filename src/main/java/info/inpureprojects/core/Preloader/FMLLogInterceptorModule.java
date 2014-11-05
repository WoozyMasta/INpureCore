package info.inpureprojects.core.Preloader;

import info.inpureprojects.core.API.IINpureSubmoduleExpanded;

import java.io.File;

/**
 * Created by den on 11/5/2014.
 */
public class FMLLogInterceptorModule implements IINpureSubmoduleExpanded{

    @Override
    public void onServerAboutToStart() {
        try{
            INpurePreLoader.fmlLogInterceptor.unhook();
        }catch(Throwable t){
            t.printStackTrace();
        }
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
