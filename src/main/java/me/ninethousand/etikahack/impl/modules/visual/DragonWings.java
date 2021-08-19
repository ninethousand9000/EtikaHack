package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class DragonWings extends Module {
    public static final Setting<Color> wingColor = new Setting<>("WingColor", new Color(0xFF00FF));
    public static final NumberSetting<Integer> scale = new NumberSetting<>("WingScale", 0, 100, 200, 1);

    public DragonWings() {
        addSettings(wingColor, scale);
    }
}
