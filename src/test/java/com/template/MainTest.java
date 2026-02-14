package com.template;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    void runGeneratesNumbersFileAndBenchmarkCsv(@TempDir Path tempDir) throws Exception {
        Path numbers = tempDir.resolve("numeros.txt");
        Path csv = tempDir.resolve("benchmark.csv");

        Main main = new Main();
        main.run(new String[] {"30", numbers.toString(), csv.toString(), "10", "1"});

        assertTrue(Files.exists(numbers));
        assertTrue(Files.exists(csv));

        List<String> numbersLines = Files.readAllLines(numbers);
        assertEquals(30, numbersLines.size());

        List<String> csvLines = Files.readAllLines(csv);
        assertTrue(csvLines.size() > 1);
        assertEquals("size,scenario,algorithm,run,time_nanos,time_millis", csvLines.get(0));
    }
}
