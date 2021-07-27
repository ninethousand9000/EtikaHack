package me.ninethousand.fate.api.util.render.font;

import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.impl.modules.client.ClientFont;
import net.minecraft.client.Minecraft;

public final class FontUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static CFontRenderer productSansFont = new CFontRenderer("ProductSans", 17.0f);
    public static CFontRenderer ubuntuFont = new CFontRenderer("Ubuntu", 17.0f);
    public static CFontRenderer latoFont = new CFontRenderer("Lato", 17.0f);
    public static CFontRenderer verdanaFont = new CFontRenderer("Verdana", 17.0f);
    public static CFontRenderer comfortaaFont = new CFontRenderer("Comfortaa", 20.0f);
    public static CFontRenderer subtitleFont = new CFontRenderer("Subtitle", 17.0f);
    public static CFontRenderer comicSansFont = new CFontRenderer("ComicSans", 17.0f);

    private static int currentSize;
    private static String currentFontName;
    private static CFontRenderer current;

    public static CFontRenderer getCurrentCustomFont() {
        if (ClientFont.size.getValue() != currentSize || ClientFont.font.getValue().toString() != currentFontName) {
            currentSize = ClientFont.size.getValue();
            currentFontName = ClientFont.font.getValue().toString();
            current = new CFontRenderer(currentFontName, currentSize);
        }

        return current;
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
