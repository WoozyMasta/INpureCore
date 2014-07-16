package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.Events.INpureHandler;
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
        InputStream s = this.getClass().getClassLoader().getResourceAsStream("scripts/javascript_test.js");
        core.importStream(s, "scripts/javascript_test.js");
        //
        s = this.getClass().getClassLoader().getResourceAsStream("scripts/typescript_test.ts");
        core.importStream(s, "scripts/typescript_test.ts");
        //
        s = this.getClass().getClassLoader().getResourceAsStream("scripts/lua_test.lua");
        core.importStream(s, "scripts/lua_test.lua");
    }
}