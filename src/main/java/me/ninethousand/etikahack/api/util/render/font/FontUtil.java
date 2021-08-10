package me.ninethousand.etikahack.api.util.render.font;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.util.math.MathUtil;
import me.ninethousand.etikahack.impl.modules.client.ClientFont;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class FontUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    //Font
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

    public static void drawTextHUD(String text, float x, float y) {
        if (!Customise.rolling.getValue()) {
            drawText(text, x, y, Customise.hudColor.getValue().getRGB());
        }

        else {
            drawTextRainbow(text, x, y, Customise.hudColor.getValue(), Customise.factor.getValue());
        }
    }

    public static void drawTextRainbow(String text, float x, float y, Color startColor, float factor) {
        Color currentColor = startColor;
        float hueIncrement = 1.0f / factor;
        String[] rainbowStrings = text.split("\u00a7.");
        float currentHue = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[0];
        float saturation = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[1];
        float brightness = Color.RGBtoHSB(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), null)[2];
        int currentWidth = 0;
        boolean shouldRainbow = true;
        boolean shouldContinue = false;
        for (int i = 0; i < text.length(); ++i) {
            char currentChar = text.charAt(i);
            char nextChar = text.charAt(MathUtil.clamp(i + 1, 0, text.length() - 1));
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                shouldRainbow = false;
            } else if ((String.valueOf(currentChar) + nextChar).equals("\u00a7+")) {
                shouldRainbow = true;
            }
            if (shouldContinue) {
                shouldContinue = false;
                continue;
            }
            if ((String.valueOf(currentChar) + nextChar).equals("\u00a7r")) {
                String escapeString = text.substring(i);
                drawText(escapeString, x + currentWidth, y, Color.WHITE.getRGB());
                break;
            }
            drawText(String.valueOf(currentChar).equals("\u00a7") ? "" : String.valueOf(currentChar), x + currentWidth, y, shouldRainbow ? currentColor.getRGB() : Color.WHITE.getRGB());
            if (String.valueOf(currentChar).equals("\u00a7")) {
                shouldContinue = true;
            }
            currentWidth += getStringWidth(String.valueOf(currentChar));
            if (String.valueOf(currentChar).equals(" ")) continue;
            currentColor = new Color(Color.HSBtoRGB(currentHue, saturation, brightness));
            currentHue += hueIncrement;
        }
    }

    public static float getStringWidth(String text) {
        return mc.fontRenderer.getStringWidth(text);
    }

    public static float getStringHeight(String text) {
        return 9f;
    }

    /*public static float getStringWidth(String text) {
        return getCurrentCustomFont().getStringWidth(text);
    }

    public static float getStringHeight(String text) {
        return getCurrentCustomFont().getStringHeight(text);
    }*/
}
