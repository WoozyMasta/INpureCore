package info.inpureprojects.core.Utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;

/**
 * Created by den on 7/19/2014.
 */
public class Downloader {

    public static final Downloader instance = new Downloader();

    public void download(String url, String path) {
        try {
            URL download = new URL(url);
            File f = new File(path);
            if (!f.exists()) {
                System.out.println("Downloading: " + url);
                FileUtils.copyURLToFile(download, f);
            }else{
                System.out.println("Skipping: " + url);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
