package info.inpureprojects.INpureCore.Scripting

import info.inpureprojects.INpureCore.API.IScriptingCore
import info.inpureprojects.INpureCore.API.TocManager
import info.inpureprojects.INpureCore.INpureCore
import net.minecraftforge.fml.common.DummyModContainer
import net.minecraftforge.fml.common.FMLCommonHandler
import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.filefilter.TrueFileFilter
import org.codehaus.groovy.jsr223.GroovyScriptEngineFactory

import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

/**
 * Created by den on 1/6/2015.
 */
class ScriptCore implements IScriptingCore{

    public static ScriptEngineManager manager;

    static{
        manager = new ScriptEngineManager(null)
        manager.registerEngineName("groovy", new GroovyScriptEngineFactory());
    }

    private ScriptEngine engine;

    ScriptCore() {
        engine = manager.getEngineByName("groovy")
    }

    private void loadStream(InputStream stream){
        InputStreamReader reader = new InputStreamReader(stream)
        engine.eval(reader)
        reader.close()
    }

    @Override
    void loadInternalScript(String path) {
        this.loadStream(this.getClass().getClassLoader().getResourceAsStream(path))
    }

    @Override
    void loadTocs(File folder) {
        for (File f : FileUtils.listFiles(folder, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            if (!f.isDirectory()){
                if (FilenameUtils.isExtension(f.getName(), "toc")){
                    TocManager.TableofContents toc = TocManager.instance.read(f);
                    INpureCore.log.info("Loading: %s, %s", toc.title, toc.version)
                    INpureCore.log.info("by: %s", toc.author)
                    FMLCommonHandler.instance().addModToResourcePack(new ModContainerScript(toc, f))
                    for (String s : toc.scripts){
                        INpureCore.log.info("Reading Script: %s", s);
                        FileInputStream read = new FileInputStream(new File(f.getParentFile(), s))
                        this.loadStream(read);
                        read.close()
                    }
                }
            }
        }
    }

    @Override
    void loadInternalToc(String path) {
        TocManager.TableofContents toc = TocManager.instance.read(this.getClass().getClassLoader().getResourceAsStream(path))
        INpureCore.log.info("Loading: %s, %s", toc.title, toc.version)
        INpureCore.log.info("by: %s", toc.author)
        FMLCommonHandler.instance().addModToResourcePack(new DummyModContainer("INpureScriptLoader:${toc.title}"))
        for (String s : toc.scripts){
            INpureCore.log.info("Reading Script: %s", s)
            this.loadStream(this.getClass().getClassLoader().getResourceAsStream(s))
        }
    }
}
