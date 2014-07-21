/// <reference path="imports.d.ts"/>

class interface_test implements IScriptEvents{

    onSave(evt : any){
        out.print("save!");
        var saveData = utils.newMap();
        saveData.put("something", "hi");
        evt.save("interfaceTest", saveData);
    }

    onLoad(evt : any){
        out.print("load!");
        var saveData = evt.load("interfaceTest");
        var s = saveData.get("something");
        out.print(s);
    }

    onReload(evt : any){
    }

}

registerEventHandler(new interface_test());