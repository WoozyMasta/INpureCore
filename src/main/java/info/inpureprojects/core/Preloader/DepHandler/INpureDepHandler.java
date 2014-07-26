package info.inpureprojects.core.Preloader.DepHandler;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 7/25/2014.
 */
public class INpureDepHandler {

    public ArrayList<String> readStream(InputStream s) {
        try {
            List<String> in = IOUtils.readLines(s);
            String url = "";
            ArrayList<String> deps = new ArrayList();
            for (String read : in) {
                if (read.contains("##url=")) {
                    url = read.split("=")[1].trim().replaceAll("\\s", "");
                } else {
                    deps.add(url + in);
                }
            }
            s.close();
            return deps;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

}
