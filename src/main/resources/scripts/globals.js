function registerEventHandler(handler){
    bus.register(scriptingCore, lang, handler);
}

function registerFMLHandler(handler){
    fml.registerModLoadEvents(scriptingCore, lang, handler);
}