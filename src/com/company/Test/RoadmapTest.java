package com.company.Test;

import com.company.Algorithms.Algorithm;
import com.company.Models.Roadmap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RoadmapTest {
    @Test
    void roadmapBuilder() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        Roadmap roadmap = new Roadmap.RoadmapBuilder().newAhead()
                .addStation("Toshkent")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(8)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();

        Roadmap roadmap1 = new Roadmap.RoadmapBuilder().newAhead()
                .cloneFrom(roadmap)
                .setCurrentIndex(8)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        Assertions.assertEquals(roadmap1.getStations(), roadmap.getStations());
    }

    @Test
    void moveAheadTest() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        Roadmap roadmap = new Roadmap.RoadmapBuilder().newAhead()
                .addStation("Dustlik")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(10)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        Assertions.assertFalse(roadmap.move());

        roadmap = new Roadmap.RoadmapBuilder().newAhead()
                .addStation("Mashinasozlar")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(9)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        Assertions.assertTrue(roadmap.move());
    }

    @Test
    void moveBackTest() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        Roadmap roadmap = new Roadmap.RoadmapBuilder().newBack()
                .addStation("Beruni")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(0)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        Assertions.assertFalse(roadmap.move());

        roadmap = new Roadmap.RoadmapBuilder().newBack()
                .addStation("Tinchlik")
                .addLine("Uzbekistan Line")
                .setCurrentIndex(1)
                .setCurrentLine("Uzbekistan Line")
                .setCurrentLineStations(algorithm.getStations().get("Uzbekistan Line"))
                .build();
        Assertions.assertTrue(roadmap.move());
    }
}