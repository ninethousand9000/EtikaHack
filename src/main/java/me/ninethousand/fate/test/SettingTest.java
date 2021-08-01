package me.ninethousand.fate.test;

import me.ninethousand.fate.api.settings.Setting;

public class SettingTest {
    public static Setting<Boolean> mainSetting = new Setting<>("Main", true);
    public static Setting<Boolean> sub1 = new Setting<>(mainSetting, "Sub1", true);
    public static Setting<Boolean> subSub1 = new Setting<>(sub1, "SubSub1", true);
    public static Setting<Boolean> sub2 = new Setting<>(mainSetting, "Sub2", true);
    public static Setting<Boolean> sub3 = new Setting<>(mainSetting, "Sub3", true);


    public static void main(String[] args) {
        drawSetting(mainSetting);
    }

    public static void drawSetting(Setting<?> setting) {
        System.out.println(setting.getName());

        setting.getSubSettings().forEach(setting1 -> drawSetting(setting1));
    }
}
