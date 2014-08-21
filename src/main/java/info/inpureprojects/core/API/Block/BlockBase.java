package info.inpureprojects.core.API.Block;

import info.inpureprojects.core.API.MovedFrom;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by den on 8/7/2014.
 */
@MovedFrom(mod = "OpenBees")
public abstract class BlockBase extends BlockContainer {

    private boolean hasGUI;
    private int idShift = 0;
    private Object modInstance;

    public BlockBase(String unloc) {
        super(Material.clay);
        this.setHardness(1.0f);
        this.setBlockName(unloc);
        this.setup();
    }

    public void setModInstance(Object modInstance) {
        this.modInstance = modInstance;
    }

    public abstract void setup();

    public void setHasGUI(boolean hasGUI) {
        this.hasGUI = hasGUI;
    }

    public void setIdShift(int idShift) {
        this.idShift = idShift;
    }

    @Override
    public abstract IIcon getIcon(int side, int meta);

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (this.hasGUI) {
            player.openGui(modInstance, (this.idShift + world.getBlockMetadata(x, y, z)), world, x, y, z);
        }
        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }

    @Override
    public abstract void getSubBlocks(Item item, CreativeTabs tabs, List list);

    @Override
    public int damageDropped(int meta) {
        return meta;
    }
}
