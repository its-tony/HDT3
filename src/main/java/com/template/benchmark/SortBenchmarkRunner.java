package com.template.benchmark;

import com.template.sort.SortAlgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class SortBenchmarkRunner {

    private final List<SortAlgorithm<Integer>> algorithms;

    public SortBenchmarkRunner(List<SortAlgorithm<Integer>> algorithms) {
        Objects.requireNonNull(algorithms, "algorithms no puede ser null");
        if (algorithms.isEmpty()) {
            throw new IllegalArgumentException("Debe existir al menos un algoritmo");
        }
        this.algorithms = List.copyOf(algorithms);
    }

    public List<SortExecutionResult> benchmarkBySize(Integer[] sourceData, int step, int runsPerScenario) {
        validateBenchmarkInput(sourceData, step, runsPerScenario);

        List<SortExecutionResult> results = new ArrayList<>();
        for (int size : buildSizes(sourceData.length, step)) {
            Integer[] randomInput = Arrays.copyOf(sourceData, size);
            Integer[] sortedInput = Arrays.copyOf(randomInput, randomInput.length);
            Arrays.sort(sortedInput);

            benchmarkScenario(results, randomInput, SortScenario.RANDOM, runsPerScenario);
            benchmarkScenario(results, sortedInput, SortScenario.SORTED, runsPerScenario);
        }
        return results;
    }

    private void benchmarkScenario(
            List<SortExecutionResult> out,
            Integer[] baseInput,
            SortScenario scenario,
            int runsPerScenario
    ) {
        for (SortAlgorithm<Integer> algorithm : algorithms) {
            for (int run = 1; run <= runsPerScenario; run++) {
                Integer[] candidate = Arrays.copyOf(baseInput, baseInput.length);
                long start = System.nanoTime();
                algorithm.sort(candidate);
                long elapsed = System.nanoTime() - start;
                ensureSorted(candidate, algorithm.name(), scenario);

                out.add(new SortExecutionResult(
                        algorithm.name(),
                        scenario,
                        baseInput.length,
                        run,
                        elapsed
                ));
            }
        }
    }

    private void ensureSorted(Integer[] data, String algorithm, SortScenario scenario) {
        for (int i = 1; i < data.length; i++) {
            if (data[i - 1] > data[i]) {
                throw new IllegalStateException(
                        "El algoritmo " + algorithm + " no ordeno correctamente en escenario " + scenario
                );
            }
        }
    }

    private List<Integer> buildSizes(int maxSize, int step) {
        List<Integer> sizes = new ArrayList<>();
        for (int size = step; size <= maxSize; size += step) {
            sizes.add(size);
        }
        if (sizes.isEmpty() || sizes.get(sizes.size() - 1) != maxSize) {
            sizes.add(maxSize);
        }
        return sizes;
    }

    private void validateBenchmarkInput(Integer[] sourceData, int step, int runsPerScenario) {
        Objects.requireNonNull(sourceData, "sourceData no puede ser null");
        if (sourceData.length == 0) {
            throw new IllegalArgumentException("sourceData no puede estar vacio");
        }
        if (step <= 0) {
            throw new IllegalArgumentException("step debe ser mayor que 0");
        }
        if (step > sourceData.length) {
            throw new IllegalArgumentException("step no puede ser mayor al tamano de sourceData");
        }
        if (runsPerScenario <= 0) {
            throw new IllegalArgumentException("runsPerScenario debe ser mayor que 0");
        }
    }
}
