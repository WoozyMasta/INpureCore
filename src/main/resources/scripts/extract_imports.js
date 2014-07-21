io.extractFileFromJar("scripts/imports.d.ts", cache.getAbsolutePath() + "/imports.d.ts");
io.downloadFile("https://raw.githubusercontent.com/kikito/middleclass/master/middleclass.lua", cache.getAbsolutePath() + "/middleclass.lua");
// refreshDate causes the cache cleaner to ignore this file.
io.refreshDate(cache.getAbsolutePath() + "/middleclass.lua");