package me.ninethousand.fate.api.settings;

import org.lwjgl.input.Keyboard;

public final class Bind {
    private int key;

    public Bind(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getKeyName() {
        return Keyboard.getKeyName(key);
    }

    @Override
    public String toString() {
        return getKeyName();
    }
}
