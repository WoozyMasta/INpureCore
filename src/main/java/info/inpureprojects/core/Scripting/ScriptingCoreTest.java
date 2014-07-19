package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.Events.INpureHandler;
import info.inpureprojects.core.Utils.Timer;
import junit.framework.TestCase;

import java.io.File;
import java.io.InputStream;

public class ScriptingCoreTest extends TestCase {

    private static ScriptingCore core = new ScriptingCore();

    static {
        core.bus.register(new INpureHandler(new File(System.getProperty("user.dir"))));
        core.doSetup();
    }

    public void testImportStream() throws Exception {
        String[] args = new String[]{"scripts/tests/javascript_test.js", "scripts/tests/typescript_test.ts", "scripts/tests/lua_test.lua", "scripts/tests/interface_test.ts",
                "scripts/tests/interface_test.lua"};
        Timer t = new Timer();
        // Script init test
        for (String s : args) {
            t.start();
            InputStream st = this.getClass().getClassLoader().getResourceAsStream(s);
            core.importStream(st, s);
            t.stop();
            t.announce("Test: " + s);
        }
        // Save test
        core.doSave();
        // Load test
        core.doLoad();
    }

    public void testLoadTocs() throws Exception {
        System.out.println("---------------------------------------");
        System.out.println("Starting table of contents unit test...");
        System.out.println("---------------------------------------");
        core.runInternalScript("scripts/tests/setup_toc_test.js");
        core.loadScripts();
        System.out.println("---------------------------------------");
    }
}