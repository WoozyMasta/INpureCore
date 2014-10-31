package info.inpureprojects.core.NEI.gtfoMicroblocks.ScriptObjects;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by den on 10/29/2014.
 */
public class JavaObject {

    public int random(int size) {
        return new Random().nextInt(size);
    }

    public Block[] ReflectAllBlocks(String clazz) {
        ArrayList<Block> blocks = new ArrayList();
        try {
            Class c = Class.forName(clazz);
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().equals(Block.class) && Modifier.isStatic(f.getModifiers())) {
                    Block b = (Block) f.get(null);
                    if (b != null) {
                        blocks.add(b);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return blocks.toArray(new Block[blocks.size()]);
    }

    public Item[] ReflectAllItems(String clazz) {
        ArrayList<Item> items = new ArrayList();
        try {
            Class c = Class.forName(clazz);
            for (Field f : c.getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getType().equals(Item.class) && Modifier.isStatic(f.getModifiers())) {
                    Item b = (Item) f.get(null);
                    if (b != null) {
                        items.add(b);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return items.toArray(new Item[items.size()]);
    }

}
