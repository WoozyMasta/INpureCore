function registerEventHandler(handler){
    bus.register(scriptingCore, lang, handler);
}

function registerFMLHandler(handler){
    fml.registerModLoadEvents(scriptingCore, lang, handler);
}

function registerMCHandler(handler){
    fml.registerMinecraftEvents(scriptingCore, lang, handler);
}

function registerItem(handler){
    fml.registerItem(scriptingCore, lang, handler);
}

function include(path){
    scriptingCore.importPath(path);
}