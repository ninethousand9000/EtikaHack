package me.ninethousand.fate.api.settings;

import me.ninethousand.fate.api.module.Module;

import java.util.function.Predicate;

public final class SettingBuilder<T> {
    private String id;
    private final T value;
    private T min, max;
    private Predicate<T> visibility = v -> true, callback = v -> true;

    public SettingBuilder(T value) {
        this.value = value;
    }

    public SettingBuilder<T> id(String id) {
        this.id = id;

        return this;
    }

    public SettingBuilder<T> min(T min) {
        this.min = min;

        return this;
    }

    public SettingBuilder<T> max(T max) {
        this.max = max;

        return this;
    }

    public SettingBuilder<T> childOf(Setting<Boolean> parent) {
        this.visibility = v -> parent.value();

        return this;
    }

    public SettingBuilder<T> visibility(Predicate<T> visibility) {
        this.visibility = visibility;

        return this;
    }

    public SettingBuilder<T> callback(Predicate<T> callback) {
        this.callback = callback;

        return this;
    }

    public Setting<T> construct() {
        return new Setting<>(
                id,
                value,
                min,
                max,
                visibility,
                callback
        );
    }

    public Setting<T> construct(Module module) {
        Setting<T> property = construct();
        module.settings.add(property);
        return property;
    }
}

