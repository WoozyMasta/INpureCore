io.mkdir(scriptFolder.getAbsolutePath() + "/Test/scripts/tests");

io.extractFileFromJar("scripts/tests/toc_test/Test.toc", scriptFolder.getAbsolutePath() + "/Test/Test.toc");
io.extractFileFromJar("scripts/tests/toc_test/toc_test.js", scriptFolder.getAbsolutePath() + "/Test/scripts/tests/toc_test.js");
io.extractFileFromJar("scripts/tests/toc_test/toc_test.ts", scriptFolder.getAbsolutePath() + "/Test/scripts/tests/toc_test.ts");
io.extractFileFromJar("scripts/tests/toc_test/toc_test.lua", scriptFolder.getAbsolutePath() + "/Test/scripts/tests/toc_test.lua");