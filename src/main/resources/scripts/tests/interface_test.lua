local events = class('events')

function events:initialize()
end

function events.onSave(EventSave)
    map = utils:newMap()
    map:put("fromLua", "this is from lua!")
    EventSave:save("lua_interface_test", map)
end

registerEventHandler(events:new())

