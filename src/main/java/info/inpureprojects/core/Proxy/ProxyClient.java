package info.inpureprojects.core.Proxy;

import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Utils.FilterLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ChatComponentText;


/**
 * Created by den on 7/16/2014.
 */
public class ProxyClient extends ProxyCommon {

    @Override
    public void client() {
        this.print("Beating Minecraft's resource loading system with a shovel. Please stand by...");
        // This causes all those missing texture errors to vanish from the console. Woo!
        if (INpureCore.properties.textureLoggerOverride) {
            TextureMap.logger = new FilterLogger(TextureMap.logger);
        }
    }

    @Override
    public void sendMessageToPlayer(String msg) {
        super.sendMessageToPlayer(msg);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("[INpureCore]: " + msg));
    }

    @Override
    public void onServerStartClient() {
        ((FilterLogger) TextureMap.logger).report();
    }
}
