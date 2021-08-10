package me.ninethousand.etikahack.impl.modules.movement;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;

@ModuleAnnotation(category = ModuleCategory.MOVEMENT)
public class AntiVoid extends Module {
    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        if (mc.player.posY < 1) mc.player.motionY = 0;
    }
}
