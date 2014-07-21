package info.inpureprojects.core.Scripting.Objects.Exposed;

import info.inpureprojects.core.Utils.Downloader;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

/**
 * Created by den on 7/16/2014.
 */
public class FileIO {

    public void forceDirOnClasspath(String dir) {
        try {
            File f = new File(dir);
            URL u = f.toURL();
            URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Class urlClass = URLClassLoader.class;
            Method method = urlClass.getDeclaredMethod("addURL", new Class[]{URL.class});
            method.setAccessible(true);
            method.invoke(urlClassLoader, new Object[]{u});
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public String getHash(String fileName) {
        File f = new File(fileName);
        try {
            InputStream i = new FileInputStream(f);
            String hash = DigestUtils.sha1Hex(i);
            return hash;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public void refreshDate(String file) {
        File f = new File(file);
        f.setLastModified(new Date().getTime());
    }

    public void downloadFile(String url, String fileName) {
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
