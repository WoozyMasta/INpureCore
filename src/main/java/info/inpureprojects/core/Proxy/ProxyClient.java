package info.inpureprojects.core.Proxy;

import info.inpureprojects.core.Client.ClientLogListener;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Utils.Loggers.EventFilter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.apache.logging.log4j.core.Logger;


/**
 * Created by den on 7/16/2014.
 */
public class ProxyClient extends ProxyCommon {

    @Override
    public void client() {
        this.print("Beating Minecraft's resource loading system with a shovel. Please stand by...");
        // This causes all those missing texture errors to vanish from the console. Woo!
        if (INpureCore.properties.textureLoggerOverride) {
            EventFilter f = new EventFilter();
            f.getBus().register(new ClientLogListener());
            ((Logger) TextureMap.logger).addFilter(f);
        }
    }

    @Override
    public void sendMessageToPlayer(String msg) {
        super.sendMessageToPlayer(msg);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "[" + "INpureCore" + "]: ").appendText(EnumChatFormatting.WHITE + msg));
    }

    @Override
    public void onServerStartClient() {
    }
}
