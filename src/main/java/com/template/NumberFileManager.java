package com.template;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public final class NumberFileManager {

    public static final int MAX_N = 3000;

    /**
     * Genera N enteros aleatorios en el rango [min, max] (incluyente).
     * @param n cantidad de numeros (1..3000)
     * @param min limite inferior (incluyente)
     * @param max limite superior (incluyente)
     * @param seed si es null, usa una semilla aleatoria; si no, genera reproducible
     */
    public List<Integer> generateRandomIntegers(int n, int min, int max, Long seed) {
        if (n < 1 || n > MAX_N) {
            throw new IllegalArgumentException("n debe estar entre 1 y " + MAX_N);
        }
        if (min > max) {
            throw new IllegalArgumentException("min no puede ser mayor que max");
        }

        Random rnd = (seed == null) ? new Random() : new Random(seed);
        List<Integer> numbers = new ArrayList<>(n);

        // Evitar overflow cuando (max - min + 1) supera int (no usual aqui, pero seguro)
        long range = (long) max - (long) min + 1L;
        if (range <= 0L || range > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Rango invalido o demasiado grande: [" + min + ", " + max + "]");
        }

        for (int i = 0; i < n; i++) {
            int value = min + rnd.nextInt((int) range);
            numbers.add(value);
        }
        return numbers;
    }

    /**
     * Genera N enteros aleatorios usando una semilla aleatoria.
     */
    public List<Integer> generateRandomIntegers(int n, int min, int max) {
        return generateRandomIntegers(n, min, max, null);
    }

    /**
     * Escribe los numeros en un archivo, 1 por linea.
     */
    public void writeNumbersToFile(Path filePath, List<Integer> numbers) throws IOException {
        Objects.requireNonNull(filePath, "filePath no puede ser null");
        Objects.requireNonNull(numbers, "numbers no puede ser null");
        if (numbers.size() > MAX_N) {
            throw new IllegalArgumentException("No se pueden escribir mas de " + MAX_N + " numeros");
        }

        // Crea carpeta si no existe
        Path parent = filePath.getParent();
        if (parent != null) Files.createDirectories(parent);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            for (Integer num : numbers) {
                if (num == null) {
                    throw new IllegalArgumentException("La lista contiene null");
                }
                writer.write(num.toString());
                writer.newLine();
            }
        }
    }

    /**
     * Lee numeros desde un archivo (1 por linea). Ignora lineas vacias.
     */
    public Integer[] readNumbersFromFile(Path filePath) throws IOException {
        Objects.requireNonNull(filePath, "filePath no puede ser null");
        List<Integer> nums = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;

                nums.add(Integer.parseInt(trimmed));

                if (nums.size() > MAX_N) {
                    throw new IllegalArgumentException("El archivo contiene mas de " + MAX_N + " numeros");
                }
            }
        }

        return nums.toArray(new Integer[0]);
    }
}
