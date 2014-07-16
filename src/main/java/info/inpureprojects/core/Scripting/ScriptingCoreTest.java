package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.Events.INpureHandler;
import info.inpureprojects.core.Utils.Timer;
import junit.framework.TestCase;

import java.io.InputStream;

public class ScriptingCoreTest extends TestCase {

    private ScriptingCore core = new ScriptingCore();

    public ScriptingCoreTest() {
        core.bus.register(new INpureHandler());
        core.doSetup();
    }

    public void testImportFile() throws Exception {
    }

    public void testImportStream() throws Exception {
        String[] args = new String[]{"scripts/javascript_test.js", "scripts/typescript_test.ts", "scripts/lua_test.lua", "scripts/typescript_test2.ts"};
        Timer t = new Timer();
        for (String s : args) {
            t.start();
            InputStream st = this.getClass().getClassLoader().getResourceAsStream(s);
            core.importStream(st, s);
            t.stop();
            t.announce("Test: " + s);
        }
    }
}