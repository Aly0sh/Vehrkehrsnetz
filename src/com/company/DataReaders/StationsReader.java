package com.company.DataReaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

// Klasse zum Lesen von .txt-Dateien
public class StationsReader {

    // Method zur Erlangung aller Stationen aus dem eingegebenen Dateipfad
    public static LinkedList<String> getStation(String inputFileName) {
        LinkedList<String> stations = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                stations.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stations;
    }
}
