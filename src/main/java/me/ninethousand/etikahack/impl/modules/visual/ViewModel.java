package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class ViewModel extends Module {
    public static final Setting<Boolean> translate = new Setting<>("Translate", true);
    public static final NumberSetting<Float> translateX = new NumberSetting<>(translate, "TranslateX", 0f, 1f, 2f, 2);
    public static final NumberSetting<Float> translateY = new NumberSetting<>(translate, "TranslateY", 0f, 1f, 2f, 2);
    public static final NumberSetting<Float> translateZ = new NumberSetting<>(translate, "TranslateZ", 0f, 1f, 2f, 2);

    public static final Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static final NumberSetting<Integer> rotateX = new NumberSetting<>(rotate, "RotateX", 0, 0, 360, 1);
    public static final NumberSetting<Integer> rotateY = new NumberSetting<>(rotate, "RotateY", 0, 0, 360, 1);
    public static final NumberSetting<Integer> rotateZ = new NumberSetting<>(rotate, "RotateZ", 0, 0, 360, 1);

    public static final Setting<Boolean> scale = new Setting<>("Scale", true);
    public static final NumberSetting<Float> scaleX = new NumberSetting<>(scale, "ScaleX", 0f, 1f, 2f, 2);
    public static final NumberSetting<Float> scaleY = new NumberSetting<>(scale, "ScaleY", 0f, 1f, 2f, 2);
    public static final NumberSetting<Float> scaleZ = new NumberSetting<>(scale, "ScaleZ", 0f, 1f, 2f, 2);

    public ViewModel() {
        addSettings(translate, rotate, scale);
    }
}
