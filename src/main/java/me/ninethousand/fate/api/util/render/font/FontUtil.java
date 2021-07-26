package me.ninethousand.fate.api.util.render.font;

import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.impl.modules.client.ClientFont;
import net.minecraft.client.Minecraft;

public final class FontUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static final CFontRenderer productSansFont = new CFontRenderer("ProductSans", 17.0f);
    public static final CFontRenderer ubuntuFont = new CFontRenderer("Ubuntu", 17.0f);
    public static final CFontRenderer latoFont = new CFontRenderer("Lato", 17.0f);
    public static final CFontRenderer verdanaFont = new CFontRenderer("Verdana", 17.0f);
    public static final CFontRenderer comfortaaFont = new CFontRenderer("Comfortaa", 20.0f);
    public static final CFontRenderer subtitleFont = new CFontRenderer("Subtitle", 17.0f);
    public static final CFontRenderer comicSansFont = new CFontRenderer("ComicSans", 17.0f);

    public static CFontRenderer getCurrentCustomFont() {
        switch (ClientFont.font.getValue()) {
            case ProductSans:
                return productSansFont;
            case Ubuntu:
                return ubuntuFont;
            case Lato:
                return latoFont;
            case Verdana:
                return verdanaFont;
            case Comfortaa:
                return comfortaaFont;
            case Subtitle:
                return subtitleFont;
            case ComicSans:
                return comicSansFont;
        }

        return productSansFont;
    }

    public static int drawText(String text, float x, float y, int color) {
        if (ClientFont.shadow.getValue()) {
            if (ModuleManager.getModule(ClientFont.class).isEnabled()) {
                return getCurrentCustomFont().drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            } else {
                return mc.fontRenderer.drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            }
        } else {
            if (ModuleManager.getModule(ClientFont.class).isEnabled()) {
                return getCurrentCustomFont().drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            } else {
                return mc.fontRenderer.drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, (int) x, (int) y, color);
            }
        }
    }

    public static void drawText(String text, double x, double y, int color) {
        drawText(text, (float) x, (float) y, color);
    }

    public static float getStringWidth(String text) {
        return getCurrentCustomFont().getStringWidth(text);
    }

    public static float getStringHeight(String text) {
        return getCurrentCustomFont().getStringHeight(text);
    }
}
