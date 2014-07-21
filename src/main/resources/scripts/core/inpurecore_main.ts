/// <reference path="imports.d.ts"/>
var inpurecore_workspace;
var inpurecore_map;
var INpureCore;

class inpurecore_mcHandler{
     onBlockBreak(evt : any){
     }
}

// Remember: None of these events are called on reload. Don't depend on them for anything but interacting with Minecraft during startup.
class inpurecore_FMLHandler implements IFML{

    onPreInit(evt : any){
    }

    onInit(evt : any){
        inpurecore_map.put("commons-codec-1.9.jar", io.getHash(scriptFolder.getParent() + "/libs/commons-codec-1.9.jar"));
        inpurecore_map.put("js.jar", io.getHash(scriptFolder.getParent() + "/libs/js.jar"));
        inpurecore_map.put("junit.jar", io.getHash(scriptFolder.getParent() + "/libs/junit.jar"));
        inpurecore_map.put("luaj-jse-3.0.jar", io.getHash(scriptFolder.getParent() + "/libs/luaj-jse-3.0.jar"));
        inpurecore_map.put("slf4j-api-1.7.7.jar", io.getHash(scriptFolder.getParent() + "/libs/slf4j-api-1.7.7.jar"));
        inpurecore_map.put("slf4j-simple-1.7.7.jar", io.getHash(scriptFolder.getParent() + "/libs/slf4j-simple-1.7.7.jar"));
        inpurecore_map.put("typescript4j-0.5.0-SNAPSHOT.jar", io.getHash(scriptFolder.getParent() + "/libs/typescript4j-0.5.0-SNAPSHOT.jar"));
    }

    onPostInit(evt : any){

    }
}

class inpurecore_dataHandler implements IScriptEvents{
     onSave(evt : any){
         evt.save("inpurecore", inpurecore_map);
     }

     onLoad(evt: any){
         inpurecore_map = evt.load("inpurecore");
     }

     onReload(evt : any){
        INpureCore.print("Reload event detected!");
     }
 }

class inpurecore_functions{
    print(msg : string){
        out.print("[INpureCore|Script]: " + msg);
    }

    setup(){
        registerFMLHandler(new inpurecore_FMLHandler());
        registerEventHandler(new inpurecore_dataHandler());
        registerMCHandler(new inpurecore_mcHandler());
        this.print("ready to go!");
    }
}

INpureCore = new inpurecore_functions();
INpureCore.setup();



