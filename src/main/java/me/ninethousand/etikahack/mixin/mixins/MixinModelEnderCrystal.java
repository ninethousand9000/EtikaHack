package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.impl.modules.visual.PlayerChams;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelEnderCrystal.class)
public class MixinModelEnderCrystal {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/fate/cool/sky.png");

    @Shadow
    @Final
    private ModelRenderer cube;

    @Shadow
    @Final
    private ModelRenderer glass;

    @Shadow
    private ModelRenderer base;

    @Overwrite
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1, 1, 1, PlayerChams.textureAlpha.getValue() / 255f);
        mc.getTextureManager().bindTexture(RES_ITEM_GLINT);

        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.translate(0.0F, -0.5F, 0.0F);

        if (this.base != null)
        {
            this.base.render(scale);
        }

        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.8F + ageInTicks, 0.0F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        this.glass.render(scale);
        float f = 0.875F;
        GlStateManager.scale(0.875F, 0.875F, 0.875F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        this.glass.render(scale);
        GlStateManager.scale(0.875F, 0.875F, 0.875F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(limbSwingAmount, 0.0F, 1.0F, 0.0F);
        this.cube.render(scale);

        GlStateManager.popMatrix();
    }
}
