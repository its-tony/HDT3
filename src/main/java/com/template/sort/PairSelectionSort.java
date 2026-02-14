package com.template.sort;

public final class PairSelectionSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    @Override
    public String name() {
        return "PairSelectionSort";
    }

    @Override
    public void sort(T[] data) {
        SortValidation.requireNoNullElements(data);
        if (data.length < 2) return;

        int left = 0;
        int right = data.length - 1;

        while (left < right) {
            int minIndex = left;
            int maxIndex = left;

            for (int i = left + 1; i <= right; i++) {
                if (data[i].compareTo(data[minIndex]) < 0) {
                    minIndex = i;
                }
                if (data[i].compareTo(data[maxIndex]) > 0) {
                    maxIndex = i;
                }
            }

            swap(data, left, minIndex);

            // Si el maximo estaba en la posicion left, se movio a minIndex.
            if (maxIndex == left) {
                maxIndex = minIndex;
            }

            swap(data, right, maxIndex);

            left++;
            right--;
        }
    }

    private void swap(T[] data, int i, int j) {
        T tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
