function INpureCore_setup(){
    io.mkdir(scriptFolder.getAbsolutePath() + "/INpureCore/scripts/core");
    io.extractFileFromJar("scripts/core/INpureCore.toc", scriptFolder.getAbsolutePath() + "/INpureCore/INpureCore.toc");
    io.extractFileFromJar("scripts/core/inpurecore_main.ts", scriptFolder.getAbsolutePath() + "/INpureCore/scripts/core/inpurecore_main.ts");
}

INpureCore_setup();