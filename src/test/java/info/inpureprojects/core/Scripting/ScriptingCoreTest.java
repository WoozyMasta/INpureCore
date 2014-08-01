package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.Scripting.Dynamic.DynamicFactory;
import junit.framework.TestCase;

import java.io.File;
import java.util.Arrays;

public class ScriptingCoreTest extends TestCase {

    public static final File testDir = new File("Tests");
    private IScriptingCore core;

    public void setUp() throws Exception {
        super.setUp();
        core = new ScriptingCore();
        core.initialize(testDir);
        core.exposeObjects(null);
    }

    public void testLoadPackagesInternal() throws Exception {
        core.loadPackagesInternal(Arrays.asList(new String[]{"scripts/Test.lua"}));
        ITestInterface t = (ITestInterface) DynamicFactory.instance.create(core, 1, core.getEngine(1).get("testInstance"), ITestInterface.class);
        t.doStuff();
        t.doStuffWithArgs("one", "two");
    }

    public void testloadPackagesFromDir() throws Exception {
        System.out.println("Testing external load...");
        core.loadPackagesFromDir(new File(testDir, "packages"));
    }

}