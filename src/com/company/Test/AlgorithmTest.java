package com.company.Test;

import com.company.Algorithms.Algorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class AlgorithmTest {
    @Test
    void findLinesTest() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        Assertions.assertFalse(algorithm.findLines("Toshkent").isEmpty());
        Assertions.assertTrue(algorithm.findLines("Toshkent").contains("Uzbekistan Line"));
    }

    @Test
    void findShortestPathTest() {
        Algorithm algorithm = new Algorithm("Toshkent", "Pushkin");
        String result = "Toshkent (Uzbekistan Line)\n" +
                "Ming Urik - Oybek (Change to Yunusobod Line)\n" +
                "Amir Temur Xiyoboni - Yunus Rajabiy (Change to Chilonzor Line)\n" +
                "Hamid Olimjon (Chilonzor Line)\n" +
                "Pushkin (Chilonzor Line)\n";
        algorithm.start();
        Assertions.assertEquals(result, algorithm.getShortestPath().show());
    }
}