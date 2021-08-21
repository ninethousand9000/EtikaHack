package me.ninethousand.etikahack.impl.modules.player;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;

@ModuleAnnotation(category = ModuleCategory.PLAYER)
public class NoHitbox extends Module {
    public static final Setting<Boolean> onlyPickaxe = new Setting<>("OnlyPickaxe", true);

    public NoHitbox() {
        addSettings(onlyPickaxe);
    }
}
