package me.ninethousand.fate.api.ui.newgui.components.category;

import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.ui.newgui.GuiColors;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import me.ninethousand.fate.impl.modules.client.ClickGUI;

import java.awt.*;

public class CategoryHeaderComponent extends GUIComponent {
    public ModuleCategory category;

    public CategoryHeaderComponent(ModuleCategory category, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.category = category;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightDown) category.setOpenInGui(!category.isOpenInGui());
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 200));

        FontUtil.drawText(category.name(), getPositionX() + getWidth() / 2 - FontUtil.getStringWidth(category.name()) / 2, getPositionY() + 4, GuiColors.font.getRGB());

        if (ClickGUI.topAccent.getValue()) {
            GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY() + getHeight()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight() + 2),
                new Color(GuiColors.accent.getRed(), GuiColors.accent.getGreen(), GuiColors.accent.getBlue(), 255));
        }
    }
}
