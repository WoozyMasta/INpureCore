package.path = scriptFolder:getAbsolutePath() .. '/?.lua;' .. package.path

class = require 'middleclass'

function registerEventHandler(handler)
    bus:register(scriptingCore, lang, handler);
end

