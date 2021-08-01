package me.ninethousand.fate.api.settings;

import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.impl.modules.visual.Chams;

import java.awt.*;
import java.util.ArrayList;

public class Setting<T> {
    private final String name;

    private T value;

    private boolean isOpened;

    private float hue = 1f;
    private float saturation = 1f;
    private float brightness = 1f;
    private float alpha = 1f;
    private boolean rainbow = false;

    private boolean typing = false;

    private final ArrayList<Setting<?>> subSettings = new ArrayList<>();

    public Setting(String name, T value) {
        this.name = name;
        this.value = value;

        if (value instanceof Color) {
            this.alpha = 1 - ((Color) value).getAlpha() / 255f;
            this.hue = Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[0];
            this.saturation = 1 - Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[1];
            this.brightness = 1 - Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[2];
        }
    }

    public Setting(Setting<?> parent, String name, T value) {
        this.name = name;
        this.value = value;

        if (value instanceof Color) {
            this.alpha = 1 - ((Color) value).getAlpha() / 255f;
            this.hue = Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[0];
            this.saturation = 1 - Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[1];
            this.brightness = 1 - Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[2];
        }

        if (parent.getValue() instanceof Boolean) {
            Setting<Boolean> booleanSetting = (Setting<Boolean>) parent;
            booleanSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Enum) {
            Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) parent;
            enumSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Color) {
            Setting<Color> colorSetting = (Setting<Color>) parent;
            colorSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Integer) {
            NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) parent;
            integerNumberSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Double) {
            NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) parent;
            doubleNumberSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Float) {
            NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) parent;
            floatNumberSetting.addSubSetting(this);
        }
    }

    public void updateSetting() {
        if (value instanceof Color) {
            if (rainbow) {
                if (hue + 0.0001 <= 1.0f) {
                    hue += 0.0001;
                }

                else {
                    hue = 0;
                }
            }

            Color c = new Color(Color.HSBtoRGB(hue, 1 - saturation, 1 - brightness));

            value = (T) (new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) ((1 - alpha) * 255)));

            if (this == Chams.playerColor) {
                Fate.log(1 + " ");
            }
        }

        subSettings.forEach(sub -> sub.updateSetting());
    }

    public ArrayList<Setting<?>> getSubSettings() {
        return this.subSettings;
    }

    public boolean hasSubSettings() {
        return this.subSettings.size() > 0;
    }

    public void addSubSetting(Setting<?> subSetting) {
        this.subSettings.add(subSetting);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }

    public float getHue() {
        return hue;
    }

    public void setHue(float hue) {
        this.hue = hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public float getBrightness() {
        return brightness;
    }

    public void setBrightness(float brightness) {
        this.brightness = brightness;
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }

    public Setting<T> register(Module module) {
        module.settings.add(this);
        return this;
    }
}
