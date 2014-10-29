package info.inpureprojects.core.NEI.gtfoMicroblocks;

import java.util.Random;

/**
 * Created by den on 10/29/2014.
 */
public class JavaObject {

    public int random(int size){
        return new Random().nextInt(size);
    }

    public void out(String msg){
        System.out.println(msg);
    }

}
