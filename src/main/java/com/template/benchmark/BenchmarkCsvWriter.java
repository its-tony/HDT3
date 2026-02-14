package com.template.benchmark;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.List;
import java.util.Objects;

public final class BenchmarkCsvWriter {

    private BenchmarkCsvWriter() {}

    public static void write(Path outputPath, List<SortExecutionResult> results) throws IOException {
        Objects.requireNonNull(outputPath, "outputPath no puede ser null");
        Objects.requireNonNull(results, "results no puede ser null");

        Path parent = outputPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8)) {
            writer.write("size,scenario,algorithm,run,time_nanos,time_millis");
            writer.newLine();
            for (SortExecutionResult result : results) {
                writer.write(result.size()
                        + "," + result.scenario()
                        + "," + result.algorithm()
                        + "," + result.runNumber()
                        + "," + result.timeNanos()
                        + "," + String.format(Locale.ROOT, "%.6f", result.timeMillis()));
                writer.newLine();
            }
        }
    }
}
