package me.ninethousand.etikahack.impl.modules.client;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.misc.DiscordUtil;

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
