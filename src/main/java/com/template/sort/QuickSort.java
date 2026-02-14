package com.template.sort;

public final class QuickSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    @Override
    public String name() {
        return "QuickSort";
    }

    @Override
    public void sort(T[] data) {
        SortValidation.requireNoNullElements(data);
        if (data.length < 2) return;

        quickSort(data, 0, data.length - 1);
    }

    private void quickSort(T[] data, int left, int right) {
        if (left >= right) return;

        int pivotIndex = partition(data, left, right);
        quickSort(data, left, pivotIndex - 1);
        quickSort(data, pivotIndex + 1, right);
    }

    private int partition(T[] data, int left, int right) {
        T pivot = data[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            if (data[j].compareTo(pivot) <= 0) {
                i++;
                swap(data, i, j);
            }
        }

        swap(data, i + 1, right);
        return i + 1;
    }

    private void swap(T[] data, int i, int j) {
        T tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
