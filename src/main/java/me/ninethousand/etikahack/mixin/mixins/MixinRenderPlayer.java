package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.impl.modules.visual.EtikaMode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value={RenderPlayer.class})
public class MixinRenderPlayer {
    @Overwrite
    public ResourceLocation getEntityTexture(final AbstractClientPlayer entity) {
        if (ModuleManager.getModule(EtikaMode.class).isEnabled()) {
            GL11.glColor4f(1f, 1f, 1f, 1f);
            return new ResourceLocation("textures/fate/skins/etikafun.png");
        }
        return entity.getLocationSkin();
    }
}
