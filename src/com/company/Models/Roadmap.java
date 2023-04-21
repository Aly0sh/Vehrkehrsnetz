package com.company.Models;

import java.util.Collections;
import java.util.LinkedList;

public abstract class Roadmap {
    protected LinkedList<String> stations;
    protected LinkedList<String> lines;
    protected LinkedList<String> currentLineStations;
    protected int currentIndex;
    protected String currentLine;

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

    public void show() {
        stations.stream().forEach((i) -> System.out.print(i + " "));
    }

    public abstract boolean move();

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
