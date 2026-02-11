package com.template;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final int DEFAULT_N = 3000;
    private static final int MIN_VALUE = -100000;
    private static final int MAX_VALUE = 100000;
    private static final Path DEFAULT_OUT = Path.of("data", "numeros.txt");

    private final NumberFileManager fileManager;

    public Main() {
        this(new NumberFileManager());
    }

    public Main(NumberFileManager fileManager) {
        this.fileManager = Objects.requireNonNull(fileManager, "fileManager no puede ser null");
    }

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    void run(String[] args) throws Exception {
        int n = parseCount(args);
        Path out = parseOutputPath(args);

        List<Integer> nums = fileManager.generateRandomIntegers(n, MIN_VALUE, MAX_VALUE);
        fileManager.writeNumbersToFile(out, nums);

        printSummary(nums, out);
    }

    private int parseCount(String[] args) {
        if (args != null && args.length >= 1 && !args[0].isBlank()) {
            int n = Integer.parseInt(args[0]);
            if (n < 1 || n > NumberFileManager.MAX_N) {
                throw new IllegalArgumentException("n debe estar entre 1 y " + NumberFileManager.MAX_N);
            }
            return n;
        }
        return DEFAULT_N;
    }

    private Path parseOutputPath(String[] args) {
        if (args != null && args.length >= 2 && !args[1].isBlank()) {
            return Path.of(args[1]);
        }
        return DEFAULT_OUT;
    }

    private void printSummary(List<Integer> nums, Path out) {
        System.out.println("Listo");
        System.out.println("Cantidad: " + nums.size());
        System.out.println("Archivo: " + out.toAbsolutePath());
    }
}
