package com.template.sort;

public interface SortAlgorithm<T extends Comparable<? super T>> {

    String name();

    void sort(T[] data);
}
