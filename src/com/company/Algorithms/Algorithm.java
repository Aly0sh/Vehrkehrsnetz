package com.company.Algorithms;

import com.company.DataReaders.LineReader;
import com.company.DataReaders.StationsReader;
import com.company.Models.Roadmap;

import java.util.*;

public class Algorithm {
    private Map<String, LinkedList<String>> stations;
    private String startStation;
    private String endStation;
    private Roadmap shortestPath;

    public Algorithm(String startStation, String endStation) {
        this.startStation = startStation;
        this.endStation = endStation;
        stations = getStations();
    }

    public Map<String, LinkedList<String>> getStations() {
        Map<String, LinkedList<String>> stations = new HashMap<>();
        for (String name : LineReader.checkDirectory("src/com/company/Lines")) {
            stations.put(name, StationsReader.getStation("src/com/company/Lines/" + name + ".txt"));
        }
        return stations;
    }

    public void start() {
        List<Roadmap> paths = new ArrayList<>();
        List<String> lines = findLines(startStation);
        for (int i = 0; i < lines.size(); i++) {
            paths.add(
                    new Roadmap.RoadmapBuilder().newAhead()
                            .addStation(startStation)
                            .addLine(lines.get(i))
                            .setCurrentIndex(stations.get(lines.get(i)).indexOf(startStation))
                            .setCurrentLine(lines.get(i))
                            .setCurrentLineStations(stations.get(lines.get(i)))
                            .build()
            );
            paths.add(
                    new Roadmap.RoadmapBuilder().newBack()
                            .addStation(startStation)
                            .addLine(lines.get(i))
                            .setCurrentIndex(stations.get(lines.get(i)).indexOf(startStation))
                            .setCurrentLine(lines.get(i))
                            .setCurrentLineStations(stations.get(lines.get(i)))
                            .build()
            );
        }
        shortestPath = findShortestPath(paths);
    }

    public List<String> findLines(String station) {
        List<String> lines = new ArrayList<>();

        for (String key : stations.keySet()) {
            if (stations.get(key).contains(station)) {
                lines.add(key);
            }
        }
        return lines;
    }

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

    public Roadmap findShortestPath(List<Roadmap> paths) {
        List<Roadmap> removeList = new ArrayList<>();
        while (!paths.isEmpty()) {
            for (int i = 0; i < paths.size(); i++) {
                if (!paths.get(i).move()) {
                    removeList.add(paths.get(i));
                }

                if (paths.get(i).getStations().getLast().contains("-")) {
                    for (String j : findLines(paths.get(i).getStations().getLast(), paths.get(i).getCurrentLine())) {
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
                if (paths.get(i).getStations().getLast().equals(endStation)) {
                    return paths.get(i);
                }
            }
            paths.removeAll(removeList);
            removeList.clear();
        }
        return null;
    }

    public Roadmap getShortestPath() {
        return shortestPath;
    }
}
