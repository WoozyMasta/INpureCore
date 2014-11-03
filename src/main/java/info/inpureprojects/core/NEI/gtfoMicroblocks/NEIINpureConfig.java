package info.inpureprojects.core.NEI.gtfoMicroblocks;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.google.common.eventbus.Subscribe;
import cpw.mods.fml.common.Loader;
import info.inpureprojects.core.API.Events.EventScriptError;
import info.inpureprojects.core.API.INpureAPI;
import info.inpureprojects.core.API.PreloaderAPI;
import info.inpureprojects.core.API.Scripting.ExposedObject;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.IScriptingManager;
import info.inpureprojects.core.API.Utils.LogWrapper;
import info.inpureprojects.core.API.Utils.Streams;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects.*;
import info.inpureprojects.core.Scripting.TestException;
import info.inpureprojects.core.Utils.Events.EventNEIReady;
import info.inpureprojects.core.modInfo;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by den on 8/1/2014.
 */
public class NEIINpureConfig implements IConfigureNEI {

    public static NEIINpureConfig instance;
    public static boolean loaded = false;
    public static IScriptingCore scripting;
    public static List<String> reg;
    private static File logs = new File(INpureCore.dir, "logs");
    public static final LogWrapper logger = new LogWrapper(LogManager.getLogger("INpureCullingEngine"), new File(logs, "debug.log"));
    private static int errorCount = 0;

    public NEIINpureConfig() {
        if (!logs.exists()) {
            logs.mkdirs();
        }
    }

    public static ArrayList<ItemStack> buildStackList(ItemStack stack, int[] metas) {
        ArrayList<ItemStack> stacks = new ArrayList();
        for (int i : metas) {
            stacks.add(new ItemStack(stack.getItem(), 1, i));
        }
        return stacks;
    }

    @Deprecated
    public static void hideBlock(Block b) {
        logger.info("Get off my curb you good for nothin! (Hiding " + b.getUnlocalizedName() + ")");
        ItemStack block = new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE);
        API.hideItem(block);
    }

    private void registryEntryPoint(List<String> list) {
        if (INpureCore.properties.dump_registry_to_debug_log) {
            logger.debug("----------------------------------");
            logger.debug("Dumping GameRegistry to debug log.");
            logger.debug("----------------------------------");
            for (String s : list) {
                logger.debug(s);
            }
            logger.debug("----------------------------------");
        }
        reg = list;
    }

    @Subscribe
    public void onScriptError(EventScriptError evt) {
        INpureCore.proxy.sendMessageToPlayer("A script error has occured. A log file has been created in config/INpureProjects/logs.");
        String fileName = new SimpleDateFormat("yyyyMMddhhmm").format(new Date()).concat("-").concat(String.valueOf(this.errorCount++).concat(".txt"));
        PrintWriter w = Streams.instance.getFilePrintWriter(new File(logs, fileName));
        evt.getT().printStackTrace(w);
        Streams.instance.close(w);
    }

    public static void startReloadProcess(){
        INpureCore.proxy.sendMessageToPlayer("Starting script reload process. Please stand by...");
        scripting.getBus().unregister(instance);
        scripting.shutdown();
        scripting = null;
        loaded = false;
        instance.loadConfig();
        INpureCore.proxy.sendMessageToPlayer("Reload complete.");
    }

    @Override
    public void loadConfig() {
        if (loaded) {
            return;
        }
        if (instance == null){
            instance = this;
        }
        logger.info("Starting NEI Filter scripting. This might take a moment to load all the modules...");
        EventNEIReady r = new EventNEIReady();
        PreloaderAPI.preLoaderEvents.post(r);
        this.registryEntryPoint(r.getList());
        File working = new File(INpureCore.dir, "custom_nei_filters");
        scripting = INpureAPI.manager.create(IScriptingManager.SupportedLanguages.JAVASCRIPT);
        scripting.getBus().register(this);
        scripting.initialize(working, logger);
        ArrayList<ExposedObject> obj = new ArrayList();
        // Load default modules
        obj.add(new ExposedObject("java", new JavaObject()));
        obj.add(new ExposedObject("FML", new FMLObject()));
        obj.add(new ExposedObject("NEI", new NEIObject()));
        // Load custom modules only if proper mod is also loaded.
        if (Loader.isModLoaded("ForgeMicroblock")) {
            obj.add(new ExposedObject("ForgeMicroblock", new ForgeMicroblockObject()));
        }
        if (Loader.isModLoaded("ExtraUtilities")) {
            obj.add(new ExposedObject("ExtraUtilities", new ExtraUtilitiesObject()));
        }
        if (Loader.isModLoaded("BuildCraft|Transport")) {
            obj.add(new ExposedObject("BC", new BCObject()));
        }
        if (Loader.isModLoaded("appliedenergistics2")) {
            obj.add(new ExposedObject("AE2", new AEObject()));
        }
        scripting.exposeObjects(obj);
        scripting.loadPackagesFromDir(working);
        loaded = true;
    }

    private void doTestError(String msg) {
        try {
            throw new TestException(msg);
        } catch (Throwable t) {
            scripting.getBus().post(new EventScriptError(t));
        }
    }

    @Override
    public String getName() {
        return modInfo.name;
    }

    @Override
    public String getVersion() {
        return modInfo.version;
    }

}
