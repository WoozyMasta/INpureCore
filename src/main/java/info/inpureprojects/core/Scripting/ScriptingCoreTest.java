package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.Events.EventLoad;
import info.inpureprojects.core.Events.EventSave;
import info.inpureprojects.core.Events.INpureHandler;
import info.inpureprojects.core.Utils.Timer;
import junit.framework.TestCase;

import java.io.File;
import java.io.InputStream;

public class ScriptingCoreTest extends TestCase {

    private static ScriptingCore core = new ScriptingCore();

    static {
        core.bus.register(new INpureHandler(new File(".")));
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
        EventSave s = new EventSave();
        System.out.println("Posting events!");
        core.bus.post(s);
        // Load test
        EventLoad l = new EventLoad(s.getMap());
        core.bus.post(l);
    }
}