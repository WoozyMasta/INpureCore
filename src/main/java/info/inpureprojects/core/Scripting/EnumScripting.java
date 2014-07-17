package info.inpureprojects.core.Scripting;

import com.mangofactory.typescript.TypescriptCompiler;
import info.inpureprojects.core.Scripting.Objects.JavaScriptCompressor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by den on 7/16/2014.
 */
public enum EnumScripting {

    JAVASCRIPT(".js", "JavaScript", new jsHandler()),
    TYPESCRIPT(".ts", "JavaScript", new tsHandler()),
    LUA(".lua", "lua", new luaHandler());
    public static ScriptEngineManager m;
    private String extension;
    private String engine;
    private handler handler;

    static {
        System.out.println("------------------------------------------------");
        System.out.println("The following is not an error please ignore it!");
        System.out.println("------------------------------------------------");
        m = new ScriptEngineManager();
    }

    EnumScripting(String extension, String engine, handler h) {
        this.extension = extension;
        this.engine = engine;
        this.handler = h;
    }

    public String getEngine() {
        return engine;
    }

    public EnumScripting.handler getHandler() {
        return handler;
    }

    public boolean isCompatible(String fileName) {
        return fileName.contains(extension);
    }

    public ScriptEngine getScriptEngine() {
        return m.getEngineByName(this.engine);
    }

    public abstract static class handler {
        public abstract String Import(InputStream stream, File scriptPath);
    }

    public static class jsHandler extends handler {

        @Override
        public String Import(InputStream stream, File scriptPath) {
            try {
                String in = IOUtils.toString(stream);
                String compressed = JavaScriptCompressor.compress(in);
                return compressed;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }

    public static class tsHandler extends handler {

        public boolean typescript_warning = false;
        public TypescriptCompiler t = new TypescriptCompiler();

        @Override
        public String Import(InputStream stream, File scriptPath) {
            boolean needsCompile = true;
            try {
                String in = IOUtils.toString(stream);
                String hash = DigestUtils.sha1Hex(in);
                File out = new File(scriptPath, hash + ".js");
                needsCompile = !out.exists();
                String compressed = null;
                String conv = null;
                if (needsCompile) {
                    if (!typescript_warning) {
                        System.out.println("Warning: The typescript compiler can take a while to process each file.");
                        typescript_warning = true;
                    }
                    t.compile(in, scriptPath, out);
                    FileInputStream s = new FileInputStream(out);
                    conv = IOUtils.toString(s);
                    s.close();
                    compressed = JavaScriptCompressor.compress(conv);
                } else {
                    System.out.println("Found cached script. Skipping compile.");
                    FileInputStream s = new FileInputStream(out);
                    conv = IOUtils.toString(s);
                    s.close();
                    compressed = JavaScriptCompressor.compress(conv);
                }
                return compressed;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }

    public static class luaHandler extends handler {

        @Override
        public String Import(InputStream stream, File scriptPath) {
            try {
                String in = IOUtils.toString(stream);
                return in;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }
    }
}
