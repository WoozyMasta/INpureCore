package info.inpureprojects.core.Scripting.Dynamic;

import info.inpureprojects.core.Scripting.ScriptingCore;

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
            DynamicHandler h = new DynamicHandler(core.getInvocable(engine), obj);
            IEvents wrapped = (IEvents) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{IEvents.class}, h);
            return wrapped;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static class DynamicHandler implements InvocationHandler {

        private Invocable invoc;
        private Object scriptClass;

        public DynamicHandler(Invocable invoc, Object scriptClass) {
            this.invoc = invoc;
            this.scriptClass = scriptClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            invoc.invokeMethod(this.scriptClass, method.getName(), args);
            return null;
        }
    }


}
