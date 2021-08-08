package me.ninethousand.fate.mixin.mixins;

import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.impl.modules.visual.SkyColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {
    @Redirect(method = "renderCloudsFancy", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
    public void colorClouds(final float red, final float blue, final float green, final float alpha) {
        if (ModuleManager.getModule(SkyColor.class).isEnabled()) {
            GlStateManager.color(SkyColor.skyColor.getValue().getRed() / 255f, SkyColor.skyColor.getValue().getGreen() / 255f, SkyColor.skyColor.getValue().getBlue() / 255f, SkyColor.skyColor.getValue().getAlpha() / 255f);
        } else {
            GlStateManager.color(red, blue, green, alpha);
        }
    }
}
