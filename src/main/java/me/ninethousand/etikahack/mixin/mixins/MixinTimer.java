package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.mixin.accessors.ITimer;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Timer.class)
public class MixinTimer implements ITimer {
    @Shadow
    private float tickLength;

    @Override
    public float getTickLength() {
        return tickLength;
    }

    @Override
    public void setTickLength(float tickLength) {
        this.tickLength = tickLength;
    }
}
