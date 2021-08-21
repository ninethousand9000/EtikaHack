package me.ninethousand.etikahack.mixin.accessors;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerControllerMP.class)
public interface IPlayerControllerMP {
    @Accessor("curBlockDamageMP")
    float getCurBlockDamageMP();
}
