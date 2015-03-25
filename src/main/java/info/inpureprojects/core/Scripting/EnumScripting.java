package info.inpureprojects.core.Scripting;

import info.inpureprojects.core.Preloader.JavaDetection;
import info.inpureprojects.core.Scripting.Objects.JavaScriptCompressor;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;

/**
 * Created by den on 7/16/2014.
 */
public enum EnumScripting {

    JAVASCRIPT(".js", JavaDetection.detectJava().JavaScript_Callsign, new jsHandler());
    public static ScriptEngineManager m;
    private String extension;
    private String engine;
    private handler handler;

    static {
        m = new ScriptEngineManager(null);
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
        public abstract String Import(InputStream stream);
    }

    public static class jsHandler extends handler {

        @Override
        public String Import(InputStream stream) {
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

}
