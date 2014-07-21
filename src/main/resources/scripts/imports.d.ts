// Everything in this class is a dummy for something coming from the java side.
// The java stuff isn't available during compile.

// lang holds what scripting language is being used.
declare var lang : string;
declare var scriptFolder;
declare var saveFolder;

declare var out;
declare var io;
declare var utils;
declare var bus;
declare var fml;

// Please don't play with this variable. It is here for registering stuff to the forward bus.
// Use the helper methods instead of using this directly.
declare var scriptingCore;

// These interfaces are just mock-ups. You don't have to implement them.
// Just make sure your object has whatever methods you want with exact spelling/case.

interface IScriptEvents{
    onSave(evt : any) : void;
    onLoad(evt : any) : void;
    onReload(evt : any) : void;
}

interface IFML{
    onPreInit(evt : any) : void;
    onInit(evt : any) : void;
    onPostInit(evt : any) : void;
}

interface IMinecraft{
    onBlockBreak(evt : any) : void;
    onWorldLoad(evt : any) : void;
    onWorldUnload(evt : any) : void;
    onAnvilUpdate(evt : any) : void;
    onCommand(evt : any) : void;
    onServerChat(evt : any) : void;
    onBlockHarvest(evt : any) : void;
    onChunkLoad(evt : any) : void;
    onChunkSave(evt : any) : void;
    onNoteBlockPlay(evt : any) : void;
    onNoteBlockChange(evt : any) : void;
    onPotionBrewed(evt : any) : void;
}

// Consider these sort of like abstract methods.
// They are implemented in globals.js
declare function registerEventHandler(handler : any);
declare function registerFMLHandler(handler : any);
declare function registerMCHandler(handler: any);