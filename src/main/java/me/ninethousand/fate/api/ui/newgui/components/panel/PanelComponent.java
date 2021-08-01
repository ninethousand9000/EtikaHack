package me.ninethousand.fate.api.ui.newgui.components.panel;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.ui.newgui.components.category.CategoryHeaderComponent;
import me.ninethousand.fate.api.ui.newgui.components.category.module.ModuleComponent;
import me.ninethousand.fate.api.ui.newgui.components.category.module.setting.SettingComponent;
import me.ninethousand.fate.api.ui.newgui.components.category.module.setting.settings.BindComponent;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import me.ninethousand.fate.impl.modules.client.ClickGUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelComponent extends GUIComponent {
    private List<GUIComponent> components = new ArrayList<>();
    private int draggingPositionX, draggingPositionY;
    private boolean dragging = false, pinned = false;

    public ModuleCategory category;

    public PanelComponent(ModuleCategory category, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.category = category;
    }

    @Override
    public void onClicked(int mouseX, int mouseY) {
        components.forEach(guiComponent -> guiComponent.onClicked(mouseX, mouseY));
    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        components.clear();

        int drawX = getPositionX();
        int drawY = getPositionY();
        int drawWidth = getWidth();
        int drawHeight = getHeight();

        components.add(new CategoryHeaderComponent(category, drawX, drawY, drawWidth, drawHeight));

        drawY += drawHeight;

        if (ClickGUI.topAccent.getValue()) drawY += 2;

        if (category.isOpenInGui()) {
            for (Module module : ModuleManager.getModulesByCategory(category)) {
                components.add(new ModuleComponent(module, drawX, drawY, drawWidth, drawHeight));
                drawY += drawHeight;

                if (module.isOpened()) {
                    SettingComponent settingComponent = new SettingComponent(module, drawX, drawY, drawWidth, drawHeight);
                    components.add(settingComponent);
                    drawY += settingComponent.getBoostY();
                    BindComponent bindComponent = new BindComponent(module, drawX, drawY, drawWidth, drawHeight);
                    components.add(bindComponent);
                    drawY += getHeight();
                }
            }
        }

        components.forEach(guiComponent -> {
            guiComponent.drawComponent(mouseX, mouseY);
        });
    }
}
