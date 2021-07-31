package me.ninethousand.fate.impl.modules.combat;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.util.game.BlockInteractionHelper;
import me.ninethousand.fate.api.util.game.BlockUtil;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

@ModuleAnnotation(category = ModuleCategory.COMBAT)
public class Surround extends Module {
    @Override
    public void onUpdate() {
        BlockPos player = mc.player.getPosition();

        for (EnumFacing direction : EnumFacing.values()) {
            if (direction == EnumFacing.UP || direction == EnumFacing.UP) continue;
            if (BlockUtil.isBlockEmpty(player.offset(direction))) {
                BlockInteractionHelper.placeBlockScaffold(player.offset(direction));
            }
        }
    }
}
