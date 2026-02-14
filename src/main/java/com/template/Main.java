package com.template;

import com.template.benchmark.BenchmarkCsvWriter;
import com.template.benchmark.BenchmarkSummaryPrinter;
import com.template.benchmark.SortBenchmarkRunner;
import com.template.benchmark.SortExecutionResult;
import com.template.sort.SortFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final int DEFAULT_N = 3000;
    private static final int DEFAULT_STEP = 10;
    private static final int DEFAULT_RUNS = 2;
    private static final int MIN_VALUE = -100000;
    private static final int MAX_VALUE = 100000;
    private static final Path DEFAULT_NUMBERS_FILE = Path.of("data", "numeros.txt");
    private static final Path DEFAULT_CSV_FILE = Path.of("data", "benchmark.csv");

    private final NumberFileManager fileManager;
    private final SortBenchmarkRunner benchmarkRunner;

    public Main() {
        this(new NumberFileManager(), new SortBenchmarkRunner(SortFactory.defaultIntegerAlgorithms()));
    }

    public Main(NumberFileManager fileManager, SortBenchmarkRunner benchmarkRunner) {
        this.fileManager = Objects.requireNonNull(fileManager, "fileManager no puede ser null");
        this.benchmarkRunner = Objects.requireNonNull(benchmarkRunner, "benchmarkRunner no puede ser null");
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    void run(String[] args) throws Exception {
        RunConfig config = RunConfig.fromArgs(args);

        List<Integer> numbers = fileManager.generateRandomIntegers(config.n(), MIN_VALUE, MAX_VALUE);
        fileManager.writeNumbersToFile(config.numbersPath(), numbers);

        Integer[] data = fileManager.readNumbersFromFile(config.numbersPath());
        List<SortExecutionResult> results = benchmarkRunner.benchmarkBySize(
                data,
                config.step(),
                config.runsPerScenario()
        );

        BenchmarkCsvWriter.write(config.csvPath(), results);
        printSummary(config, data.length, results.size());
        BenchmarkSummaryPrinter.printAverageByAlgorithmAndScenario(results);
    }

    private void printSummary(RunConfig config, int totalNumbers, int totalExecutions) {
        System.out.println("Ejecucion completada");
        System.out.println("Cantidad de numeros generados: " + totalNumbers);
        System.out.println("Intervalo (step): " + config.step());
        System.out.println("Corridas por escenario: " + config.runsPerScenario());
        System.out.println("Archivo de numeros: " + config.numbersPath().toAbsolutePath());
        System.out.println("CSV de resultados: " + config.csvPath().toAbsolutePath());
        System.out.println("Total de ejecuciones de sort: " + totalExecutions);
    }

    record RunConfig(int n, Path numbersPath, Path csvPath, int step, int runsPerScenario) {
        static RunConfig fromArgs(String[] args) {
            int n = parseN(args, 0, DEFAULT_N);
            Path numbersPath = parsePath(args, 1, DEFAULT_NUMBERS_FILE);
            Path csvPath = parsePath(args, 2, DEFAULT_CSV_FILE);
            int step = parsePositiveInt(args, 3, DEFAULT_STEP, "step");
            int runs = parsePositiveInt(args, 4, DEFAULT_RUNS, "runs");

            if (n < 1 || n > NumberFileManager.MAX_N) {
                throw new IllegalArgumentException("n debe estar entre 1 y " + NumberFileManager.MAX_N);
            }
            if (step > n) {
                throw new IllegalArgumentException("step no puede ser mayor que n");
            }
            return new RunConfig(n, numbersPath, csvPath, step, runs);
        }

        private static int parseN(String[] args, int index, int defaultValue) {
            if (args == null || args.length <= index || args[index].isBlank()) {
                return defaultValue;
            }
            return Integer.parseInt(args[index]);
        }

        private static int parsePositiveInt(String[] args, int index, int defaultValue, String fieldName) {
            if (args == null || args.length <= index || args[index].isBlank()) {
                return defaultValue;
            }
            int value = Integer.parseInt(args[index]);
            if (value <= 0) {
                throw new IllegalArgumentException(fieldName + " debe ser mayor que 0");
            }
            return value;
        }

        private static Path parsePath(String[] args, int index, Path defaultValue) {
            if (args == null || args.length <= index || args[index].isBlank()) {
                return defaultValue;
            }
            return Path.of(args[index]);
        }
    }
}
