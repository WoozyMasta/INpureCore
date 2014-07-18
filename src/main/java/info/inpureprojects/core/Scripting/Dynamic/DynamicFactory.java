package info.inpureprojects.core.Scripting.Dynamic;

import info.inpureprojects.core.Scripting.ScriptingCore;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import javax.script.Invocable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by den on 7/18/2014.
 */
public class DynamicFactory {

    public static final DynamicFactory instance = new DynamicFactory();

    public IEvents create(ScriptingCore core, String engine, Object obj) {
        try {
            System.out.println(obj.getClass().getName());
            DynamicHandler h = new DynamicHandler(core, engine, obj);
            IEvents wrapped = (IEvents) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{IEvents.class}, h);
            return wrapped;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static class DynamicHandler implements InvocationHandler {

        private ScriptingCore core;
        private String engine;
        private Object scriptClass;

        public DynamicHandler(ScriptingCore core, String engine, Object scriptClass) {
            this.core = core;
            this.engine = engine;
            this.scriptClass = scriptClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (core.getEngine(engine) instanceof Invocable){
                Invocable invoc = (Invocable) core.getEngine(engine);
                invoc.invokeMethod(this.scriptClass, method.getName(), args);
            }else{
                // The lua engine does not implement Invocable.
                // I've made this extendable via an enum in case of supporting more languages later.
                for (specialHandlers h : specialHandlers.values()){
                    if (h.getHandler().isObjectCompatible(this.scriptClass)){
                        h.getHandler().handle(this.scriptClass, method.getName(), args[0]);
                        break;
                    }
                }
            }
            return null;
        }
    }

    public static abstract class specialHandler{

        public abstract void handle(Object o, String target, Object arg);

        public abstract boolean isObjectCompatible(Object o);

    }

    public static enum specialHandlers{

        LuaTable(new specialHandler() {
            @Override
            public void handle(Object o, String target, Object arg) {
                LuaTable table = (LuaTable) o;
                LuaValue a = table.get(target);
                LuaFunction f = (LuaFunction) a;
                f.call(CoerceJavaToLua.coerce(arg));
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


}
