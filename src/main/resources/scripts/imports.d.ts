// Everything in this class is a dummy for something coming from the java side.
// The java stuff isn't available during compile.

// lang holds what scripting language is being used.
declare var lang : string;

declare var out;
declare var io;
declare var utils;
declare var bus;
declare var scriptingCore;

declare class EventSave{
    save(tag : string, map : any) : void;
}

// Remember that the strict typing goes out the window once we get passed the compiler.
interface IEvents{
    onSave(evt : EventSave) : void;
}

declare function registerEventHandler(handler : any);