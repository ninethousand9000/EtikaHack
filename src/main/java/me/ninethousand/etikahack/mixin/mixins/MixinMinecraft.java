package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.mixin.accessors.IMinecraft;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public final class MixinMinecraft implements IMinecraft {
    @Shadow
    @Final
    private Timer timer;

    @Shadow
    private int rightClickDelayTimer;

    @Override
    public int getRightClickDelayTimer() {
        return rightClickDelayTimer;
    }

    @Override
    public void setRightClickDelayTimer(final int rightClickDelayTimer) {
        this.rightClickDelayTimer = rightClickDelayTimer;
    }

    @Override
    public Timer getTimer() {
        return timer;
    }
}
