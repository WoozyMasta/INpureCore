/// <reference path="imports.d.ts"/>

var inpurecore_workspace;
var inpurecore_map;

class inpurecore_FMLHandler implements IFML{
    onPreInit(evt : any){
    }

    onInit(evt : any){
        inpurecore_generate_libs_sha1();
    }

    onPostInit(evt : any){

    }
}

class inpurecore_dataHandler implements IEvents{
    onSave(evt : any){
        evt.save("inpurecore", inpurecore_map);
    }

    onLoad(evt: any){
        inpurecore_map = evt.load("inpurecore");
    }
}

function inpurecore_generate_libs_sha1(){
    inpurecore_map.put("commons-codec-1.9.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/commons-codec-1.9.jar"));
    inpurecore_map.put("js.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/js.jar"));
    inpurecore_map.put("junit.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/junit.jar"));
    inpurecore_map.put("luaj-jse-3.0.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/luaj-jse-3.0.jar"));
    inpurecore_map.put("slf4j-api-1.7.7.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/slf4j-api-1.7.7.jar"));
    inpurecore_map.put("slf4j-simple-1.7.7.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/slf4j-simple-1.7.7.jar"));
    inpurecore_map.put("typescript4j-0.5.0-SNAPSHOT.jar", io.getHash(inpurecore_workspace.getAbsolutePath() + "/libs/typescript4j-0.5.0-SNAPSHOT.jar"));
}

registerFMLHandler(new inpurecore_FMLHandler());
registerEventHandler(new inpurecore_dataHandler());