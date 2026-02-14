package com.template.sort;

public final class GnomeSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    @Override
    public String name() {
        return "GnomeSort";
    }

    @Override
    public void sort(T[] data) {
        SortValidation.requireNoNullElements(data);
        if (data.length < 2) return;

        int index = 1;
        while (index < data.length) {
            if (index == 0 || data[index - 1].compareTo(data[index]) <= 0) {
                index++;
            } else {
                swap(data, index, index - 1);
                index--;
            }
        }
    }

    private void swap(T[] data, int i, int j) {
        T tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
