/// <reference path="imports.d.ts"/>

class inpurecore_FMLHandler implements IFML{
    onPreInit(evt : any){
        out.print("Pre!");
    }

    onInit(evt : any){
        out.print("Init!");
    }

    onPostInit(evt : any){
        out.print("Post!");
    }
}

registerFMLHandler(new inpurecore_FMLHandler());