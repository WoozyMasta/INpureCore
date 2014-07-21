package info.inpureprojects.core.Minecraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import info.inpureprojects.core.INpureCore;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.WorldEvent;

/**
 * Created by den on 7/19/2014.
 */
public class ForgeHandler {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent evt){
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onCommand(CommandEvent evt){
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent evt){
        INpureCore.core.forwardingBus.post(evt);
    }
}
