package info.inpureprojects.core.Minecraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import info.inpureprojects.core.INpureCore;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.brewing.PotionBrewedEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
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
    public void onAnvilUpdate(AnvilUpdateEvent evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onCommand(CommandEvent evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onServerChat(ServerChatEvent evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkDataEvent.Load evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onChunkSave(ChunkDataEvent.Save evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onNoteBlockPlay(NoteBlockEvent.Play evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onNoteBlockChange(NoteBlockEvent.Change evt) {
        INpureCore.core.forwardingBus.post(evt);
    }

    @SubscribeEvent
    public void onPotionBrewed(PotionBrewedEvent evt) {
        INpureCore.core.forwardingBus.post(evt);
    }
}
