package info.inpureprojects.core.Scripting;

import com.google.common.eventbus.EventBus;
import info.inpureprojects.core.API.Events.EventExposeObjects;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Scripting.Objects.ExposedObject;

import javax.script.ScriptEngine;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by den on 7/16/2014.
 */
public class ScriptingCore{

    private HashMap<String, ScriptEngine> engines = new HashMap();
    private ArrayList<ExposedObject> exposedObjects = new ArrayList();
    public EventBus bus = new EventBus();

    public void doSetup(){
        this.setupObjects();
        this.setupSupportedEngines();
    }

    private void setupObjects(){
        bus.post(new EventExposeObjects(exposedObjects));
    }

    private void setupSupportedEngines(){
        for (EnumScripting s : EnumScripting.values()){
            if (!engines.containsKey(s)){
                engines.put(s.getEngine(), s.getScriptEngine());
                for (ExposedObject o : exposedObjects){
                    engines.get(s.getEngine()).put(o.getIdentifier(), o.getObj());
                }
            }
        }
    }

    public void importFile(File file){
        try{
            FileInputStream f = new FileInputStream(file);
            this.importStream(f, file.getName());
        }catch(Throwable t){
            t.printStackTrace();
        }
    }

    public void importStream(InputStream stream, String fileName){
        for (EnumScripting s : EnumScripting.values()){
            if (s.isCompatible(fileName)){
                String script = s.getHandler().Import(stream);
                try{
                    engines.get(s.getEngine()).eval(script);
                }catch(Throwable t){
                    t.printStackTrace();
                }
            }
        }
    }

}
