package appeng.api.parts;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IPartHelper {

    /**
     * Register a new layer with the part layer system, this allows you to write
     * an in between between tile entities and parts.
     * <p/>
     * AE By Default includes,
     * <p/>
     * 1. ISidedInventory ( and by extension IInventory. )
     * <p/>
     * 2. IFluidHandler Forge Fluids
     * <p/>
     * 3. IPowerEmitter BC Power output.
     * <p/>
     * 4. IPowerReceptor BC Power input.
     * <p/>
     * 5. IEnergySink IC2 Power input.
     * <p/>
     * 6. IEnergySource IC2 Power output.
     * <p/>
     * 7. IPipeConnection BC Pipe Connections
     * <p/>
     * As long as a valid layer is registered for a interface you can simply
     * implement that interface on a part get implement it.
     *
     * @return true on success, false on failure, usually a error will be logged
     * as well.
     */
    boolean registerNewLayer(String string, String layerInterface);

    /**
     * Register IBusItem with renderer
     */
    void setItemBusRenderer(IPartItem i);

    /**
     * use in use item, to try and place a IBusItem
     *
     * @param is     ItemStack of an item which implements {@link IPartItem}
     * @param x
     * @param y
     * @param z
     * @param side
     * @param player
     * @param world
     * @return
     */
    boolean placeBus(ItemStack is, int x, int y, int z, int side, EntityPlayer player, World world);

}
