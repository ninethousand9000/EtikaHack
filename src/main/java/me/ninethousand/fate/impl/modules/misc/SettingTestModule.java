package me.ninethousand.fate.impl.modules.misc;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class SettingTestModule extends Module {
    public static Setting<Color> color = new Setting<>("Color", Color.red);
    public static Setting<String> string1 = new Setting<>(color, "Stringe", "nottrue");
    public static Setting<String> string2 = new Setting<>(string1, "Stringeeee", "nottrue");

    public SettingTestModule() {
        addSettings(color);
    }

    public enum EnumTest {
        Enum1,
        Enum2,
        Enum3
    }
}
