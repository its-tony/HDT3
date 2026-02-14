package com.template.sort;

final class SortValidation {

    private SortValidation() {}

    static <T> void requireNoNullElements(T[] data) {
        if (data == null) {
            throw new IllegalArgumentException("data no puede ser null");
        }
        for (T item : data) {
            if (item == null) {
                throw new IllegalArgumentException("data no puede contener null");
            }
        }
    }
}
