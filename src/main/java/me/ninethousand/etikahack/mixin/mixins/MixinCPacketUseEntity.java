package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.mixin.accessors.ICPacketUseEntity;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CPacketUseEntity.class)
public class MixinCPacketUseEntity implements ICPacketUseEntity {
    @Shadow
    protected int entityId;

    @Shadow
    protected CPacketUseEntity.Action action;

    @Override
    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void setEntityAction(CPacketUseEntity.Action action) {
        this.action = action;
    }
}
