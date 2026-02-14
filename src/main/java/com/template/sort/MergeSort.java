package com.template.sort;

public final class MergeSort<T extends Comparable<? super T>> implements SortAlgorithm<T> {

    @Override
    public String name() {
        return "MergeSort";
    }

    @Override
    public void sort(T[] data) {
        SortValidation.requireNoNullElements(data);
        if (data.length < 2) return;

        T[] aux = data.clone();
        mergeSort(data, aux, 0, data.length - 1);
    }

    private void mergeSort(T[] data, T[] aux, int left, int right) {
        if (left >= right) return;

        int middle = left + (right - left) / 2;
        mergeSort(data, aux, left, middle);
        mergeSort(data, aux, middle + 1, right);
        merge(data, aux, left, middle, right);
    }

    private void merge(T[] data, T[] aux, int left, int middle, int right) {
        System.arraycopy(data, left, aux, left, right - left + 1);

        int i = left;
        int j = middle + 1;
        int k = left;

        while (i <= middle && j <= right) {
            if (aux[i].compareTo(aux[j]) <= 0) {
                data[k++] = aux[i++];
            } else {
                data[k++] = aux[j++];
            }
        }

        while (i <= middle) {
            data[k++] = aux[i++];
        }

        while (j <= right) {
            data[k++] = aux[j++];
        }
    }
}
