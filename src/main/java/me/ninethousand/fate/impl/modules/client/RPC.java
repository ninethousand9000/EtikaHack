package me.ninethousand.fate.impl.modules.client;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.misc.DiscordUtil;

@ModuleAnnotation(category = ModuleCategory.CLIENT)
public class RPC extends Module {
    public static final Setting<String> message = new Setting<>("Message", "Etika Strong");

    public RPC() {
        addSettings(message);
    }

    @Override
    public void onEnable() {
        DiscordUtil.start();
    }

    @Override
    public void onDisable() {
        DiscordUtil.stop();
    }
}