package me.ninethousand.fate.mixin.mixins;

import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.util.misc.popghost.EntityPopGhost;
import me.ninethousand.fate.impl.modules.visual.EtikaMode;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderPlayer.class})
public class MixinRenderPlayer {
    @Inject(method={"renderEntityName"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        if (entityIn instanceof EntityPopGhost) info.cancel();
    }

    @Overwrite
    public ResourceLocation getEntityTexture(final AbstractClientPlayer entity) {
        if (ModuleManager.getModule(EtikaMode.class).isEnabled()) {
            GL11.glColor4f(1f, 1f, 1f, 1f);
            return new ResourceLocation("textures/fate/skins/etikafun.png");
        }
        return entity.getLocationSkin();
    }
}
