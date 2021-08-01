package me.ninethousand.fate.api.ui.newgui.components.category.module.setting;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.ui.newgui.GuiColors;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.ui.newgui.components.category.module.setting.settings.*;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import scala.Int;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SettingComponent extends GUIComponent {
    public Module module;
    private List<GUIComponent> settingComponents = new ArrayList<>();
    private int drawX, drawY;

    public SettingComponent(Module module, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.module = module;

        drawX = positionX;
        drawY = positionY;

        module.getSettings().forEach(setting -> createSettings(setting));
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        settingComponents.forEach(settingComponent -> settingComponent.onClicked(mouseX, mouseY));
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        GraphicsUtil2d.drawRectFill(GraphicsUtil2d.vertexHelperUB,
                new Vec2d(getPositionX(), getPositionY()),
                new Vec2d(getPositionX() + 1, drawY),
                new Color(GuiColors.accent.getRed(), GuiColors.accent.getGreen(), GuiColors.accent.getBlue(), 255));

        settingComponents.forEach(settingComponent -> settingComponent.drawComponent(mouseX, mouseY));
    }

    private void createSettings(Setting<?> setting) {
        if (setting.getValue() instanceof Boolean) {
            settingComponents.add(new BooleanComponent((Setting<Boolean>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += getHeight();
        }

        else if (setting.getValue() instanceof Enum) {
            settingComponents.add(new EnumComponent((Setting<Enum>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += getHeight();
        }

        else if (setting.getValue() instanceof String) {
            settingComponents.add(new StringComponent((Setting<String>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += getHeight();
        }

        else if (setting.getValue() instanceof Integer) {
            settingComponents.add(new IntegerComponent((NumberSetting<Integer>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += getHeight();
        }

        else if (setting.getValue() instanceof Float) {
            settingComponents.add(new FloatComponent((NumberSetting<Float>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += getHeight();
        }

        else if (setting.getValue() instanceof Double) {
            settingComponents.add(new DoubleComponent((NumberSetting<Double>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += getHeight();
        }

        else if (setting.getValue() instanceof Color) {
            settingComponents.add(new BetterColorComponent((Setting<Color>) setting, drawX, drawY, getWidth(), getHeight()));
            drawY += setting.isOpened() ? getHeight() + (20 - getHeight()) + 40 + getHeight() : getHeight();
        }

        if (setting.isOpened()) setting.getSubSettings().forEach(setting1 -> createSettings(setting1));
    }

    public int getBoostY() {
        return drawY - getPositionY();
    }
}
