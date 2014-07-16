package info.inpureprojects.core.Scripting;

import com.mangofactory.typescript.CompilationContext;
import com.mangofactory.typescript.TypescriptCompiler;
import info.inpureprojects.core.Scripting.Objects.JavaScriptCompressor;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.InputStream;

/**
 * Created by den on 7/16/2014.
 */
public enum EnumScripting {

    JAVASCRIPT(".js", "JavaScript", new jsHandler()),
    TYPESCRIPT(".ts", "JavaScript", new tsHandler()),
    LUA(".lua", "lua", new luaHandler());

    static{
        System.out.println("------------------------------------------------");
        System.out.println("The following is not an error please ignore it!");
        System.out.println("------------------------------------------------");
        m = new ScriptEngineManager();
    }

    public static ScriptEngineManager m;
    private String extension;
    private String engine;
    private handler handler;

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

    public boolean isCompatible(String fileName){
        return fileName.contains(extension);
    }

    public ScriptEngine getScriptEngine(){
        return m.getEngineByName(this.engine);
    }

    public abstract static class handler{
        public abstract String Import(InputStream stream);
    }

    public static class jsHandler extends handler{

        @Override
        public String Import(InputStream stream) {
            try{
                String in = IOUtils.toString(stream);
                String compressed = JavaScriptCompressor.compress(in);
                return compressed;
            }catch(Throwable t){
                t.printStackTrace();
            }
            return null;
        }
    }

    public static class tsHandler extends handler{

        @Override
        public String Import(InputStream stream) {
            try{
                String in = IOUtils.toString(stream);
                TypescriptCompiler t = new TypescriptCompiler();
                System.out.println("Starting the Typescript compiler. This might take a moment...");
                String conv = t.compile(in);
                String compressed = JavaScriptCompressor.compress(conv);
                return compressed;
            }catch(Throwable t){
                t.printStackTrace();
            }
            return null;
        }
    }

    public static class luaHandler extends handler{

        @Override
        public String Import(InputStream stream) {
            try{
                String in = IOUtils.toString(stream);
                return in;
            }catch(Throwable t){
                t.printStackTrace();
            }
            return null;
        }
    }
}
