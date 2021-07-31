package me.ninethousand.fate.api.ui.newgui.components;

import me.ninethousand.fate.api.util.misc.GuiUtil;

public abstract class GUIComponent {
    private int positionX, positionY, width, height;

    public GUIComponent(int positionX, int positionY, int width, int height) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.width = width;
        this.height = height;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void onClicked(int mouseX, int mouseY, int mouseButton);

    public abstract void drawComponent(int mouseX, int mouseY);

    protected boolean isHoveringOverComponent(int mouseX, int mouseY) {
        return GuiUtil.mouseOver(mouseX, mouseY, mouseX + width, mouseY + height);
    }
}