package com.template.benchmark;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class BenchmarkSummaryPrinter {

    private BenchmarkSummaryPrinter() {}

    public static void printAverageByAlgorithmAndScenario(List<SortExecutionResult> results) {
        Map<String, DoubleSummaryStatistics> summary = results.stream()
                .collect(Collectors.groupingBy(
                        r -> r.algorithm() + "|" + r.scenario(),
                        Collectors.summarizingDouble(SortExecutionResult::timeMillis)
                ));

        System.out.println("Resumen promedio (ms) por algoritmo y escenario:");
        summary.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    String key = entry.getKey();
                    double avg = entry.getValue().getAverage();
                    long count = entry.getValue().getCount();
                    System.out.printf("  %-28s avg=%10.4f ms  muestras=%d%n", key, avg, count);
                });
    }
}
