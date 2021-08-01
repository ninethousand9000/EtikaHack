package me.ninethousand.fate.api.ui.newgui.components.category.module.setting.settings;

import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.ui.newgui.GuiColors;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BooleanComponent extends GUIComponent {
    public Setting<Boolean> setting;

    public BooleanComponent(Setting<Boolean> setting, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setting = setting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightDown) setting.setOpened(!setting.isOpened());
            if (GuiUtil.leftDown) setting.setValue(!setting.getValue());
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        Color fontEnable = GuiColors.font;
        if (setting.getValue()) fontEnable = GuiColors.accent;

        FontUtil.drawText(setting.getName(), getPositionX() + 3, getPositionY() +  FontUtil.getStringHeight(setting.getName()) / 2, fontEnable.getRGB());

        if (setting.getSubSettings().size() > 0) {
            String text = ">";
            if (setting.isOpened()) text = "v";

            FontUtil.drawText(text, getPositionX() + getWidth() - 3 - FontUtil.getStringWidth(text), getPositionY() + FontUtil.getStringHeight(text) / 2, GuiColors.font.getRGB());
        }
    }
}
