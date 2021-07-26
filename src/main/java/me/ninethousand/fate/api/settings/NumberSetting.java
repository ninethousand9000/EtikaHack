package me.ninethousand.fate.api.settings;

import me.ninethousand.fate.api.module.Module;

import java.util.function.Predicate;

public final class NumberSetting<T extends Number> extends Setting<T> {
    private final T min;
    private final T max;

    private final int scale;

    public NumberSetting(String name, T min, T value, T max, int scale) {
        super(name, value);

        this.min = min;
        this.max = max;
        this.scale = scale;
    }

    public NumberSetting(Setting<?> parent, String name, T min, T value, T max, int scale) {
        super(parent, name, value);

        this.min = min;
        this.max = max;
        this.scale = scale;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    public int getScale() {
        return scale;
    }
}

