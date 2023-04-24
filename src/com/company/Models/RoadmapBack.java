package com.company.Models;

// Kinderklasse "Roadmap", die entlang der aktuellen Linienstationen rückwärtsgeht
public class RoadmapBack extends Roadmap{

    // Der Methode für das Rückwärtsbewegung
    @Override
    public boolean move() {
        if (currentIndex == 0) {
            return false; // Wenn die Stationen zu Ende sind, gibt die Methode false zurück
        }
        // Ansonsten fährt es weiter
        currentIndex -= 1;
        addLines(currentLine);
        addStation(currentLineStations.get(currentIndex));
        return true;
    }
}
