package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.impl.modules.visual.PlayerChams;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase<T extends EntityLivingBase> extends Render<T> {
    @Shadow
    private static final Logger LOGGER = LogManager.getLogger();

    @Shadow
    protected ModelBase mainModel;

    @Shadow
    protected boolean renderMarker;

    protected MixinRenderLivingBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Overwrite
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<T>(entity, RenderLivingBase.class.cast(this), partialTicks, x, y, z))) return;
        GlStateManager.pushMatrix();
        GlStateManager.disableCull();
        this.mainModel.swingProgress = this.getSwingProgress(entity, partialTicks);
        boolean shouldSit = entity.isRiding() && (entity.getRidingEntity() != null && entity.getRidingEntity().shouldRiderSit());
        this.mainModel.isRiding = shouldSit;
        this.mainModel.isChild = entity.isChild();

        try {
            float f = this.interpolateRotation(entity.prevRenderYawOffset, entity.renderYawOffset, partialTicks);
            float f1 = this.interpolateRotation(entity.prevRotationYawHead, entity.rotationYawHead, partialTicks);
            float f2 = f1 - f;

            if (shouldSit && entity.getRidingEntity() instanceof EntityLivingBase) {
                EntityLivingBase entitylivingbase = (EntityLivingBase)entity.getRidingEntity();
                f = this.interpolateRotation(entitylivingbase.prevRenderYawOffset, entitylivingbase.renderYawOffset, partialTicks);
                f2 = f1 - f;
                float f3 = MathHelper.wrapDegrees(f2);

                if (f3 < -85.0F)
                {
                    f3 = -85.0F;
                }

                if (f3 >= 85.0F)
                {
                    f3 = 85.0F;
                }

                f = f1 - f3;

                if (f3 * f3 > 2500.0F)
                {
                    f += f3 * 0.2F;
                }

                f2 = f1 - f;
            }

            float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
            this.renderLivingAt(entity, x, y, z);
            float f8 = this.handleRotationFloat(entity, partialTicks);
            this.applyRotations(entity, f8, f, partialTicks);
            float f4 = this.prepareScale(entity, partialTicks);
            float f5 = 0.0F;
            float f6 = 0.0F;

            if (!entity.isRiding()) {
                f5 = entity.prevLimbSwingAmount + (entity.limbSwingAmount - entity.prevLimbSwingAmount) * partialTicks;
                f6 = entity.limbSwing - entity.limbSwingAmount * (1.0F - partialTicks);

                if (entity.isChild())
                {
                    f6 *= 3.0F;
                }

                if (f5 > 1.0F)
                {
                    f5 = 1.0F;
                }
                f2 = f1 - f; // Forge: Fix MC-1207
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entity, f6, f5, partialTicks);
            this.mainModel.setRotationAngles(f6, f5, f8, f2, f7, f4, entity);

            if (this.renderOutlines) {
                boolean flag1 = this.setScoreTeamColor(entity);
                GlStateManager.enableColorMaterial();
                GlStateManager.enableOutlineMode(this.getTeamColor(entity));

                if (!this.renderMarker)
                {
                    this.renderModel(entity, f6, f5, f8, f2, f7, f4);
                }

                if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())
                {
                    this.renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);
                }

                GlStateManager.disableOutlineMode();
                GlStateManager.disableColorMaterial();

                if (flag1)
                {
                    this.unsetScoreTeamColor();
                }
            }

            else {
                if (ModuleManager.getModule(PlayerChams.class).isEnabled()) {
                    if (entity instanceof EntityPlayer) {
                        if (entity instanceof EntityOtherPlayerMP && entity.getName() == "人" && PlayerChams.pops.getValue()) {
                            float
                                    r = PlayerChams.popColor.getValue().getRed() / 255f,
                                    g = PlayerChams.popColor.getValue().getGreen() / 255f,
                                    b = PlayerChams.popColor.getValue().getBlue() / 255f,
                                    a = PlayerChams.getAlpha(entity) / 255f,
                                    ro = PlayerChams.popColorO.getValue().getRed() / 255f,
                                    go = PlayerChams.popColorO.getValue().getGreen() / 255f,
                                    bo = PlayerChams.popColorO.getValue().getBlue() / 255f;

                            if (PlayerChams.popMode.getValue() == PlayerChams.ChamModePop.Fill ||
                                    PlayerChams.popMode.getValue() == PlayerChams.ChamModePop.Pretty) {
                                GlStateManager.pushMatrix();
                                GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                                GL11.glDisable(GL11.GL_DEPTH_TEST);
                                GL11.glDepthMask(false);

                                GL11.glColor4f(r, g, b, a);
                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_DEPTH_TEST);
                                GL11.glDepthMask(true);

                                GL11.glColor4f(r, g, b, a);
                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                                GL11.glEnable(GL11.GL_LIGHTING);
                                GlStateManager.popAttrib();
                                GlStateManager.popMatrix();
                            }

                            if (PlayerChams.popMode.getValue() == PlayerChams.ChamModePop.Wireframe ||
                                    PlayerChams.popMode.getValue() == PlayerChams.ChamModePop.Pretty) {
                                GlStateManager.pushMatrix();
                                GlStateManager.rotate(entity.rotationYaw, 0.0F, 1.0F, 0.0F);
                                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glDisable(GL11.GL_DEPTH_TEST);
                                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                                GL11.glColor4f(ro, go, bo, a);
                                GL11.glLineWidth(PlayerChams.popOutlineWidth.getValue());
                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                                GL11.glEnable(2896);
                                GlStateManager.popAttrib();
                                GlStateManager.popMatrix();
                            }
                        }

                        else if (PlayerChams.players.getValue()) {
                            float
                                    r = PlayerChams.playerColor.getValue().getRed() / 255f,
                                    g = PlayerChams.playerColor.getValue().getGreen() / 255f,
                                    b = PlayerChams.playerColor.getValue().getBlue() / 255f,
                                    a = PlayerChams.playerColor.getValue().getAlpha() / 255f,
                                    ro = PlayerChams.playerColorO.getValue().getRed() / 255f,
                                    go = PlayerChams.playerColorO.getValue().getGreen() / 255f,
                                    bo = PlayerChams.playerColorO.getValue().getBlue() / 255f,
                                    ao = PlayerChams.playerColorO.getValue().getAlpha() / 255f;

                            if (PlayerChams.playerModel.getValue()) {
                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                            }

                            if (PlayerChams.playerMode.getValue() == PlayerChams.ChamMode.Fill ||
                                    PlayerChams.playerMode.getValue() == PlayerChams.ChamMode.Pretty) {
                                GlStateManager.pushMatrix();
                                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                                if (PlayerChams.middleWall.getValue()) {
                                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                                    GL11.glDepthMask(false);
                                }

                                GL11.glColor4f(r, g, b, a);

                                ResourceLocation resourcelocation = new ResourceLocation("textures/fate/skins/player_cham.png");
                                this.bindTexture(resourcelocation);

                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_DEPTH_TEST);
                                GL11.glDepthMask(true);

                                GL11.glColor4f(r, g, b, a);
                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                                GL11.glEnable(GL11.GL_LIGHTING);
                                GlStateManager.popAttrib();
                                GlStateManager.popMatrix();
                            }

                            if (PlayerChams.playerMode.getValue() == PlayerChams.ChamMode.Wireframe ||
                                    PlayerChams.playerMode.getValue() == PlayerChams.ChamMode.Pretty) {

                                GlStateManager.pushMatrix();
                                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                                if (PlayerChams.lineWall.getValue()) {
                                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                                }

                                GL11.glColor4f(ro, go, bo, ao);
                                GL11.glLineWidth(PlayerChams.playerOutlineWidth.getValue());
                                renderModel(entity, f6, f5, f8, f2, f7, f4);
                                GL11.glEnable(2896);
                                GlStateManager.popAttrib();
                                GlStateManager.popMatrix();
                            }

                            if (PlayerChams.playerMode.getValue() == PlayerChams.ChamMode.Normal){
                                GlStateManager.pushMatrix();
                                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_BLEND);
                                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                                GL11.glDisable(GL11.GL_DEPTH_TEST);
                                GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
                                GL11.glPolygonOffset(1.0f, -1000000.0f);
                                GL11.glDepthMask(false);

                                renderModel(entity, f6, f5, f8, f2, f7, f4);

                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glEnable(GL11.GL_DEPTH_TEST);
                                GL11.glDepthMask(true);

                                renderModel(entity, f6, f5, f8, f2, f7, f4);

                                GL11.glEnable(GL11.GL_LIGHTING);
                                GL11.glPolygonOffset(1.0f, 1000000.0f);
                                GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);

                                GlStateManager.popAttrib();
                                GlStateManager.popMatrix();
                            }
                        }

                        else {
                            renderModel(entity, f6, f5, f8, f2, f7, f4);
                        }
                    }

                    else {
                        renderModel(entity, f6, f5, f8, f2, f7, f4);
                    }

                }

                else {
                    renderModel(entity, f6, f5, f8, f2, f7, f4);
                }
            }

            boolean flag1 = setDoRenderBrightness(entity, partialTicks);
            if (flag1)
                unsetBrightness();
            GlStateManager.depthMask(true);
            if (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator())
                renderLayers(entity, f6, f5, partialTicks, f8, f2, f7, f4);

            GlStateManager.disableRescaleNormal();
        }
        catch (Exception exception)
        {
            LOGGER.error("Couldn't render entity", (Throwable)exception);
        }

        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T>(entity, RenderLivingBase.class.cast(this), partialTicks, x, y, z));
    }

    @Shadow
    protected abstract boolean isVisible(EntityLivingBase paramEntityLivingBase);

    @Shadow
    protected abstract float getSwingProgress(T paramT, float paramFloat);

    @Shadow
    protected abstract float interpolateRotation(float paramFloat1, float paramFloat2, float paramFloat3);

    @Shadow
    protected abstract float handleRotationFloat(T paramT, float paramFloat);

    @Shadow
    protected abstract void applyRotations(T paramT, float paramFloat1, float paramFloat2, float paramFloat3);

    @Shadow
    public abstract float prepareScale(T paramT, float paramFloat);

    @Shadow
    protected abstract void unsetScoreTeamColor();

    @Shadow
    protected abstract boolean setScoreTeamColor(T paramT);

    @Shadow
    protected abstract void renderLivingAt(T paramT, double paramDouble1, double paramDouble2, double paramDouble3);

    @Shadow
    protected abstract void unsetBrightness();

    @Shadow
    protected abstract void renderModel(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

    @Shadow
    protected abstract void renderLayers(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7);

    @Shadow
    protected abstract boolean setDoRenderBrightness(T paramT, float paramFloat);

    @Overwrite
    protected boolean canRenderName(T entity)
    {
        if (entity.getName() == "人") {
            this.unsetBrightness();
            return false;
        }

        EntityPlayerSP entityplayersp = Minecraft.getMinecraft().player;
        boolean flag = !entity.isInvisibleToPlayer(entityplayersp);

        if (entity != entityplayersp)
        {
            Team team = entity.getTeam();
            Team team1 = entityplayersp.getTeam();

            if (team != null)
            {
                Team.EnumVisible team$enumvisible = team.getNameTagVisibility();

                switch (team$enumvisible)
                {
                    case ALWAYS:
                        return flag;
                    case NEVER:
                        return false;
                    case HIDE_FOR_OTHER_TEAMS:
                        return team1 == null ? flag : team.isSameTeam(team1) && (team.getSeeFriendlyInvisiblesEnabled() || flag);
                    case HIDE_FOR_OWN_TEAM:
                        return team1 == null ? flag : !team.isSameTeam(team1) && flag;
                    default:
                        return true;
                }
            }
        }

        return Minecraft.isGuiEnabled() && entity != this.renderManager.renderViewEntity && flag && !entity.isBeingRidden();
    }
}
