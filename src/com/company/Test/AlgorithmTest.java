package com.company.Test;

import com.company.Algorithms.Algorithm;
import com.company.Models.Roadmap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {
    @Test
    void findLinesTest() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        Assertions.assertFalse(algorithm.findLines("Toshkent").isEmpty());
        Assertions.assertTrue(algorithm.findLines("Toshkent").contains("Uzbekistan Line"));
    }

    @Test
    void roadmapBuilder() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        Roadmap roadmap = new Roadmap.RoadmapBuilder().newBack()
                .addStation("Toshkent")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(8)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        roadmap.move();
        roadmap.show();
        roadmap = new Roadmap.RoadmapBuilder().newAhead()
                .addStation("Toshkent")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(8)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        roadmap.show();

        Roadmap roadmap1 = new Roadmap.RoadmapBuilder().newAhead()
                .cloneFrom(roadmap)
                .setCurrentIndex(8)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        Assertions.assertEquals(roadmap1.getStations(), roadmap.getStations());
    }
}