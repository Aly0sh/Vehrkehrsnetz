package com.company.Test;

import com.company.DataReaders.LineReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


class LineReaderTest {

    @Test
    void checkDirectoryIsEmptyTest() {
        List<String> lines = LineReader.checkDirectory("src/com/company/Lines");
        Assertions.assertNotNull(lines);
        Assertions.assertFalse(lines.isEmpty());
    }

    @Test
    void checkDirectoryIsEqualsTest() {
        List<String> names = Arrays.asList("Uzbekistan Line", "Chilonzor Line", "Yunusobod Line");
        List<String> lines = LineReader.checkDirectory("src/com/company/Lines");
        Assertions.assertTrue(lines.containsAll(names));
    }
}