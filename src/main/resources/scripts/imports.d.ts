// Everything in this class is a dummy for something coming from the java side.
// The java stuff isn't available during compile.

// lang holds what scripting language is being used.
declare var lang : string;
// These are normal java file objects and you can invoke their methods just like java.
// e.g. scriptFolder.getAbsolutePath() to get the path as a string, or scriptFolder.getParent() to go up a directory.
declare var scriptFolder;
declare var saveFolder;

// out contains a print method to write to the console.
declare var out;
// io: See https://github.com/INpureProjects/INpureCore/blob/master/src/main/java/info/inpureprojects/core/Scripting/Objects/Exposed/FileIO.java
// You can invoke any of the methods in the linked class for all of these just like they are java objects.
declare var io;
// utils: See https://github.com/INpureProjects/INpureCore/blob/master/src/main/java/info/inpureprojects/core/Scripting/Objects/Exposed/DataTypes.java
declare var utils;
// bus: don't use this variable directly. Use the helper methods at the bottom. See https://github.com/INpureProjects/INpureCore/blob/master/src/main/resources/scripts/globals.js for implementations of the helpers.
declare var bus;
// fml: See https://github.com/INpureProjects/INpureCore/blob/master/src/main/java/info/inpureprojects/core/Minecraft/FMLWrapper.java
declare var fml;
// worlds: getWorldByName(name) or getWorldById(id). Supported names are "overworld", "nether", and "end". Returns the World java object.
declare var worlds;

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
    onBlockTextures(evt : any) : void;
    onItemTextures(evt : any) : void;
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

interface IScriptableItem{
    getUnlocalizedName() : string;
    getIcon(item : any) : any;
}

// Consider these sort of like abstract methods.
// They are implemented in globals.js
declare function registerEventHandler(handler : any);
declare function registerFMLHandler(handler : any);
declare function registerMCHandler(handler: any);
declare function registerItem(handler : any);