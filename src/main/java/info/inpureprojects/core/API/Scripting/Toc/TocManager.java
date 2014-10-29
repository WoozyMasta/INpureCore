package info.inpureprojects.core.API.Scripting.Toc;

import org.apache.commons.io.IOUtils;
import scala.actors.threadpool.Arrays;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by den on 7/18/2014.
 */
public class TocManager {

    public static final TocManager instance = new TocManager();

    public TableofContents read(File file) {
        try {
            FileInputStream f = new FileInputStream(file);
            return this.read(f);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public TableofContents read(InputStream stream) {
        try {
            TableofContents c = this.parse(IOUtils.readLines(stream));
            stream.close();
            return c;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    private String clean(String s) {
        return s.split(":")[1].trim().replaceAll("\\s", "").toLowerCase();
    }

    private String cleanNoCaseChange(String s) {
        return s.split(":")[1].trim().replaceAll("\\s", "");
    }

    private TableofContents parse(List<String> lines) {
        TableofContents c = new TableofContents();
        ArrayList<String> f = new ArrayList();
        for (String s : lines) {
            if (s.contains("## Title:")) {
                c.setTitle(clean(s));
            } else if (s.contains("## Author:")) {
                c.setAuthor(clean(s));
            } else if (s.contains("## Version:")) {
                c.setVersion(clean(s));
            } else if (s.contains("## Saved Variables:")) {
                String temp = s.replace("## Saved Variables:", "").replaceAll("\\s", "");
                c.setSavedVariables(Arrays.asList(temp.split(",")));
            } else if (s.contains("## Bootstrap:")) {
                c.setBootstrap(cleanNoCaseChange(s));
            } else if (!s.contains("##")) {
                f.add(s.trim());
            }
        }
        c.setScripts(f);
        return c;
    }

    public static class TableofContents {

        private String title;
        private String author;
        private String version;
        private List<String> savedVariables;
        private String bootstrap;
        private List<String> scripts;

        public List<String> getScripts() {
            return scripts;
        }

        public void setScripts(List<String> scripts) {
            this.scripts = scripts;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public List<String> getSavedVariables() {
            return savedVariables;
        }

        public void setSavedVariables(List<String> savedVariables) {
            this.savedVariables = savedVariables;
        }

        public String getBootstrap() {
            return bootstrap;
        }

        public void setBootstrap(String bootstrap) {
            this.bootstrap = bootstrap;
        }
    }

}
