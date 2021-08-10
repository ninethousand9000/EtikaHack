package me.ninethousand.etikahack.api.ui.newgui.components.category.module;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.ui.newgui.GuiColors;
import me.ninethousand.etikahack.api.ui.newgui.components.GUIComponent;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import me.ninethousand.etikahack.api.util.misc.GuiUtil;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;

import java.awt.*;

public class ModuleComponent extends GUIComponent {
    public Module module;

    public ModuleComponent(Module module, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.module = module;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightClicked) module.setOpened(!module.isOpened());
            if (GuiUtil.leftClicked) module.setEnabled(!module.isEnabled());
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        Color fontEnable = GuiColors.font;
        if (module.isEnabled()) fontEnable = GuiColors.accent;

        FontUtil.drawText(module.getName(), getPositionX() + 2, getPositionY() +  FontUtil.getStringHeight(module.getName()) / 2, fontEnable.getRGB());

        if (module.settings.size() > 0) {
            String text = ">";
            if (module.isOpened()) text = "v";

            FontUtil.drawText(text, getPositionX() + getWidth() - 3 - FontUtil.getStringWidth(text), getPositionY() + FontUtil.getStringHeight(text) / 2, GuiColors.font.getRGB());
        }
    }
}
