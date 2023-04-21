package com.company.Models;

public class RoadmapAhead extends Roadmap{

    @Override
    public boolean move() {
        if (currentIndex + 1 == currentLineStations.size()) {
            return false;
        }
        currentIndex += 1;
        addLines(currentLine);
        addStation(currentLineStations.get(currentIndex));
        return true;
    }
}
