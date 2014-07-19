// Everything in this class is a dummy for something coming from the java side.
// The java stuff isn't available during compile.

// lang holds what scripting language is being used.
declare var lang : string;

declare var out;
declare var io;
declare var utils;
declare var bus;
declare var scriptingCore;
declare var fml;

interface IEvents{
    onSave(evt : any) : void;
    onLoad(evt : any) : void;
}

interface IFML{
    onPreInit(evt : any) : void;
    onInit(evt : any) : void;
    onPostInit(evt : any) : void;
}

// Consider these sort of like abstract methods.
// They are implemented in globals.js
declare function registerEventHandler(handler : any);
declare function registerFMLHandler(handler : any);