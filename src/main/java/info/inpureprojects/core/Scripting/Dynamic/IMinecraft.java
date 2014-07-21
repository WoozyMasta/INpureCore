package info.inpureprojects.core.Scripting.Dynamic;

import com.google.common.eventbus.Subscribe;
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
public interface IMinecraft {

    @Subscribe
    public void onWorldLoad(WorldEvent.Load evt);

    @Subscribe
    public void onWorldUnload(WorldEvent.Unload evt);

    @Subscribe
    public void onAnvilUpdate(AnvilUpdateEvent evt);

    @Subscribe
    public void onCommand(CommandEvent evt);

    @Subscribe
    public void onServerChat(ServerChatEvent evt);

    @Subscribe
    public void onBlockBreak(BlockEvent.BreakEvent evt);

    @Subscribe
    public void onBlockHarvest(BlockEvent.HarvestDropsEvent evt);

    @Subscribe
    public void onChunkLoad(ChunkDataEvent.Load evt);

    @Subscribe
    public void onChunkSave(ChunkDataEvent.Save evt);

    @Subscribe
    public void onNoteBlockPlay(NoteBlockEvent.Play evt);

    @Subscribe
    public void onNoteBlockChange(NoteBlockEvent.Change evt);

    @Subscribe
    public void onPotionBrewed(PotionBrewedEvent evt);
}
