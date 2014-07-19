package.path = cache:getAbsolutePath() .. '/?.lua;' .. package.path

class = require 'middleclass'

function registerEventHandler(handler)
    bus:register(scriptingCore, lang, handler);
end

function registerFMLHandler(handler)
    fml:registerModLoadEvents(scriptingCore, lang, handler);
end

