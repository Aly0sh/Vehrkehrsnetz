package com.company.Test;

import com.company.DataReaders.StationsReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class StationsReaderTest {

    @Test
    void getStationIsEmptyTest() {
        String name = "Chilonzor Line";
        LinkedList<String> stations = StationsReader.getStation("src/com/company/Lines/" + name + ".txt");
        Assertions.assertFalse(stations.isEmpty());
    }

    @Test
    void getStationIsEqualsTest() {
        String name = "Chilonzor Line";
        String requiredResult = "Buyuk Ipak Yuli\n" +
                "Pushkin\n" +
                "Hamid Olimjon\n" +
                "Amir Temur Xiyoboni - Yunus Rajabiy\n" +
                "Mustaqilliq Maidoni\n" +
                "Paxtakor - Alisher Navoiy\n" +
                "Bunyodkor\n" +
                "Milliy Bog\n" +
                "Novza\n" +
                "Mirzo Ulugbek\n" +
                "Chilonzor\n" +
                "Olmazar\n";
        StringBuilder result = new StringBuilder();
        LinkedList<String> stations = StationsReader.getStation("src/com/company/Lines/" + name + ".txt");
        for (String i : stations) {
            result.append(i);
            result.append("\n");
        }
        Assertions.assertEquals(requiredResult, result.toString());
    }
}