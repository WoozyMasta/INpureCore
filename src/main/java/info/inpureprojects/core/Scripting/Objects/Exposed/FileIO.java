package info.inpureprojects.core.Scripting.Objects.Exposed;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by den on 7/16/2014.
 */
public class FileIO {

    public void extractFileFromJar(String path, String fileName) {
        try {
            File f = new File(fileName);
            FileOutputStream out = new FileOutputStream(f);
            InputStream i = this.getClass().getClassLoader().getResourceAsStream(path);
            IOUtils.copy(i, out);
            out.close();
            i.close();
            if (f.exists()) {
                System.out.println("File extraction was successful!");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
