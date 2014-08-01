package info.inpureprojects.core.Client;

import cpw.mods.fml.common.ModContainer;
import info.inpureprojects.core.API.Toc.TocManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by den on 7/21/2014.
 */
public class ResourcePackScript implements IResourcePack {

    private Set<String> set = new HashSet<String>();
    private ScriptModContainer script;

    public ResourcePackScript(ModContainer container) {
        set.add(container.getModId());
        script = (ScriptModContainer) container;
    }

    @Override
    public InputStream getInputStream(ResourceLocation loc) throws IOException {
        try {
            return new FileInputStream(this.getFileFromLoc(loc));
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private File getFileFromLoc(ResourceLocation loc) {
        File firstDir = new File(script.getSource(), loc.getResourceDomain());
        TocManager.TableofContents toc = null;
        for (TocManager.TableofContents c : script.getCore().getLoadedModules()) {
            if (c.getTitle().equals(loc.getResourceDomain())) {
                toc = c;
                break;
            }
        }
        File resourceDir = new File(firstDir, "/resources");
        File actualFile = new File(resourceDir, "/" + loc.getResourcePath());
        return actualFile;
    }

    @Override
    public boolean resourceExists(ResourceLocation p_110589_1_) {
        return this.getFileFromLoc(p_110589_1_).exists();
    }

    @Override
    public Set getResourceDomains() {
        return set;
    }

    @Override
    public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
        return null;
    }

    @Override
    public BufferedImage getPackImage() throws IOException {
        return null;
    }

    @Override
    public String getPackName() {
        return "INpure_ScriptLoader:" + set.toArray()[0];
    }
}
