package info.inpureprojects.core.Scripting.Dynamic;

import info.inpureprojects.core.API.Scripting.IScriptingCore;
import info.inpureprojects.core.INpureCore;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import javax.script.Invocable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;

/**
 * Created by den on 7/18/2014.
 */
public class DynamicFactory {

    public static final DynamicFactory instance = new DynamicFactory();

    public Object create(IScriptingCore core, int engine, Object obj, Class Interface) {
        try {
            DynamicHandler h = new DynamicHandler(core, engine, obj);
            return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Interface}, h);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static enum specialHandlers {

        LuaTable(new specialHandler() {
            @Override
            public void handle(Object o, String target, Object[] arg) {
                LuaTable table = (LuaTable) o;
                LuaValue a = table.get(target);
                LuaFunction f = (LuaFunction) a;
                if (arg == null){
                    f.invoke(table);
                }else{
                    ArrayList<LuaValue> list = new ArrayList();
                    list.add(table);
                    for (Object o1 : arg){
                        list.add(CoerceJavaToLua.coerce(o1));
                    }
                    f.invoke(list.toArray(new LuaValue[list.size()]));
                }
            }

            @Override
            public boolean isObjectCompatible(Object o) {
                return (o.getClass().getName().equals("org.luaj.vm2.LuaTable"));
            }
        });

        private specialHandler handler;

        specialHandlers(specialHandler handler) {
            this.handler = handler;
        }

        public specialHandler getHandler() {
            return handler;
        }
    }

    public static class DynamicHandler implements InvocationHandler {

        private IScriptingCore core;
        private int engine;
        private Object scriptClass;

        public DynamicHandler(IScriptingCore core, int engine, Object scriptClass) {
            this.core = core;
            this.engine = engine;
            this.scriptClass = scriptClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                if (core.getEngine(engine) instanceof Invocable) {
                    Invocable invoc = (Invocable) core.getEngine(engine);
                    return invoc.invokeMethod(this.scriptClass, method.getName(), args);
                } else {
                    // The lua engine does not implement Invocable.
                    // I've made this extendable via an enum in case of supporting more languages later.
                    for (specialHandlers h : specialHandlers.values()) {
                        if (h.getHandler().isObjectCompatible(this.scriptClass)) {
                            h.getHandler().handle(this.scriptClass, method.getName(), args);
                            break;
                        }
                    }
                }
            } catch (Throwable t) {
            }
            return null;
        }
    }

    public static abstract class specialHandler {

        public abstract void handle(Object o, String target, Object[] arg);

        public abstract boolean isObjectCompatible(Object o);

    }


}
