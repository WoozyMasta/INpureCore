package info.inpureprojects.INpureCore.Scripting

import info.inpureprojects.INpureCore.INpureCore
import net.minecraft.client.resources.IResourcePack
import net.minecraft.client.resources.data.IMetadataSection
import net.minecraft.client.resources.data.IMetadataSerializer
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.ModContainer

import java.awt.image.BufferedImage

/**
 * Created by den on 1/7/2015.
 */
class ResourcePackScript implements IResourcePack{

    private Set<String> set = new HashSet<String>()
    private ModContainerScript script

    public ResourcePackScript(ModContainer container) {
        set.add(container.getModId())
        script = (ModContainerScript) container;
    }

    @Override
    InputStream getInputStream(ResourceLocation loc) throws IOException {
        return new FileInputStream(this.getFileFromLoc(loc))
    }

    private File getFileFromLoc(ResourceLocation loc) {
        File firstDir = new File(script.getSource(), loc.getResourceDomain())
        File resourceDir = new File(firstDir, "/resources");
        File actualFile = new File(resourceDir, "/" + loc.getResourcePath())
        INpureCore.log.info(actualFile.getAbsolutePath())
        return actualFile
    }

    @Override
    boolean resourceExists(ResourceLocation p_110589_1_) {
        return this.getFileFromLoc(p_110589_1_).exists()
    }

    @Override
    Set getResourceDomains() {
        return set;
    }

    @Override
    IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
        return null
    }

    @Override
    BufferedImage getPackImage() throws IOException {
        return null
    }

    @Override
    String getPackName() {
        return "INpure_ScriptLoader:${set.toArray()[0]}"
    }
}
