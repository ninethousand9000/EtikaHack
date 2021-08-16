package me.ninethousand.etikahack.api.util.render.font;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.util.math.MathUtil;
import me.ninethousand.etikahack.api.util.render.font.better.CustomFont;
import me.ninethousand.etikahack.impl.modules.client.ClientFont;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    //Font
    private static float currentSize;
    private static String currentFontName;
    private static CustomFont fontRenderer = new CustomFont(new Font("Verdana", 0, 17), true, true);

    public static void updateFont() {
        if (ClientFont.size.getValue() != currentSize || ClientFont.font.getValue().toString() != currentFontName) {
            currentSize = ClientFont.size.getValue();
            currentFontName = ClientFont.font.getValue().toString();
//            fontRenderer = new CustomFont(new Font(currentFontName, 0, currentSize), true, true);

            try {
                fontRenderer = new CustomFont(getFontByName(currentFontName).deriveFont(Font.BOLD).deriveFont(currentSize), true, true);
            }

            catch (Exception e) {
                fontRenderer = new CustomFont(new Font(currentFontName, 0, (int) currentSize), true, true);
                Command.sendClientMessageDefault("Exception");
            }
        }
    }

    public static float drawText(String text, float x, float y, int color) {
        updateFont();

        if (ClientFont.shadow.getValue()) {
            if (ModuleManager.getModule(ClientFont.class).isEnabled()) {
                return fontRenderer.drawStringWithShadow(text, x, y, color);
            } else {
                return mc.fontRenderer.drawStringWithShadow(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, x, y, color);
            }
        } else {
            if (ModuleManager.getModule(ClientFont.class).isEnabled()) {
                return fontRenderer.drawString(text, x, y, color);
            } else {
                return mc.fontRenderer.drawString(ClientFont.lowercase.getValue() ? text.toLowerCase() : text, (int) x, (int) y, color);
            }
        }
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
        updateFont();

        if (ModuleManager.getModule(ClientFont.class).isEnabled()) {
            return fontRenderer.getStringWidth(text);
        }

        else {
            return mc.fontRenderer.getStringWidth(text);
        }
    }

    public static float getStringHeight(String text) {
        updateFont();

        return fontRenderer.getStringHeight(text) + 2;
    }

    private static Font font = null;

    public static Font getFontFromInput(String path) throws IOException, FontFormatException {
        font = Font.createFont(Font.TRUETYPE_FONT, FontUtil.class.getResourceAsStream(path));

        return font;
    }

    public static Font getFontByName(String name) throws IOException, FontFormatException {
        if (name == "ProductSans")
            return getFontFromInput("/assets/fate/fonts/ProductSans.ttf");

        else if (name == "Ubuntu")
            return getFontFromInput("/assets/fate/fonts/Ubuntu.ttf");

        else if (name == "Lato")
            return getFontFromInput("/assets/fate/fonts/Lato.ttf");

        else if (name == "Verdana")
            return getFontFromInput("/assets/fate/fonts/Verdana.ttf");

        else if (name == "Comfortaa")
            return getFontFromInput("/assets/fate/fonts/Comfortaa.ttf");

        else if (name == "Subtitle")
            return getFontFromInput("/assets/fate/fonts/Subtitle.ttf");

        else if (name == "ComicSans")
            return getFontFromInput("/assets/fate/fonts/ComicSans.ttf");

        else if (name == "SergoeUI")
            return getFontFromInput("/assets/fate/fonts/SergoeUI.ttf");

        else if (name == "Roboto")
            return getFontFromInput("/assets/fate/fonts/Roboto.ttf");

        else if (name == "Arial")
            return getFontFromInput("/assets/fate/fonts/Arial.ttf");

        else
            return Font.createFont(Font.TRUETYPE_FONT, new File("/assets/fate/fonts/" + name + ".ttf"));
    }
}
