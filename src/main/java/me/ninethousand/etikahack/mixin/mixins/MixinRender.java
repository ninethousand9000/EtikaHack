package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.impl.modules.visual.PlayerChams;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(Render.class)
public abstract class MixinRender<T extends Entity> {
    @Overwrite
    protected boolean bindEntityTexture(T entity) {
        ResourceLocation resourcelocation = null;

        if (entity instanceof EntityPlayer) {
            resourcelocation = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        }

        else {
            resourcelocation = this.getEntityTexture(entity);
        }

        if (resourcelocation == null)
        {
            return false;
        }
        else
        {
            this.bindTexture(resourcelocation);
            return true;
        }
    }

    @Shadow
    protected abstract ResourceLocation getEntityTexture(T entity);

    @Shadow
    public abstract void bindTexture(ResourceLocation location);
}
