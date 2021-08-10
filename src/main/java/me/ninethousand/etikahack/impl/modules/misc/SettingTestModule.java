package me.ninethousand.etikahack.impl.modules.misc;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;

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
