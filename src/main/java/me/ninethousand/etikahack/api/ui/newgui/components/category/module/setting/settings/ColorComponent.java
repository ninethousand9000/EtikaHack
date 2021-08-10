package me.ninethousand.etikahack.api.ui.newgui.components.category.module.setting.settings;

import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.ui.newgui.GuiColors;
import me.ninethousand.etikahack.api.ui.newgui.components.GUIComponent;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import me.ninethousand.etikahack.api.util.misc.GuiUtil;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;

import java.awt.*;

public class ColorComponent extends GUIComponent {
    public Setting<Color> setting;
    private int circleX;

    public ColorComponent(Setting<Color> setting, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setting = setting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightClicked) setting.setOpened(!setting.isOpened());
            if (GuiUtil.leftClicked) setting.setRainbow(!setting.isRainbow());
        }

        if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + (getHeight() / 2 - 2), getPositionX() + 4 + getWidth() - 4, getPositionY() + (getHeight() / 2 - 2) + 4)) {
            if (GuiUtil.leftDown) {
                int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                setting.setHue((percentError * (1 / 100.0f)));
            }
        }

        if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + (getHeight() / 2 - 2) + getHeight(), getPositionX() + 4 + getWidth() - 4, getPositionY() + (getHeight() / 2 - 2) + 4 + getHeight())) {
            if (GuiUtil.leftDown) {
                int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                setting.setSaturation((percentError * (1 / 100.0f)));
            }
        }

        if (GuiUtil.mouseOver(getPositionX() + 4, getPositionY() + (getHeight() / 2 - 2) + getHeight() + getHeight(), getPositionX() + 4 + getWidth() - 4, getPositionY() + (getHeight() / 2 - 2) + 4 + getHeight() + getHeight())) {
            if (GuiUtil.leftDown) {
                int percentError = (GuiUtil.mouseX - (getPositionX() + 4)) * 100 / (getWidth() - 4);
                setting.setBrightness((percentError * (1 / 100.0f)));
            }
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        if (setting.isRainbow()) {
            GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(getPositionX(), getPositionY()),
                    new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight() * 4),
                    new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

            int hueX = getPositionX() + 4;
            int hueY = getPositionY() + (getHeight() / 2 - 2);
            int hueWidth = getWidth() - 4;
            int hueHeight = 4;

            for (int i = 0; i < 5; i++) {
                Color start = new Color(Color.HSBtoRGB((float) i / 5, 1.0f, 1.0f));
                Color end = new Color(Color.HSBtoRGB((((float) i + 1) / 5), 1.0f, 1.0f));

                GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                        new Vec2d(hueX, hueY),
                        new Vec2d(hueX + hueWidth / 5, hueY + hueHeight),
                        start, end, true);

                hueX += hueWidth / 5;
            }

            circleX = (int) ((getWidth() - 8) * (setting.getHue() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, hueY),
                    new Vec2d(circleX + 4, hueY + 4),
                    2, 20, Color.WHITE);

            int satX = getPositionX() + 4;
            int satY = getHeight() + hueY;
            int satWidth = getWidth() - 4;
            int satHeight = 4;

            GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(satX, satY),
                    new Vec2d(satX + satWidth, satY + satHeight),
                    new Color(Color.HSBtoRGB(setting.getHue(), 1f, 1f)),
                    new Color(Color.HSBtoRGB(setting.getHue(), 0f, 1f)), true);

            circleX = (int) ((getWidth() - 8) * (setting.getSaturation() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, satY),
                    new Vec2d(circleX + 4, satY + 4),
                    2, 20, Color.WHITE);


            int briX = getPositionX() + 4;
            int briY = getHeight() + satY;
            int briWidth = getWidth() - 4;
            int briHeight = 4;

            GraphicsUtil2d.drawRectGradient(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(briX, briY),
                    new Vec2d(briX + briWidth, briY + briHeight),
                    new Color(Color.HSBtoRGB(setting.getHue(), setting.getSaturation(), 1f)),
                    new Color(Color.HSBtoRGB(setting.getHue(), setting.getSaturation(), 0f)), true);

            circleX = (int) ((getWidth() - 8) * (setting.getBrightness() / 1) + getPositionX() + 4);

            GraphicsUtil2d.drawRoundedRectangleFilled(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(circleX, briY),
                    new Vec2d(circleX + 4, briY + 4),
                    2, 20, Color.WHITE);

            setting.setValue(new Color(Color.HSBtoRGB(setting.getHue(), 1 - setting.getSaturation(), 1 - setting.getBrightness())));

            int finX = getPositionX() + 4;
            int finY = getHeight() + briY;
            int finWidth = getWidth() - 4;
            int finHeight = 4;

            GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(finX, finY),
                    new Vec2d(finX + finWidth, finY + finHeight),
                    setting.getValue());
        }

        else {
            GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                    new Vec2d(getPositionX(), getPositionY()),
                    new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                    new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

            FontUtil.drawText(setting.getName(), getPositionX() + 3, getPositionY() +  FontUtil.getStringHeight(setting.getName()) / 2, GuiColors.accent.getRGB());
        }
    }
}
