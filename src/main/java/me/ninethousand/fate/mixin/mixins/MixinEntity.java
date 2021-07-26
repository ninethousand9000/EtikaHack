package me.ninethousand.fate.mixin.mixins;

import me.ninethousand.fate.mixin.accessors.IEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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

