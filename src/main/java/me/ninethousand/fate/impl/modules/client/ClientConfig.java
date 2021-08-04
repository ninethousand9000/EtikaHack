package me.ninethousand.fate.impl.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.config.Config;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.CLIENT, alwaysEnabled = true)
public class ClientConfig extends Module {
    public static final Setting<Boolean> saveConfig = new Setting<>("Save", false);
    public static final Setting<Boolean> loadConfig = new Setting<>("Load", false);

    public ClientConfig() {
        addSettings(saveConfig, loadConfig);
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        if (saveConfig.getValue()) {
            Config.saveConfig();
            saveConfig.setValue(false);
            Command.sendClientMessageDefault(ChatFormatting.GREEN + "Config Saved!");
        }

        if (loadConfig.getValue()) {
            Config.loadConfig();
            loadConfig.setValue(false);
            Command.sendClientMessageDefault(ChatFormatting.GREEN + "Config Loaded!");
        }
    }
}
