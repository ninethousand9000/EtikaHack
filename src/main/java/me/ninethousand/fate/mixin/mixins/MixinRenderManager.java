package me.ninethousand.fate.mixin.mixins;

import me.ninethousand.fate.mixin.accessors.IRenderManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderManager.class)
public class MixinRenderManager implements IRenderManager {
    @Shadow
    private double renderPosX;

    @Shadow
    private double renderPosY;

    @Shadow
    private double renderPosZ;

    @Shadow
    @Final
    private RenderPlayer playerRenderer;

    @Override
    public double getRenderPosX() {
        return renderPosX;
    }

    @Override
    public double getRenderPosY() {
        return renderPosY;
    }

    @Override
    public double getRenderPosZ() {
        return renderPosZ;
    }

    @Override
    public RenderPlayer getPlayerRenderer() {
        return playerRenderer;
    }
}
