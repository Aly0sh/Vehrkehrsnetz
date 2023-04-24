package com.company.Models;

// Kinderklasse "Roadmap", die entlang der aktuellen Linienstationen vorwärtsgeht
public class RoadmapAhead extends Roadmap{

    // Der Methode für das Vorwärtsbewegen
    @Override
    public boolean move() {
        if (currentIndex + 1 == currentLineStations.size()) {
            return false; // Wenn die Stationen zu Ende sind, gibt die Methode false zurück
        }
        // Ansonsten fährt es weiter
        currentIndex += 1;
        addLines(currentLine);
        addStation(currentLineStations.get(currentIndex));
        return true;
    }
}
