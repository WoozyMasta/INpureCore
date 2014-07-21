package.path = cache:getAbsolutePath() .. '/?.lua;' .. package.path

class = require 'middleclass'

function registerEventHandler(handler)
    bus:register(scriptingCore, lang, handler);
end

function registerFMLHandler(handler)
    fml:registerModLoadEvents(scriptingCore, lang, handler);
end

function registerMCHandler(handler)
    fml:registerMinecraftEvents(scritpingCore, lang, handler);
end

function registerItem(handler)
    fml:registerItem(scriptingCore, lang, handler);
end

