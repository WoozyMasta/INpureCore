package info.inpureprojects.core.Scripting.Dynamic;

import info.inpureprojects.core.API.Scripting.IScriptingCore;

import javax.script.Invocable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by den on 7/18/2014.
 */
public class DynamicFactory {

    public static final DynamicFactory instance = new DynamicFactory();

    public Object create(IScriptingCore core, Object obj, Class Interface) {
        try {
            DynamicHandler h = new DynamicHandler(core, obj);
            return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{Interface}, h);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static class DynamicHandler implements InvocationHandler {

        private IScriptingCore core;
        private Object scriptClass;

        public DynamicHandler(IScriptingCore core, Object scriptClass) {
            this.core = core;
            this.scriptClass = scriptClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            try {
                if (core.getEngine() instanceof Invocable) {
                    Invocable invoc = (Invocable) core.getEngine();
                    return invoc.invokeMethod(this.scriptClass, method.getName(), args);
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
