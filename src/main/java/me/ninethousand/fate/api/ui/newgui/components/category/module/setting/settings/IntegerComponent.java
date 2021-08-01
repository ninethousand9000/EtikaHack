package me.ninethousand.fate.api.ui.newgui.components.category.module.setting.settings;

import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.ui.newgui.ClickGUIScreen;
import me.ninethousand.fate.api.ui.newgui.GuiColors;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;

import java.awt.*;

public class IntegerComponent extends GUIComponent {
    public NumberSetting<Integer> setting;

    public IntegerComponent(NumberSetting<Integer> setting, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setting = setting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightClicked) setting.setOpened(!setting.isOpened());
            if (GuiUtil.leftDown) {
                int percentError = (GuiUtil.mouseX - (getPositionX())) * 100 / getWidth();
                setting.setValue((int) (percentError * ((setting.getMax() - setting.getMin()) / 100.0D) + setting.getMin()));
            }
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        int pixAdd = getWidth() * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());

        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + pixAdd, getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));


        FontUtil.drawText(setting.getName() + ":", getPositionX() + 3, getPositionY() +  FontUtil.getStringHeight(setting.getName()) / 2, GuiColors.accent.getRGB());
        FontUtil.drawText(setting.getValue().toString(), getPositionX() + 5 + FontUtil.getStringWidth(setting.getName() + ":"), getPositionY() + FontUtil.getStringHeight(setting.getValue().toString()) / 2, GuiColors.font.getRGB());


        if (setting.getSubSettings().size() > 0) {
            String text = ">";
            if (setting.isOpened()) text = "v";

            FontUtil.drawText(text, getPositionX() + getWidth() - 3 - FontUtil.getStringWidth(text), getPositionY() + FontUtil.getStringHeight(text) / 2, GuiColors.font.getRGB());
        }
    }
}
