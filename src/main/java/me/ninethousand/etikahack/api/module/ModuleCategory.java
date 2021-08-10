package me.ninethousand.etikahack.api.module;

public enum ModuleCategory {
    COMBAT,//
    MOVEMENT,//
    PLAYER,//
    VISUAL,//
    MISC,
    HUD,
    CLIENT;

    private boolean openInGui = true;

    public boolean isOpenInGui() {
        return openInGui;
    }

    public void setOpenInGui(final boolean openInGui) {
        this.openInGui = openInGui;
    }
}
