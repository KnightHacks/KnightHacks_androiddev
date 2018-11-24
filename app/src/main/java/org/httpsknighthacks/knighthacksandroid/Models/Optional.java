package org.httpsknighthacks.knighthacksandroid.Models;

public class Optional<T> {

    private T mValue;

    private Optional() {
        this.mValue = null;
    }

    private Optional(T mValue) {
        this.mValue = mValue;
    }

    public static<T> Optional<T> empty() {
        return new Optional<>();
    }

    public static<T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public boolean isPresent() {
        return mValue != null;
    }

    public T getValue() {
        return mValue;
    }
}
