package com.template;

import com.template.sort.SortAlgorithm;
import com.template.sort.SortFactory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SortAlgorithmsTest {

    private final List<SortAlgorithm<Integer>> algorithms = SortFactory.defaultIntegerAlgorithms();

    @Test
    void allAlgorithmsSortRandomData() {
        Integer[] source = {5, -1, 100, 7, 7, 0, -35, 88, 2, -1};
        Integer[] expected = source.clone();
        Arrays.sort(expected);

        for (SortAlgorithm<Integer> algorithm : algorithms) {
            Integer[] candidate = source.clone();
            algorithm.sort(candidate);
            assertArrayEquals(expected, candidate, algorithm.name());
        }
    }

    @Test
    void allAlgorithmsHandleSortedAndReverseData() {
        Integer[] sorted = {-5, -2, 0, 1, 4, 9, 11};
        Integer[] reverse = {11, 9, 4, 1, 0, -2, -5};

        for (SortAlgorithm<Integer> algorithm : algorithms) {
            Integer[] candidate1 = sorted.clone();
            Integer[] candidate2 = reverse.clone();
            algorithm.sort(candidate1);
            algorithm.sort(candidate2);
            assertArrayEquals(sorted, candidate1, algorithm.name());
            assertArrayEquals(sorted, candidate2, algorithm.name());
        }
    }

    @Test
    void allAlgorithmsRejectNullInput() {
        for (SortAlgorithm<Integer> algorithm : algorithms) {
            assertThrows(IllegalArgumentException.class, () -> algorithm.sort(null), algorithm.name());
        }
    }

    @Test
    void allAlgorithmsRejectNullElements() {
        Integer[] source = {1, null, 2};

        for (SortAlgorithm<Integer> algorithm : algorithms) {
            Integer[] candidate = source.clone();
            assertThrows(IllegalArgumentException.class, () -> algorithm.sort(candidate), algorithm.name());
        }
    }
}
