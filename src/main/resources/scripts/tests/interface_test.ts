/// <reference path="imports.d.ts"/>

class interface_test implements IEvents{

    onSave(evt : any){
        out.print("save!");
        var saveData = utils.newMap();
        saveData.put("something", "hi");
        evt.save("interfaceTest", saveData);
    }

}

registerEventHandler(new interface_test());