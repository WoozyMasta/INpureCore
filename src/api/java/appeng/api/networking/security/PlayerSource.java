package appeng.api.networking.security;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerSource extends BaseActionSource {

    public final EntityPlayer player;
    public final IActionHost via;

    public PlayerSource(EntityPlayer p, IActionHost v) {
        player = p;
        via = v;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

}
