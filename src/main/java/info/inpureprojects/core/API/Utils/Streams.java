package info.inpureprojects.core.API.Utils;

import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

/**
 * Created by den on 7/25/2014.
 */
public class Streams {

    public static final Streams instance = new Streams();

    public Reader getFileReader(File f) {
        try {
            return new FileReader(f);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public Reader getReader(InputStream in) {
        try {
            return new InputStreamReader(in);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public Writer getWriter(File f) {
        try {
            return new FileWriter(f);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public InputStream getStream(File f) {
        try {
            return new FileInputStream(f);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public OutputStream getStreamOut(File f) {
        try {
            return new FileOutputStream(f);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public void IO(InputStream i, OutputStream o) {
        try {
            IOUtils.copy(i, o);
            this.close(i);
            this.close(o);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public InputStream getByteStream(byte[] bytes) {
        try {
            return new ByteArrayInputStream(bytes);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public void close(Closeable c) {
        try {
            c.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public byte[] getBytes(InputStream in) {
        try {
            return IOUtils.toByteArray(in);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public byte[] getBytesFromFile(File f) {
        return getBytes(this.getStream(f));
    }

}
