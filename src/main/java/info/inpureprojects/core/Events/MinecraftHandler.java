package info.inpureprojects.core.Events;

import com.google.common.eventbus.Subscribe;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Created by den on 7/17/2014.
 */
public class MinecraftHandler {

    @Subscribe
    public void onBlockBreak(BlockEvent.BreakEvent evt) {

    }

}
