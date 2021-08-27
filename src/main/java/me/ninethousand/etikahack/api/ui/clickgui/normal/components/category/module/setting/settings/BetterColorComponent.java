package me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.module.setting.settings;

import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.ui.clickgui.normal.GuiColors;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.GUIComponent;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import me.ninethousand.etikahack.api.util.misc.GuiUtil;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;

import java.awt.*;

public class BetterColorComponent extends GUIComponent {
    public Setting<Color> setting;

    public BetterColorComponent(Setting<Color> setting, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setting = setting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightClicked) setting.setOpened(!setting.isOpened());
        }

        if (setting.isOpened()) {
            if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + 20, getPositionX() + 4 + getWidth() - 8, getPositionY() + 24)) {
                if (GuiUtil.leftDown) {
                    int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                    setting.setHue((percentError * (1 / 100.0f)));
                }
            }

            if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + 30, getPositionX() + 4 + getWidth() - 8, getPositionY() + 34)) {
                if (GuiUtil.leftDown) {
                    int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                    setting.setSaturation((percentError * (1 / 100.0f)));
                }
            }

            if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + 40, getPositionX() + 4 + getWidth() - 8, getPositionY() + 44)) {
                if (GuiUtil.leftDown) {
                    int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                    setting.setBrightness((percentError * (1 / 100.0f)));
                }
            }

            if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + 50, getPositionX() + 4 + getWidth() - 8, getPositionY() + 54)) {
                if (GuiUtil.leftDown) {
                    int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                    setting.setAlpha((percentError * (1 / 100.0f)));
                }
            }

            if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + 60, getPositionX() + 4 + getWidth() - 8, getPositionY() + 60 + getHeight())) {
                if (GuiUtil.leftClicked) {
                    setting.setRainbow(!setting.isRainbow());
                }
            }
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        FontUtil.drawText(setting.getName(), getPositionX() + 3, getPositionY() +  FontUtil.getStringHeight(setting.getName()) / 2, GuiColors.accent.getRGB());

        GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX() + FontUtil.getStringWidth(setting.getName()) + 11, getPositionY() + getHeight() / 2 - 4),
                new Vec2d(getPositionX() + FontUtil.getStringWidth(setting.getName()) + 19, getPositionY() + getHeight() / 2 + 4),
                4, 20, setting.getValue());

        if (setting.isOpened()) {
            int sliderWidth = getWidth() - 8;
            int sliderHeight = 4;

            int drawX = getPositionX() + 4;
            int drawY = getPositionY() + 20;

            GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(getPositionX(), getPositionY() + getHeight()),
                    new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight() + (20 - getHeight()) + 40),
                    new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

            for (int i = 0; i < 5; i++) {
                Color start = new Color(Color.HSBtoRGB((float) i / 5, 1.0f, 1.0f));
                Color end = new Color(Color.HSBtoRGB((((float) i + 1) / 5), 1.0f, 1.0f));

                GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                        new Vec2d(drawX, drawY),
                        new Vec2d(drawX + (sliderWidth + 10) / 5, drawY + sliderHeight),
                        start, end, true);

                drawX += sliderWidth / 5;
            }

            int circleX = (int) ((getWidth() - 8) * (setting.getHue() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, drawY),
                    new Vec2d(circleX + 4, drawY + 4),
                    2, 20, Color.WHITE);

            drawX = getPositionX() + 4;
            drawY += 10;

            GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(drawX, drawY),
                    new Vec2d(drawX + sliderWidth, drawY + sliderHeight),
                    new Color(Color.HSBtoRGB(setting.getHue(), 1f, 1f)),
                    new Color(Color.HSBtoRGB(setting.getHue(), 0f, 1f)), true);

           circleX = (int) ((getWidth() - 8) * (setting.getSaturation() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, drawY),
                    new Vec2d(circleX + 4, drawY + 4),
                    2, 20, Color.WHITE);

            drawY += 10;

            GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(drawX, drawY),
                    new Vec2d(drawX + sliderWidth, drawY + sliderHeight),
                    new Color(Color.HSBtoRGB(setting.getHue(), setting.getSaturation(), 1f)),
                    new Color(Color.HSBtoRGB(setting.getHue(), setting.getSaturation(), 0f)), true);

            circleX = (int) ((getWidth() - 8) * (setting.getBrightness() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, drawY),
                    new Vec2d(circleX + 4, drawY + 4),
                    2, 20, Color.WHITE);

            drawY += 10;

            Color c = new Color(Color.HSBtoRGB(setting.getHue(), 1 - setting.getSaturation(), 1 - setting.getBrightness()));

            GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(drawX, drawY),
                    new Vec2d(drawX + sliderWidth, drawY + sliderHeight),
                    new Color(c.getRed(), c.getGreen(), c.getBlue(), 255),
                    new Color(c.getRed(), c.getGreen(), c.getBlue(), 1), true);

            circleX = (int) ((getWidth() - 8) * (setting.getAlpha() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, drawY),
                    new Vec2d(circleX + 4, drawY + 4),
                    2, 20, Color.WHITE);

            drawY += 10;

            GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(getPositionX(), drawY),
                    new Vec2d(getPositionX() + getWidth(), drawY + getHeight()),
                    new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

            Color fontEnable = GuiColors.font;
            if (setting.isRainbow()) fontEnable = GuiColors.accent;

            FontUtil.drawText("Rainbow", getPositionX() + getWidth() / 2 - FontUtil.getStringWidth("Rainbow") / 2, drawY + FontUtil.getStringHeight("Rainbow") / 2, fontEnable.getRGB());

        }

        String text = ">";
        if (setting.isOpened()) text = "v";

        FontUtil.drawText(text, getPositionX() + getWidth() - 3 - FontUtil.getStringWidth(text), getPositionY() + FontUtil.getStringHeight(text) / 2, GuiColors.font.getRGB());
    }
}
