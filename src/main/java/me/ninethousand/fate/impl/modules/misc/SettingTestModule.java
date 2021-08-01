package me.ninethousand.fate.impl.modules.misc;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class SettingTestModule extends Module {
    public static Setting<Boolean> mainSetting = new Setting<>("Main", true);
    public static Setting<String> string = new Setting<>("String", "Fate");
    public static Setting<Boolean> sub1 = new Setting<>(mainSetting, "Sub1", true);
    public static Setting<EnumTest> subEnum = new Setting<>(mainSetting, "SubEnum", EnumTest.Enum1);
    public static Setting<Boolean> subSub1 = new Setting<>(sub1, "SubSub1", true);
    public static Setting<Boolean> sub2 = new Setting<>(mainSetting, "Sub2", true);
    public static Setting<Boolean> sub3 = new Setting<>(mainSetting, "Sub3", true);

    public SettingTestModule() {
        addSettings(mainSetting, string);
    }

    public enum EnumTest {
        Enum1,
        Enum2,
        Enum3
    }
}
