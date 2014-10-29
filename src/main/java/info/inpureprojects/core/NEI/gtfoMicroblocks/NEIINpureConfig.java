package info.inpureprojects.core.NEI.gtfoMicroblocks;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import cpw.mods.fml.common.Loader;
import info.inpureprojects.core.API.INpureAPI;
import info.inpureprojects.core.API.Scripting.ExposedObject;
import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.API.Scripting.IScriptingManager;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects.*;
import info.inpureprojects.core.modInfo;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by den on 8/1/2014.
 */
public class NEIINpureConfig implements IConfigureNEI {

    //public static final String[] supported = new String[]{"ForgeMicroblock", "ExtraUtilities", "BuildCraft|Transport", "appliedenergistics2", "BiblioCraft", "ThermalExpansion", "Mekanism"};
    public static boolean loaded = false;
    public static IScriptingCore scripting;

    public static ArrayList<ItemStack> buildStackList(ItemStack stack, int[] metas) {
        ArrayList<ItemStack> stacks = new ArrayList();
        for (int i : metas) {
            stacks.add(new ItemStack(stack.getItem(), 1, i));
        }
        return stacks;
    }

    public static void hideBlock(Block b) {
        INpureCore.proxy.print("Get off my curb you good for nothin! (Hiding " + b.getUnlocalizedName() + ")");
        ItemStack block = new ItemStack(b, 1, OreDictionary.WILDCARD_VALUE);
        API.hideItem(block);
    }

    @Override
    public void loadConfig() {
        if (loaded) {
            return;
        }
        INpureCore.proxy.print("Starting NEI Filter scripting. This might take a moment to load all the modules...");
        File working = new File(INpureCore.dir, "custom_nei_filters");
        scripting = INpureAPI.manager.create(IScriptingManager.SupportedLanguages.JAVASCRIPT);
        scripting.initialize(working);
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
        if (Loader.isModLoaded("appliedenergistics2")){
            obj.add(new ExposedObject("AE2", new AEObject()));
        }
        scripting.exposeObjects(obj);
        try {
            scripting.loadPackagesFromDir(working);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        loaded = true;

        // TODO: Remove old code after a few versions.
//        if (INpureCore.properties.hideVanillaCrapFromNEI) {
//            IGtfoModule vanilla = new Vanilla();
//            vanilla.run();
//        }
//        if (INpureCore.properties.hideCrapFromNEI) {
//            for (String s : supported) {
//                try {
//                    if (Loader.isModLoaded(s)) {
//                        IGtfoModule module = (IGtfoModule) Class.forName("info.inpureprojects.core.NEI.gtfoMicroblocks.Modules.".concat(s.replace("|", ""))).getConstructor(new Class[]{String.class}).newInstance(new Object[]{this.getRandomMaterial()});
//                        module.run();
//                    }
//                } catch (Throwable t) {
//                    INpureCore.proxy.warning("Failed to load NEI Cleanup Module: " + s);
//                }
//            }
//        }
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
