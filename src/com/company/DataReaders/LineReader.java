package com.company.DataReaders;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Klasse zur Überprüfung von Verzeichnissen
public class LineReader {

    // Method zur Erlangung einer Liste aller Zeilen
    public static List<String> checkDirectory(String path) {
        List<String> fileNames = new ArrayList<>();
        try {
            File dir = new File(path);
            for (File i : dir.listFiles()) {
                fileNames.add(i.getName().replace(".txt", ""));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return fileNames;
    }
}
