package info.inpureprojects.core.Preloader;

import info.inpureprojects.core.API.IModuleManager;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by den on 8/2/2014.
 */
public class ModuleManager implements IModuleManager {

    public static ArrayList<String> modules = new ArrayList();

    @Override
    public void register(String clazz) {
        modules.add(clazz);
    }

    @Override
    public void registerAll(String[] clazzes) {
        modules.addAll(Arrays.asList(clazzes));
    }
}
