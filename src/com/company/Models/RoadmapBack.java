package com.company.Models;

public class RoadmapBack extends Roadmap{

    @Override
    public boolean move() {
        if (currentIndex == 0) {
            return false;
        }
        currentIndex -= 1;
        addLines(currentLine);
        addStation(currentLineStations.get(currentIndex));
        return true;
    }
}
