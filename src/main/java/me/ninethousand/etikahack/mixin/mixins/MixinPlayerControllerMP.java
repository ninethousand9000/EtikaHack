package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.event.events.PlayerDamageBlockEvent;
import me.ninethousand.etikahack.mixin.accessors.IPlayerControllerMP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public final class MixinPlayerControllerMP implements IPlayerControllerMP {
    @Shadow
    private float curBlockDamageMP;

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    public void onOnPlayerDamageBlockHead(BlockPos pos, EnumFacing facing, CallbackInfoReturnable<Boolean> cir) {
        PlayerDamageBlockEvent playerDamageBlockEvent = new PlayerDamageBlockEvent(pos, facing);
        MinecraftForge.EVENT_BUS.post(playerDamageBlockEvent);
        if (playerDamageBlockEvent.isCanceled()) cir.setReturnValue(false);
    }

    @Override
    public float getCurBlockDamageMP() {
        return curBlockDamageMP;
    }
}
