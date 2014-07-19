io.mkdir(scriptFolder.getAbsolutePath() + "/Test/scripts/tests");

io.extractFileFromJar("scripts/tests/Test.toc", scriptFolder.getAbsolutePath() + "/Test/Test.toc");
io.extractFileFromJar("scripts/tests/toc_test.js", scriptFolder.getAbsolutePath() + "/Test/scripts/tests/toc_test.js");