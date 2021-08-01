package me.ninethousand.fate.api.ui.newgui.components.category.module.setting.settings;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.ui.newgui.GuiColors;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class BindComponent extends GUIComponent {
    public Module module;

    public BindComponent(Module module, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.module = module;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.leftClicked) {
                module.setBinding(!module.isBinding());
                GuiUtil.keyDown = Keyboard.KEY_NONE;
            }
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        Color fontEnable = Color.BLUE;
        if (module.isBinding()) fontEnable = GuiColors.accent;

        if (module.isBinding()) {
            int newKey = GuiUtil.keyDown;

            if (newKey == Keyboard.KEY_BACK || newKey == Keyboard.KEY_DELETE || newKey == Keyboard.KEY_ESCAPE) {
                module.setKey(Keyboard.KEY_NONE);
                module.setBinding(false);
            }

            else if (newKey == Keyboard.KEY_RETURN) {
                module.setBinding(false);
            }

            else if (newKey != Keyboard.KEY_NONE){
                module.setKey(newKey);
                module.setBinding(false);
            }
        }

        FontUtil.drawText("Bind:", getPositionX() + 3, getPositionY() + FontUtil.getStringHeight("Bind:") / 2, GuiColors.accent.getRGB());
        FontUtil.drawText(module.isBinding() ? "..." : Keyboard.getKeyName(module.getKey()), getPositionX() + 5 + FontUtil.getStringWidth("Bind:"), getPositionY() + FontUtil.getStringHeight("[") / 2, GuiColors.font.getRGB());
    }
}