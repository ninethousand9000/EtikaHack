package me.ninethousand.fate.impl.modules.client;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.Client, enabledByDefault = true)
public final class ClientFont extends Module {
    public static final Setting<FontModes> font = new Setting<>("Font", FontModes.Comfortaa);
    public static final Setting<Boolean> overrideMinecraft = new Setting<>("Override Minecraft", false);
    public static final Setting<Boolean> shadow = new Setting<>("Shadow", true);
    public static final Setting<Boolean> lowercase = new Setting<>("Lowercase", false);

    public ClientFont() {
        addSettings(
                font,
                overrideMinecraft,
                shadow,
                lowercase
        );
    }

    public enum FontModes {
        ProductSans,
        Ubuntu,
        Lato,
        Verdana,
        Comfortaa,
        Subtitle,
        ComicSans
    }
}
