package me.ninethousand.etikahack.api.ui.clickgui.normal.components.panel;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.GUIComponent;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.CategoryHeaderComponent;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.module.ModuleComponent;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.module.setting.SettingComponent;
import me.ninethousand.etikahack.api.ui.clickgui.normal.components.category.module.setting.settings.BindComponent;
import me.ninethousand.etikahack.api.util.misc.GuiUtil;
import me.ninethousand.etikahack.impl.modules.client.ClickGUI;

import java.util.ArrayList;
import java.util.List;

public class PanelComponent extends GUIComponent {
    private List<GUIComponent> components = new ArrayList<>();
    private int draggingPositionX, draggingPositionY;
    private boolean dragging = false, pinned = false;
    private int offsetX = 0, offsetY = 0;

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

        if (GuiUtil.mouseOver(getPositionX(), getPositionY(), getPositionX() + getWidth(), getPositionY() + getHeight())) {
            if (GuiUtil.leftDown) {
                if (!dragging) {
                    offsetX = mouseX - getPositionX();
                    offsetY = mouseY - getPositionY();
                }

                dragging = true;

                setPositionX(mouseX - offsetX);
                setPositionY(mouseY - offsetY);
            }

            else {
                dragging = false;
            }
        }

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
