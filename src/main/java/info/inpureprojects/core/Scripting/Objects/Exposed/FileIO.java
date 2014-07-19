package info.inpureprojects.core.Scripting.Objects.Exposed;

import info.inpureprojects.core.Utils.Downloader;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by den on 7/16/2014.
 */
public class FileIO {

    public String getHash(String fileName){
        File f = new File(fileName);
        try{
            InputStream i = new FileInputStream(f);
            String hash = DigestUtils.sha1Hex(i);
            return hash;
        }catch(Throwable t){
            t.printStackTrace();
        }
        return null;
    }

    public void downloadFile(String url, String fileName){
        Downloader.instance.download(url, fileName);
    }

    public void extractFileFromJar(String path, String fileName) {
        try {
            File f = new File(fileName);
            FileOutputStream out = new FileOutputStream(f);
            InputStream i = this.getClass().getClassLoader().getResourceAsStream(path);
            IOUtils.copy(i, out);
            out.close();
            i.close();
            if (f.exists()) {
                System.out.println("File extraction: " + path + " was successful!");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void mkdir(String path) {
        File f = new File(path);
        f.mkdirs();
    }

}
