package com.template.benchmark;

public record SortExecutionResult(
        String algorithm,
        SortScenario scenario,
        int size,
        int runNumber,
        long timeNanos
) {
    public double timeMillis() {
        return timeNanos / 1_000_000.0;
    }
}
