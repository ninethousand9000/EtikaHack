package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.impl.modules.visual.CrystalChams;
import me.ninethousand.etikahack.impl.modules.visual.PlayerChams;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderEnderCrystal.class)
public abstract class MixinRenderCrystal extends Render<EntityEnderCrystal> {
    @Shadow
    private static final ResourceLocation ENDER_CRYSTAL_TEXTURES = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");

    @Shadow
    private final ModelBase modelEnderCrystal = new ModelEnderCrystal(0.0F, true);

    @Shadow
    private final ModelBase modelEnderCrystalNoBase = new ModelEnderCrystal(0.0F, false);

    public MixinRenderCrystal(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    @Overwrite
    public void doRender(EntityEnderCrystal entity, double x, double y, double z, float entityYaw, float partialTicks) {
        float f = (float)entity.innerRotation + partialTicks;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        this.bindTexture(ENDER_CRYSTAL_TEXTURES);
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = f1 * f1 + f1;

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        else {
            ModelBase modelToRender;

            if (entity.shouldShowBottom()) {
                modelToRender = this.modelEnderCrystal;
            }

            else {
                modelToRender = this.modelEnderCrystalNoBase;
            }

            if (ModuleManager.getModule(CrystalChams.class).isEnabled()) {
                float
                        r = CrystalChams.crystalColor.getValue().getRed() / 255f,
                        g = CrystalChams.crystalColor.getValue().getGreen() / 255f,
                        b = CrystalChams.crystalColor.getValue().getBlue() / 255f,
                        a = CrystalChams.crystalColor.getValue().getAlpha() / 255f,
                        ro = CrystalChams.crystalColorO.getValue().getRed() / 255f,
                        go = CrystalChams.crystalColorO.getValue().getGreen() / 255f,
                        bo = CrystalChams.crystalColorO.getValue().getBlue() / 255f,
                        ao = CrystalChams.crystalColorO.getValue().getAlpha() / 255f;

                if (CrystalChams.crystalModel.getValue()) {
                    modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
                }

                if (CrystalChams.crystalMode.getValue() == CrystalChams.ChamMode.Fill ||
                        CrystalChams.crystalMode.getValue() == CrystalChams.ChamMode.Pretty) {
                    GlStateManager.pushMatrix();
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                    if (CrystalChams.middleWall.getValue()) {
                        GL11.glDisable(GL11.GL_DEPTH_TEST);
                        GL11.glDepthMask(false);
                    }

                    GL11.glColor4f(r, g, b, a);
                    modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthMask(true);

                    GL11.glColor4f(r, g, b, a);
                    modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GlStateManager.popAttrib();
                    GlStateManager.popMatrix();
                }

                if (CrystalChams.crystalMode.getValue() == CrystalChams.ChamMode.Wireframe ||
                        CrystalChams.crystalMode.getValue() == CrystalChams.ChamMode.Pretty) {

                    GlStateManager.pushMatrix();
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_LINE_SMOOTH);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                    if (CrystalChams.lineWall.getValue()) {
                        GL11.glDisable(GL11.GL_DEPTH_TEST);
                    }

                    GL11.glColor4f(ro, go, bo, ao);
                    GL11.glLineWidth(CrystalChams.crystalOutlineWidth.getValue());
                    modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
                    GL11.glEnable(2896);
                    GlStateManager.popAttrib();
                    GlStateManager.popMatrix();
                }

                if (CrystalChams.crystalMode.getValue() == CrystalChams.ChamMode.Normal){
                    GlStateManager.pushMatrix();
                    GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
                    GL11.glPolygonOffset(1.0f, -1000000.0f);
                    GL11.glDepthMask(false);

                    modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);

                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthMask(true);

                    modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);

                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glPolygonOffset(1.0f, 1000000.0f);
                    GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);

                    GlStateManager.popAttrib();
                    GlStateManager.popMatrix();
                }
            }

            else {
                modelToRender.render(entity, 0.0F, f * 3.0F, f1 * 0.2F, 0.0F, 0.0F, 0.0625F);
            }
        }

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();
        BlockPos blockpos = entity.getBeamTarget();

        if (blockpos != null)
        {
            this.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
            float f2 = (float)blockpos.getX() + 0.5F;
            float f3 = (float)blockpos.getY() + 0.5F;
            float f4 = (float)blockpos.getZ() + 0.5F;
            double d0 = (double)f2 - entity.posX;
            double d1 = (double)f3 - entity.posY;
            double d2 = (double)f4 - entity.posZ;
            RenderDragon.renderCrystalBeams(x + d0, y - 0.3D + (double)(f1 * 0.4F) + d1, z + d2, partialTicks, (double)f2, (double)f3, (double)f4, entity.innerRotation, entity.posX, entity.posY, entity.posZ);
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    @Shadow
    protected abstract ResourceLocation getEntityTexture(EntityEnderCrystal entity);

    @Shadow
    public abstract boolean shouldRender(EntityEnderCrystal livingEntity, ICamera camera, double camX, double camY, double camZ);
}
