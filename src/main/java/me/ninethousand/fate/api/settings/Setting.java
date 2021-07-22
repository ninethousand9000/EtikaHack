package me.ninethousand.fate.api.settings;

import java.util.function.Predicate;

public final class Setting<T> {
    private final String id;
    private T value;
    private final T min, max;
    private final Predicate<T> visibility, callback;

    Setting(
            String id,
            T value,
            T min,
            T max,
            Predicate<T> visibility,
            Predicate<T> callback
    ) {
        this.id = id;
        this.value = value;
        this.min = min;
        this.max = max;
        this.visibility = visibility;
        this.callback = callback;
    }

    public String id() {
        return id;
    }

    public T value() {
        return value;
    }

    public void value(T value) {
        if (callback.test(value)) this.value = value;
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    public boolean visible() {
        return visibility.test(value);
    }
}
