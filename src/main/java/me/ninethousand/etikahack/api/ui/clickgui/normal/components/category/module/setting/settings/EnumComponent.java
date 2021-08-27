package me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.module.setting.settings;

import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.ui.clickgui.normal.GuiColors;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.GUIComponent;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import me.ninethousand.etikahack.api.util.misc.GuiUtil;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;

import java.awt.*;

public class EnumComponent extends GUIComponent {
    public Setting<Enum> setting;
    public int startX, endX;

    public EnumComponent(Setting<Enum> setting, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setting = setting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightClicked) setting.setOpened(!setting.isOpened());
            if (GuiUtil.leftClicked) changeValue();
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        FontUtil.drawText(setting.getName() + ":", getPositionX() + 3, getPositionY() +  FontUtil.getStringHeight(setting.getName()) / 2, GuiColors.accent.getRGB());
        FontUtil.drawText(setting.getValue().toString(), getPositionX() + 5 + FontUtil.getStringWidth(setting.getName() + ":"), getPositionY() + FontUtil.getStringHeight(setting.getValue().toString()) / 2, GuiColors.font.getRGB());

        if (setting.getSubSettings().size() > 0) {
            String text = ">";
            if (setting.isOpened()) text = "v";

            FontUtil.drawText(text, getPositionX() + getWidth() - 3 - FontUtil.getStringWidth(text), getPositionY() + FontUtil.getStringHeight(text) / 2, GuiColors.font.getRGB());
        }
    }

    private <E extends Enum<E>> void changeValue() {
        final E[] values = (E[]) setting.getValue().getDeclaringClass().getEnumConstants();
        final int ordinal = setting.getValue().ordinal();

        setting.setValue(values[ordinal + 1 >= values.length ? 0 : ordinal + 1]);
    }
}
