package me.ninethousand.fate.api.module;

public enum ModuleCategory {
    Combat,//
    Movement,//
    Player,//
    Visual,//
    Exploit,
    Misc,
    Hud,
    Client;

    private boolean openInGui = true;

    public boolean isOpenInGui() {
        return openInGui;
    }

    public void setOpenInGui(final boolean openInGui) {
        this.openInGui = openInGui;
    }
}
