package info.inpureprojects.core.API.Events;

import info.inpureprojects.core.Client.IconManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by den on 7/21/2014.
 */
public class EventRegisterTexture extends INpureEvent {

    private static IconManager icon_manager = new IconManager();
    private TextureMap manager;

    public EventRegisterTexture(TextureMap manager) {
        this.manager = manager;
    }

    public static void setupManager() {
        MinecraftForge.EVENT_BUS.register(icon_manager);
    }

    public IIcon register(String path) {
        return this.manager.registerIcon(path);
    }

    public TextureMap getManager() {
        return manager;
    }

    public static class Item extends EventRegisterTexture {
        public Item(TextureMap manager) {
            super(manager);
        }
    }

    public static class Block extends EventRegisterTexture {
        public Block(TextureMap manager) {
            super(manager);
        }
    }

}
