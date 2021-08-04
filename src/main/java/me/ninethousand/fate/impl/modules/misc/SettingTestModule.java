package me.ninethousand.fate.impl.modules.misc;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class SettingTestModule extends Module {
    public static Setting<String> string = new Setting<>("String", "Sexy");
    public static Setting<String> string1 = new Setting<>(string, "Stringe", "nottrue");
    public static Setting<String> string2 = new Setting<>(string1, "Stringeeee", "nottrue");

    public SettingTestModule() {
        addSettings(string);
    }

    public enum EnumTest {
        Enum1,
        Enum2,
        Enum3
    }
}
