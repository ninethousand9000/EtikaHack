package me.ninethousand.fate.api.util.render.font;

import net.minecraft.client.Minecraft;

public final class FontUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static final CFontRenderer productSansFont = new CFontRenderer("ProductSans", 17.0f);

    public static CFontRenderer getCurrentCustomFont() {
        return productSansFont;
    }

    public static void drawText(String text, float x, float y, int color) {
        /*if (ClientFont.shadow.getValue()) {
            if (ModuleManager.getModuleByName("Font").isEnabled()) {
                getCurrentCustomFont().drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            } else {
                mc.fontRenderer.drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            }
        } else {
            if (ModuleManager.getModuleByName("Font").isEnabled()) {
                getCurrentCustomFont().drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            } else {
                mc.fontRenderer.drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, (int) x, (int) y, color);
            }
        }*/

        getCurrentCustomFont().drawStringWithShadow(text, x, y, color);
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
