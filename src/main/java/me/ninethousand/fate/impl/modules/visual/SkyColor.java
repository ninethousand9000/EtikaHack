package me.ninethousand.fate.impl.modules.visual;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class SkyColor extends Module {
    public static final Setting<Color> skyColor = new Setting<>("SkyColor", new Color(0x87CEEB));

    public SkyColor() {
        addSettings(skyColor);
    }
}
