package com.template.sort;

import java.util.List;

public final class SortFactory {

    private SortFactory() {}

    public static List<SortAlgorithm<Integer>> defaultIntegerAlgorithms() {
        return List.of(
                new GnomeSort<>(),
                new MergeSort<>(),
                new QuickSort<>(),
                new RadixSort(),
                new PairSelectionSort<>()
        );
    }
}
