package me.ninethousand.etikahack.impl.modules.client;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.render.font.CFontRenderer;

@ModuleAnnotation(category = ModuleCategory.CLIENT, enabledByDefault = true)
public final class ClientFont extends Module {
    public static final Setting<FontModes> font = new Setting<>("Font", FontModes.Arial);
    public static final NumberSetting<Integer> size = new NumberSetting<>("Size", 10, 17, 40, 1);
    public static final Setting<Boolean> overrideMinecraft = new Setting<>("Override Minecraft", false);
    public static final Setting<Boolean> shadow = new Setting<>("Shadow", true);
    public static final Setting<Boolean> lowercase = new Setting<>("Lowercase", false);

    public static CFontRenderer customFont = null;

    public ClientFont() {
        addSettings(
                font,
                size,
                overrideMinecraft,
                shadow,
                lowercase
        );
    }

    @Override
    public void onUpdate() {

    }

    public enum FontModes {
        ProductSans,
        Ubuntu,
        Lato,
        Verdana,
        Comfortaa,
        Subtitle,
        ComicSans,
        SergoeUI,
        Roboto,
        Arial
    }
}
