package me.ninethousand.etikahack.api.settings;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.Minecraft;

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

        parent.addSubSetting(this);
    }

    public void updateSetting() {
        if (Minecraft.getMinecraft().player == null ||Minecraft.getMinecraft().world == null) return;

        float speed = 0.001f;

        try {
            speed = 101 - Customise.rainbowSpeed.getValue();
        }

        catch (Exception e) {}

        if (value instanceof Color) {
            if (rainbow) {
                hue = (float) (System.currentTimeMillis() % (long) (360 * speed)) / (360.0f * speed);
            }

            Color c = new Color(Color.HSBtoRGB(hue, 1 - saturation, 1 - brightness));

            value = (T) (new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) ((1 - alpha) * 255)));
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

        if (value instanceof Color) {
            this.alpha = 1 - ((Color) value).getAlpha() / 255f;
            this.hue = Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[0];
            this.saturation = 1 - Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[1];
            this.brightness = 1 - Color.RGBtoHSB(((Color) value).getRed(), ((Color) value).getGreen(), ((Color) value).getBlue(), null)[2];
        }
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
