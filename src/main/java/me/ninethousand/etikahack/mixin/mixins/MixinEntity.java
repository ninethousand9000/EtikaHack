package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.mixin.accessors.IEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class MixinEntity implements IEntity {
    @Shadow
    protected boolean isInWeb;

    @Shadow
    protected boolean inPortal;


    @Override
    public boolean getIsInWeb() {
        return isInWeb;
    }

    @Override
    public void setIsInWeb(boolean isInWeb) {
        this.isInWeb = isInWeb;
    }

    @Override
    public boolean isInPortal() {
        return inPortal;
    }
}

