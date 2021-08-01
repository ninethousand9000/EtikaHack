package me.ninethousand.fate.impl.modules.client;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.CLIENT, alwaysEnabled = true)
public class Customise extends Module {
    public static final Setting<String> clientName = new Setting<>("Name", "Fate");
    public static final Setting<Color> clientColor = new Setting<>("Color", new Color(0x8019B8));

    public Customise() {
        addSettings(clientColor, clientName);
    }
}
