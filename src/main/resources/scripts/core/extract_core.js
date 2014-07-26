function INpureCore_setup(){
    io.mkdir(scriptFolder.getAbsolutePath() + "/inpurecore_scripts/scripts/core");
    io.mkdir(scriptFolder.getAbsolutePath() + "/inpurecore_scripts/resources/textures/items");
    io.mkdir(scriptFolder.getAbsolutePath() + "/inpurecore_scripts/resources/lang");
    io.extractFileFromJar("scripts/core/INpureCore.toc", scriptFolder.getAbsolutePath() + "/inpurecore_scripts/INpureCore.toc");
    io.extractFileFromJar("scripts/core/inpurecore_main.ts", scriptFolder.getAbsolutePath() + "/inpurecore_scripts/scripts/core/inpurecore_main.ts");
    io.extractFileFromJar("scripts/core/radical_edward.png", scriptFolder.getAbsolutePath() + "/inpurecore_scripts/resources/textures/items/radical_edward.png");
    io.extractFileFromJar("scripts/core/en_US.lang", scriptFolder.getAbsolutePath() + "/inpurecore_scripts/resources/lang/en_US.lang");
}

INpureCore_setup();