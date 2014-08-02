package info.inpureprojects.core.NEI.gtfoMicroblocks.Modules;

import codechicken.nei.api.API;
import info.inpureprojects.core.INpureCore;
import info.inpureprojects.core.NEI.gtfoMicroblocks.NEIINpureConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by den on 8/2/2014.
 */
public class BiblioCraft extends GtfoFMPModule{

    public BiblioCraft(String id) {
        super(id);
    }
    private Random r = new Random();

    @Override
    public void run() {
        INpureCore.proxy.print("Taking an axe to some BiblioCraft blocks...");
        try{
            for (Field f : Class.forName("jds.bibliocraft.blocks.BlockLoader").getDeclaredFields()){
                if (f.getType().equals(Block.class)){
                    Block b = (Block) f.get(null);
                    ItemStack stack = new ItemStack(b, 1, 0);
                    API.setItemListEntries(stack.getItem(), NEIINpureConfig.buildStackList(stack, new int[]{r.nextInt(5)}));
                }
            }
            for (Field f : Class.forName("jds.bibliocraft.items.ItemLoader").getDeclaredFields()){
                if (f.getType().equals(Item.class)){
                    Item i = (Item) f.get(null);
                    ItemStack stack = new ItemStack(i, 1, 0);
                    API.setItemListEntries(stack.getItem(), NEIINpureConfig.buildStackList(stack, new int[]{r.nextInt(5)}));
                }
            }
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
}
