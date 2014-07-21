package info.inpureprojects.core.Client;

import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.Scripting.Toc.TocManager;
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

    @Override
    public InputStream getInputStream(ResourceLocation loc) throws IOException {
        try {
            File actualFile = this.getFileFromLoc(loc);
            return new FileInputStream(actualFile);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private File getFileFromLoc(ResourceLocation loc) {
        File firstDir = new File(INpureCore.core.getScriptFolder(), loc.getResourceDomain());
        TocManager.TableofContents toc = INpureCore.scriptHandler.getTocs().get(loc.getResourceDomain());
        File resourceDir = new File(firstDir, toc.getResources() + "/textures");
        File actualFile = new File(resourceDir, loc.getResourcePath() + ".png");
        return actualFile;
    }

    @Override
    public boolean resourceExists(ResourceLocation p_110589_1_) {
        return this.getFileFromLoc(p_110589_1_).exists();
    }

    @Override
    public Set getResourceDomains() {
        return null;
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
        return null;
    }
}
