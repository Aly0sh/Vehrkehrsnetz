package com.company.Models;

import java.util.LinkedList;

// Abstract-Klasse für das Wegmodell
public abstract class Roadmap {
    protected LinkedList<String> stations; // Liste aller besuchten Stationen
    protected LinkedList<String> lines; // Liste aller besuchten Linien
    protected LinkedList<String> currentLineStations; // Liste aller Stationen der aktuellen Linie
    protected int currentIndex; // Aktueller Index der Station in der Liste der Stationen der aktuellen Linie
    protected String currentLine; // Aktuelle Linie

    public Roadmap() {
        stations = new LinkedList<>();
        lines = new LinkedList<>();
    }

    public LinkedList<String> getStations() {
        return stations;
    }

    public void addStation(String station) {
        this.stations.addLast(station);
    }

    public void addLines(String linie) {
        this.lines.addLast(linie);
    }

    public String getCurrentLine() {
        return currentLine;
    }

    // Methode zum Erhalten eines Strings zur Ausgabe in der Konsole
    public String show() {
        StringBuilder res = new StringBuilder();
        String prevLine = lines.getFirst();
        for (int i = 0; i < stations.size(); i++) {
            res.append(stations.get(i)).append(" (");
            if (!prevLine.equals(lines.get(i + 1))) {
                res.append("Change to ");
            }
            res.append(lines.get(i + 1)).append(")\n");
            prevLine = lines.get(i + 1);
        }
        return res.toString();
    }

    // Methode zur Erstellung einer Liste von Zeichenfolgen für die GUI-Ausgabe
    public LinkedList<String> getResultAsList() {
        LinkedList<String> resList = new LinkedList<>();
        StringBuilder res;
        String prevLine = lines.getFirst();
        for (int i = 0; i < stations.size(); i++) {
            res = new StringBuilder();
            res.append(stations.get(i)).append(" (");
            if (!prevLine.equals(lines.get(i + 1))) {
                res.append("Change to ");
            }
            res.append(lines.get(i + 1)).append(")");
            prevLine = lines.get(i + 1);
            resList.addLast(res.toString());
        }
        return resList;
    }

    public abstract boolean move();

    // Ein Builder für die Klasse Roadmap
    public static class RoadmapBuilder {
        private Roadmap roadmap;


        public RoadmapBuilder newAhead() {
            roadmap = new RoadmapAhead();
            return this;
        }

        public RoadmapBuilder newBack() {
            roadmap = new RoadmapBack();
            return this;
        }

        public RoadmapBuilder cloneFrom(Roadmap path) {
            roadmap.stations = new LinkedList<>(path.stations);
            roadmap.lines = new LinkedList<>(path.lines);
            return this;
        }

        public RoadmapBuilder setCurrentLineStations(LinkedList<String> currentLineStations) {
            roadmap.currentLineStations = currentLineStations;
            return this;
        }

        public RoadmapBuilder setCurrentIndex(int index) {
            roadmap.currentIndex = index;
            return this;
        }

        public RoadmapBuilder setCurrentLine(String line) {
            roadmap.currentLine = line;
            return this;
        }

        public RoadmapBuilder addStation(String station) {
            roadmap.stations.addLast(station);
            return this;
        }

        public RoadmapBuilder addLine(String line) {
            roadmap.lines.addLast(line);
            return this;
        }

        public Roadmap build() {
            return roadmap;
        }
    }
}
