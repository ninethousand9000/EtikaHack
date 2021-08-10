package me.ninethousand.etikahack.api.event.events;

import me.ninethousand.etikahack.api.event.EventStage;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public final class PlayerDamageBlockEvent extends EventStage {
    private final BlockPos pos;
    private final EnumFacing direction;

    public PlayerDamageBlockEvent(BlockPos pos, EnumFacing direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public BlockPos pos() {
        return pos;
    }

    public EnumFacing direction() {
        return direction;
    }
}
