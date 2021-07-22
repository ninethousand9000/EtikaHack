package me.ninethousand.fate.api.module;

public enum ModuleCategory {
    Combat,
    Movement,
    Player,
    Visuals,
    Exploits,
    Misc;

    private boolean openInGui = true;

    public boolean isOpenInGui() {
        return openInGui;
    }

    public void setOpenInGui(final boolean openInGui) {
        this.openInGui = openInGui;
    }
}
