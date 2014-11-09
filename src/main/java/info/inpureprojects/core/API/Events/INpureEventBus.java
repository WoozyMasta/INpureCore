package info.inpureprojects.core.API.Events;

import cpw.mods.fml.common.eventhandler.Event;
import info.inpureprojects.core.Preloader.INpurePreLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by den on 11/8/2014.
 */

// Fuck it. We'll implement our own eventbus because the google one keeps derping out.
public class INpureEventBus {

    private CopyOnWriteArrayList<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    public void register(Object o) {
        Listener l = new Listener(o);
        for (Method m : o.getClass().getDeclaredMethods()) {
            if (m.getAnnotation(INpureSubscribe.class) != null) {
                if (m.getParameterTypes().length > 1) {
                    INpurePreLoader.print("Cannot have an event handler with more than 1 parameter!");
                    return;
                }
                Class eventType = m.getParameterTypes()[0];
                l.handlers.put(eventType, m);
            }
        }
        listeners.add(l);
    }

    public void unregister(Object o) {
        listeners.remove(o);
    }

    public void post(Object evt) {
        if (!(evt instanceof Event)) {
            INpurePreLoader.print("Cannot post object that does not extend event base class!");
        }
        for (Listener l : listeners) {
            l.handleEvent(evt);
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface INpureSubscribe {
    }

    public static class Listener {

        private Object instance;
        private HashMap<Class, Method> handlers = new HashMap();

        public Listener(Object instance) {
            this.instance = instance;
        }

        public void handleEvent(Object evt) {
            if (handlers.containsKey(evt.getClass())) {
                try {
                    handlers.get(evt.getClass()).invoke(this.instance, evt);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

}
