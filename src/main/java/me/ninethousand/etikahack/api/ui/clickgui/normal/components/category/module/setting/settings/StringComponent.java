package me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.module.setting.settings;

import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.ui.clickgui.normal.GuiColors;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.GUIComponent;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import me.ninethousand.etikahack.api.util.misc.GuiUtil;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class StringComponent extends GUIComponent {
    public Setting<String> setting;

    public StringComponent(Setting<String> setting, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.setting = setting;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.rightClicked) setting.setOpened(!setting.isOpened());
            if (GuiUtil.leftClicked) setting.setTyping(!setting.isTyping());
        }
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + getWidth(), getPositionY() + getHeight()),
                new Color(GuiColors.normal.getRed(), GuiColors.normal.getGreen(), GuiColors.normal.getBlue(), 150));

        Color fontEnable = Color.BLUE;
        if (setting.isTyping()) fontEnable = GuiColors.accent;

        typeKey(GuiUtil.keyDown);

        FontUtil.drawText(setting.getName() + ":", getPositionX() + 3, getPositionY() +  FontUtil.getStringHeight(setting.getName()) / 2, GuiColors.accent.getRGB());
        FontUtil.drawText(setting.isTyping() ? setting.getValue() + "_" : setting.getValue(), getPositionX() + 5 + FontUtil.getStringWidth(setting.getName() + ":"), getPositionY() + FontUtil.getStringHeight(setting.getValue().toString()) / 2, GuiColors.font.getRGB());

        if (setting.getSubSettings().size() > 0) {
            String text = ">";
            if (setting.isOpened()) text = "v";

            FontUtil.drawText(text, getPositionX() + getWidth() - 3 - FontUtil.getStringWidth(text), getPositionY() + FontUtil.getStringHeight(text) / 2, GuiColors.font.getRGB());
        }
    }

    private void typeKey(int key) {
        if (setting.isTyping() && key != Keyboard.KEY_NONE) {
            if (key == Keyboard.KEY_RETURN || key == Keyboard.KEY_ESCAPE) {
                setting.setTyping(false);

                return;
            }

            if (key == Keyboard.KEY_BACK) {
                if (!setting.getValue().isEmpty()) setting.setValue(setting.getValue().substring(0, setting.getValue().length() - 1));

                return;
            }

            boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

            if (key == Keyboard.KEY_SPACE) setting.setValue(setting.getValue() + " ");
            else if (key == Keyboard.KEY_PERIOD) setting.setValue(setting.getValue() + (shift ? ">" : "."));
            else if (key == Keyboard.KEY_COMMA) setting.setValue(setting.getValue() + (shift ? "<" : ","));
            else if (key == Keyboard.KEY_SEMICOLON) setting.setValue(setting.getValue() + (shift ? ":" : ";"));
            else if (key == Keyboard.KEY_GRAVE) setting.setValue(setting.getValue() + (shift ? "~" : "`"));
            else if (key == Keyboard.KEY_APOSTROPHE) setting.setValue(setting.getValue() + (shift ? "\"" : "'"));
            else if (key == Keyboard.KEY_LBRACKET) setting.setValue(setting.getValue() + (shift ? "{" : "["));
            else if (key == Keyboard.KEY_RBRACKET) setting.setValue(setting.getValue() + (shift ? "}" : "]"));
            else if (key == Keyboard.KEY_MINUS) setting.setValue(setting.getValue() + (shift ? "_" : "-"));
            else if (key == Keyboard.KEY_EQUALS) setting.setValue(setting.getValue() + (shift ? "+" : "="));
            else if (key == Keyboard.KEY_BACKSLASH) setting.setValue(setting.getValue() + (shift ? "|" : "\\"));
            else if (key == Keyboard.KEY_SLASH) setting.setValue(setting.getValue() + (shift ? "?" : "/"));
            else if (key == Keyboard.KEY_1 && shift) setting.setValue(setting.getValue() + "!");
            else if (key == Keyboard.KEY_2 && shift) setting.setValue(setting.getValue() + "@");
            else if (key == Keyboard.KEY_3 && shift) setting.setValue(setting.getValue() + "#");
            else if (key == Keyboard.KEY_4 && shift) setting.setValue(setting.getValue() + "$");
            else if (key == Keyboard.KEY_5 && shift) setting.setValue(setting.getValue() + "%");
            else if (key == Keyboard.KEY_6 && shift) setting.setValue(setting.getValue() + "^");
            else if (key == Keyboard.KEY_7 && shift) setting.setValue(setting.getValue() + "&");
            else if (key == Keyboard.KEY_8 && shift) setting.setValue(setting.getValue() + "*");
            else if (key == Keyboard.KEY_9 && shift) setting.setValue(setting.getValue() + "(");
            else if (key == Keyboard.KEY_0 && shift) setting.setValue(setting.getValue() + ")");
            else if (key != Keyboard.KEY_CAPITAL && key != Keyboard.KEY_TAB && key != Keyboard.KEY_DELETE &&
                    key != Keyboard.KEY_LCONTROL && key != Keyboard.KEY_LSHIFT && key != Keyboard.KEY_LMENU && key != Keyboard.KEY_LMETA &&
                    key != Keyboard.KEY_RCONTROL && key != Keyboard.KEY_RSHIFT && key != Keyboard.KEY_RMENU && key != Keyboard.KEY_RMETA)
                setting.setValue(setting.getValue() + (shift ? Keyboard.getKeyName(key).toUpperCase() : Keyboard.getKeyName(key).toLowerCase()).charAt(0));
        }
    }
}
