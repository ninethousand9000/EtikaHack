package me.ninethousand.fate.api.ui.newgui.components.panel;

import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;
import me.ninethousand.fate.api.ui.newgui.components.category.CategoryHeaderComponent;

import java.util.ArrayList;
import java.util.List;

public class PanelComponent extends GUIComponent {
    private List<GUIComponent> components = new ArrayList<>();
    private int draggingPositionX, draggingPositionY;
    private boolean dragging = false, pinned = false, open = true;

    public ModuleCategory category;

    public PanelComponent(ModuleCategory category, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.category = category;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {
        new CategoryHeaderComponent(category, getPositionX(), getPositionY(), getWidth(), getHeight()).drawComponent(mouseX, mouseY);
    }
}
