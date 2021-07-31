package me.ninethousand.fate.api.ui.newgui.components.category.module;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.ui.newgui.components.GUIComponent;

public class ModuleComponent extends GUIComponent {
    public Module module;

    public ModuleComponent(Module module, int positionX, int positionY, int width, int height) {
        super(positionX, positionY, width, height);
        this.module = module;
    }

    @Override
    public void onClicked(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void drawComponent(int mouseX, int mouseY) {

    }
}
