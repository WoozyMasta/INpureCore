package info.inpureprojects.core.Scripting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mangofactory.typescript.TypescriptCompiler;
import info.inpureprojects.core.Scripting.Objects.JavaScriptCompressor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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

    public static class tsHandler extends handler {

        public boolean typescript_warning = false;
        public TypescriptCompiler t = new TypescriptCompiler();
        private File out = new File("ScriptCache.json");
        private HashMap<String, String> cache = new HashMap();
        private Gson g = new GsonBuilder().setPrettyPrinting().create();

        @Override
        public String Import(InputStream stream) {
            boolean needsCompile = true;
            if (out.exists()) {
                try {
                    Reader r = new FileReader(out);
                    cache = g.fromJson(r, HashMap.class);
                    r.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            try {
                String in = IOUtils.toString(stream);
                String hash = DigestUtils.sha1Hex(in);
                needsCompile = !cache.containsKey(hash);
                String compressed = null;
                if (needsCompile) {
                    if (!typescript_warning) {
                        System.out.println("Warning: The typescript compiler can take a while to process each file.");
                        typescript_warning = true;
                    }
                    String conv = t.compile(in);
                    compressed = JavaScriptCompressor.compress(conv);
                    byte[] b = compressed.getBytes();
                    System.out.println("Gzipping resulting script for cache storage...");
                    ByteArrayOutputStream byteStream = new ByteArrayOutputStream(b.length);
                    GZIPOutputStream zipStream = zipStream = new GZIPOutputStream(byteStream);
                    zipStream.write(b);
                    zipStream.close();
                    byteStream.close();
                    byte[] compressedData = byteStream.toByteArray();
                    String hex = "";
                    for (byte b1 : compressedData) {
                        hex += b1;
                        hex += ",";
                    }
                    hex = hex.substring(0, hex.length() - 1);
                    cache.put(hash, hex);
                    String json = g.toJson(cache);
                    Writer w = new FileWriter(out);
                    IOUtils.write(json, w);
                    w.close();
                } else {
                    System.out.println("Found cached script. Skipping compile.");
                    String[] parse = cache.get(hash).split(",");
                    byte[] bytes = new byte[parse.length];
                    for (int i = 0; i < parse.length; i++){
                        bytes[i] = new Byte(parse[i]).byteValue();
                    }
                    ByteArrayOutputStream o = new ByteArrayOutputStream();
                    ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
                    GZIPInputStream gin = new GZIPInputStream(bin);
                    IOUtils.copy(gin, o);
                    compressed = new String(o.toByteArray());
                    o.close();
                    bin.close();
                    gin.close();
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
        public String Import(InputStream stream) {
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
