package me.ninethousand.fate.test;

import me.ninethousand.fate.api.settings.Setting;

import java.awt.*;

public class SettingTest {
    public static Setting<Boolean> mainSetting = new Setting<>("Main", true);
    public static Setting<Boolean> sub1 = new Setting<>(mainSetting, "Sub1", true);
    public static Setting<Boolean> subSub1 = new Setting<>(sub1, "SubSub1", true);
    public static Setting<Boolean> sub2 = new Setting<>(mainSetting, "Sub2", true);
    public static Setting<Boolean> sub3 = new Setting<>(mainSetting, "Sub3", true);


    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Color start = new Color(Color.HSBtoRGB((float) i / 5, 1.0f, 1.0f));
            Color end = new Color(Color.HSBtoRGB((((float) i + 1) / 5), 1.0f, 1.0f));
            System.out.println("Start:" + i + " Color: " + start);
            System.out.println("End:" + i + " Color: " + end);
        }
    }

    public static void drawSetting(Setting<?> setting) {
        System.out.println(setting.getName());

        setting.getSubSettings().forEach(setting1 -> drawSetting(setting1));
    }
}
