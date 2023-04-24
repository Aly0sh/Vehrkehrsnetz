package com.company.Algorithms;

import com.company.DataReaders.LineReader;
import com.company.DataReaders.StationsReader;
import com.company.Models.Roadmap;

import java.util.*;

// Klasse zur Berechnung des kürzesten Pfades mit BFS
public class Algorithm {
    private Map<String, LinkedList<String>> stations; // Wörterbuch mit allen vorhandenen Stationen
    private List<String> visited; // Liste aller besuchten Stationen
    private String startStation;
    private String endStation;
    private Roadmap shortestPath; // Kürzester Weg

    public Algorithm(String startStation, String endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
        stations = getStations();
        visited = new ArrayList<>();
    }

    // Methode zum Ausfüllen des Wörterbuchs mit allen Stationen
    public Map<String, LinkedList<String>> getStations() {
        Map<String, LinkedList<String>> stations = new HashMap<>();
        for (String name : LineReader.checkDirectory("src/com/company/Lines")) {
            stations.put(name, StationsReader.getStation("src/com/company/Lines/" + name + ".txt"));
        }
        return stations;
    }

    // Methode zur Initialisierung des Algorithmus
    public void start() {
        List<Roadmap> paths = new ArrayList<>(); // Liste aller Pfade, die wir durchlaufen
        List<String> lines = findLines(startStation); // Liste aller Linien, auf denen sich der Startstation befindet
        for (int i = 0; i < lines.size(); i++) {
            paths.add(
                    new Roadmap.RoadmapBuilder().newAhead() // Hinzufügen des Pfads zur Liste, der von der Startstation aus vorwärts geht
                            .addStation(startStation)
                            .addLine(lines.get(i))
                            .setCurrentIndex(stations.get(lines.get(i)).indexOf(startStation))
                            .setCurrentLine(lines.get(i))
                            .setCurrentLineStations(stations.get(lines.get(i)))
                            .build()
            );
            paths.add(
                    new Roadmap.RoadmapBuilder().newBack() // Hinzufügen des Pfads zur Liste, der von der Startstation aus rückwärts geht
                            .addStation(startStation)
                            .addLine(lines.get(i))
                            .setCurrentIndex(stations.get(lines.get(i)).indexOf(startStation))
                            .setCurrentLine(lines.get(i))
                            .setCurrentLineStations(stations.get(lines.get(i)))
                            .build()
            );
        }
        visited.add(startStation); // Hinzufügen Startstation zu den besuchten Stationen
        shortestPath = findShortestPath(paths); // Starten des Hauptalgorithmus
    }

    // Methode zum Suchen aller Linien, auf denen sich eine Station befindet
    public List<String> findLines(String station) {
        List<String> lines = new ArrayList<>();

        for (String key : stations.keySet()) {
            if (stations.get(key).contains(station)) {
                lines.add(key);
            }
        }
        return lines;
    }

    // Methode zum Suchen aller Linien außer der angegebenen Linie, auf denen sich eine Station befindet
    public List<String> findLines(String station, String line) {
        List<String> lines = new ArrayList<>();
        for (String key : stations.keySet()) {
            if (!key.equals(line)) {
                if (stations.get(key).contains(station)) {
                    lines.add(key);
                }
            }
        }
        return lines;
    }

    // Hauptmethode für die Breitensuche
    public Roadmap findShortestPath(List<Roadmap> paths) {
        List<Roadmap> removeList = new ArrayList<>(); // Liste der Pfade zum Löschen

        while (!paths.isEmpty()) { // der Algorithmus funktioniert, solange es Pfade gibt, auf denen man sich bewegen kann

            // Ich habe eine separate Variable für die Größe erstellt, da der Algorithmus an Kreuzungen nach Hinzufügen
            // neuer Pfade den Zyklus berücksichtigt hat. Daher hat der Algorithmus nach dem Auffinden einer Kreuzung einen
            // zusätzlichen Schritt ausgeführt
            int size = paths.size();
            for (int i = 0; i < size; i++) {

                // Überprüfung, ob der kürzeste Pfad gefunden wurde
                if (paths.get(i).getStations().getLast().equals(endStation)) {
                    paths.get(i).addLines(paths.get(i).getCurrentLine());
                    return paths.get(i);
                }

                if (!paths.get(i).move() || visited.contains(paths.get(i).getStations().getLast())) {
                    // Wenn die Station bereits besucht wurde oder es keine weiteren Stationen gibt,
                    // wird der gesamte durchlaufenen Pfad zur Liste der zu löschenden Pfade hinzugefügt
                    removeList.add(paths.get(i));
                } else {
                    // Ansonsten wird die Station zu den besuchten Stationen hinzugefügt
                    visited.add(paths.get(i).getStations().getLast());
                }

                // Überprüfung, ob es sich um eine Kreuzung handelt
                if (paths.get(i).getStations().getLast().contains("-")) {
                    for (String j : findLines(paths.get(i).getStations().getLast(), paths.get(i).getCurrentLine())) {
                        // Nachdem eine Kreuzung gefunden wurde, werden zwei Pfade hinzugefügt, die in zwei verschiedene Richtungen führen
                        paths.add(
                                new Roadmap.RoadmapBuilder().newAhead()
                                        .cloneFrom(paths.get(i))
                                        .setCurrentLine(j)
                                        .setCurrentLineStations(stations.get(j))
                                        .setCurrentIndex(stations.get(j).indexOf(paths.get(i).getStations().getLast()))
                                        .build()
                        );
                        paths.add(
                                new Roadmap.RoadmapBuilder().newBack()
                                        .cloneFrom(paths.get(i))
                                        .setCurrentLine(j)
                                        .setCurrentLineStations(stations.get(j))
                                        .setCurrentIndex(stations.get(j).indexOf(paths.get(i).getStations().getLast()))
                                        .build()
                        );
                    }
                }
            }
            paths.removeAll(removeList); // Löschen von Pfaden ohne Fortsetzung
            removeList.clear();
        }
        return null;
    }

    public Roadmap getShortestPath() {
        return shortestPath;
    }

}
