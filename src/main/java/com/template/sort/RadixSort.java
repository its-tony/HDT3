package com.template.sort;

public final class RadixSort implements SortAlgorithm<Integer> {

    @Override
    public String name() {
        return "RadixSort";
    }

    @Override
    public void sort(Integer[] data) {
        SortValidation.requireNoNullElements(data);
        if (data.length < 2) return;

        int negativeCount = 0;
        int positiveCount = 0;

        for (int value : data) {
            if (value == Integer.MIN_VALUE) {
                throw new IllegalArgumentException("RadixSort no soporta Integer.MIN_VALUE");
            }
            if (value < 0) {
                negativeCount++;
            } else {
                positiveCount++;
            }
        }

        int[] negatives = new int[negativeCount];
        int[] positives = new int[positiveCount];

        int ni = 0;
        int pi = 0;
        for (int value : data) {
            if (value < 0) {
                negatives[ni++] = -value;
            } else {
                positives[pi++] = value;
            }
        }

        radixLsd(negatives);
        radixLsd(positives);

        int index = 0;
        for (int i = negatives.length - 1; i >= 0; i--) {
            data[index++] = -negatives[i];
        }
        for (int value : positives) {
            data[index++] = value;
        }
    }

    private void radixLsd(int[] data) {
        if (data.length < 2) return;

        int max = findMax(data);
        int exp = 1;
        while (max / exp > 0) {
            countingSortByDigit(data, exp);
            if (exp > Integer.MAX_VALUE / 10) {
                break;
            }
            exp *= 10;
        }
    }

    private int findMax(int[] data) {
        int max = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        return max;
    }

    private void countingSortByDigit(int[] data, int exp) {
        int[] count = new int[10];
        int[] output = new int[data.length];

        for (int value : data) {
            int digit = (value / exp) % 10;
            count[digit]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = data.length - 1; i >= 0; i--) {
            int digit = (data[i] / exp) % 10;
            output[count[digit] - 1] = data[i];
            count[digit]--;
        }

        System.arraycopy(output, 0, data, 0, data.length);
    }
}
