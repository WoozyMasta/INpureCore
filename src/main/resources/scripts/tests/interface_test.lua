local events = class('events')

function events.initialize()
end

function events.onSave(EventSave)
    out:print("Lua: Save");
    map = utils:newMap()
    map:put("fromLua", "this is from lua!")
    EventSave:save("lua_interface_test", map)
end

function events.onLoad(EventLoad)
    out:print("Lua: Load");
    map = EventLoad:load("lua_interface_test");
    s = map:get("fromLua");
    out:print(s);
end

registerEventHandler(events:new())

