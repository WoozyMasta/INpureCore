package info.inpureprojects.core.Client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import info.inpureprojects.core.API.Events.EventRegisterTexture;
import info.inpureprojects.core.INpureCore;
import net.minecraftforge.client.event.TextureStitchEvent;

/**
 * Created by den on 7/21/2014.
 */
public class IconManager {

    @SubscribeEvent
    public void onTexture(TextureStitchEvent.Pre evt) {
        switch (evt.map.getTextureType()) {
            case 0:
                INpureCore.core.forwardingBus.post(new EventRegisterTexture.Block(evt.map));
                break;
            case 1:
                INpureCore.core.forwardingBus.post(new EventRegisterTexture.Item(evt.map));
                break;
        }
    }

}
