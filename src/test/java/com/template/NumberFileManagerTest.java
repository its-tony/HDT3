package com.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberFileManagerTest {

    private final NumberFileManager fileManager = new NumberFileManager();

    @Test
    void generateRandomIntegersRespectsBoundsAndSize() {
        int n = 100;
        int min = -50;
        int max = 50;

        List<Integer> numbers = fileManager.generateRandomIntegers(n, min, max, 123L);

        assertEquals(n, numbers.size());
        assertTrue(numbers.stream().allMatch(value -> value >= min && value <= max));
    }

    @Test
    void writeAndReadRoundTrip(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("numeros.txt");
        List<Integer> numbers = List.of(-3, 0, 14, 14, 9);

        fileManager.writeNumbersToFile(file, numbers);
        Integer[] loaded = fileManager.readNumbersFromFile(file);

        assertArrayEquals(numbers.toArray(new Integer[0]), loaded);
    }

    @Test
    void readMoreThanMaxNumbersThrows(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("overflow.txt");
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < NumberFileManager.MAX_N + 1; i++) {
            content.append(i).append(System.lineSeparator());
        }
        Files.writeString(file, content.toString());

        assertThrows(IllegalArgumentException.class, () -> fileManager.readNumbersFromFile(file));
    }
}
