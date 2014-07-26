/// <reference path="imports.d.ts"/>
var inpurecore_scripts_workspace;
var inpurecore_map;
var INpureCore;
//------------
var inpurecore_icons;
var inpurecore_test;

class inpurecore_mcHandler{
     onBlockBreak(evt : any){
     }
}

class inpurecore_testItem implements IScriptableItem{

    getUnlocalizedName(){
        return "inpurecore.testItem";
    }

    getIcon(item : any){
        return inpurecore_icons.get("edward");
    }

}
// Remember: None of these events are called on reload. Don't depend on them for anything but interacting with Minecraft during startup.
class inpurecore_FMLHandler implements IFML{

    onPreInit(evt : any){
        inpurecore_icons = utils.newMap();
    }

    onInit(evt : any){
        inpurecore_test = registerItem(new inpurecore_testItem());
    }

    onPostInit(evt : any){
    }

    onItemTextures(evt : any){
        inpurecore_icons.put("edward", evt.register("inpurecore_scripts:radical_edward"));
    }

    onBlockTextures(evt : any){
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



