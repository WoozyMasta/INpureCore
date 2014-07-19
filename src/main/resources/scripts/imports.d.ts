// Everything in this class is a dummy for something coming from the java side.
// The java stuff isn't available during compile.

// lang holds what scripting language is being used.
declare var lang : string;

declare var out;
declare var io;
declare var utils;
declare var bus;
declare var scriptingCore;

interface IEvents{
    onSave(evt : any) : void;
    onLoad(evt : any) : void;
}

declare function registerEventHandler(handler : any);