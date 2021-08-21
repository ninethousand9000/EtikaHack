package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.social.FriendManager;
import me.ninethousand.etikahack.api.util.game.BlockUtil;
import me.ninethousand.etikahack.api.util.misc.ColorUtil;
import me.ninethousand.etikahack.mixin.accessors.IRenderManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class Skeleton extends Module {
    public static final Setting<Boolean> invisibles = new Setting<>("Invisibles", true);
    public static final Setting<Color> color = new Setting<>("Color", Color.WHITE);
    public static final Setting<Color> friendColor = new Setting<>("FriendColor", ColorUtil.friendColor);
    public static final NumberSetting<Float> lineWidth = new NumberSetting<>("LineWidth", 0.1f, 1.0f, 5.0f, 1);

    public Skeleton() {
        addSettings(invisibles, color, friendColor, lineWidth);
    }

    ICamera camera = new Frustum();
    static HashMap<EntityPlayer, float[][]> entities = new HashMap<>();

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if (nullCheck())
            return;

        mc.getRenderManager();
        if (mc.getRenderManager().options == null)
            return;

        startEnd(true);
        glEnable(GL_COLOR_MATERIAL);
        glDisable(GL_LINE_SMOOTH);
        entities.keySet().removeIf(this::doesNotContain);

        mc.world.playerEntities.forEach(skeletonEntity -> drawSkeleton(event, skeletonEntity));

        Gui.drawRect(0, 0, 0, 0, 0);
        startEnd(false);
    }
    void drawSkeleton(RenderWorldLastEvent event, EntityPlayer skeletonEntity) {
        camera.setPosition(mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double) event.getPartialTicks(), mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double) event.getPartialTicks(), mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double) event.getPartialTicks());

        float[][] skeletonPos = entities.get(skeletonEntity);
        if (skeletonPos != null && skeletonEntity.isEntityAlive() && camera.isBoundingBoxInFrustum(skeletonEntity.getEntityBoundingBox()) && !skeletonEntity.isDead && skeletonEntity != mc.player && !skeletonEntity.isPlayerSleeping()) {
            glPushMatrix();
            glEnable(GL_LINE_SMOOTH);
            glLineWidth((float) lineWidth.getValue());
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(getVec3(event, skeletonEntity).x - ((IRenderManager) mc.getRenderManager()).getRenderPosX(), getVec3(event, skeletonEntity).y - ((IRenderManager) mc.getRenderManager()).getRenderPosY(), getVec3(event, skeletonEntity).z - ((IRenderManager) mc.getRenderManager()).getRenderPosZ());
            glRotatef(-(skeletonEntity.prevRenderYawOffset + (skeletonEntity.renderYawOffset - skeletonEntity.prevRenderYawOffset) * event.getPartialTicks()), 0.0F, 1.0F, 0.0F);
            glTranslated(0.0D, 0.0D, skeletonEntity.isSneaking() ? -0.235D : 0.0D);
            glPushMatrix();
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(-0.125D, skeletonEntity.isSneaking() ? 0.6F : 0.75F, 0.0D);

            if (skeletonPos[3][0] != 0.0F)
                glRotatef(skeletonPos[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);

            if (skeletonPos[3][1] != 0.0F)
                glRotatef(skeletonPos[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);

            if (skeletonPos[3][2] != 0.0F)
                glRotatef(skeletonPos[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);

            glBegin(3);
            glVertex3d(0.0D, 0.0D, 0.0D);
            glVertex3d(0.0D, -(skeletonEntity.isSneaking() ? 0.6F : 0.75F), 0.0D);
            glEnd();
            glPopMatrix();
            glPushMatrix();
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(0.125D, skeletonEntity.isSneaking() ? 0.6F : 0.75F, 0.0D);

            if (skeletonPos[4][0] != 0.0F)
                glRotatef(skeletonPos[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);

            if (skeletonPos[4][1] != 0.0F)
                glRotatef(skeletonPos[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);

            if (skeletonPos[4][2] != 0.0F)
                glRotatef(skeletonPos[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);

            glBegin(3);
            glVertex3d(0.0D, 0.0D, 0.0D);
            glVertex3d(0.0D, -(skeletonEntity.isSneaking() ? 0.6F : 0.75F), 0.0D);
            glEnd();
            glPopMatrix();
            glTranslated(0.0D, 0.0D, skeletonEntity.isSneaking() ? 0.25D : 0.0D);
            glPushMatrix();
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(0.0D, skeletonEntity.isSneaking() ? -0.05D : 0.0D, skeletonEntity.isSneaking() ? -0.01725D : 0.0D);
            glPushMatrix();
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(-0.375D, (skeletonEntity.isSneaking() ? 0.6F : 0.75F) + 0.55D, 0.0D);

            if (skeletonPos[1][0] != 0.0F)
                glRotatef(skeletonPos[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);

            if (skeletonPos[1][1] != 0.0F)
                glRotatef(skeletonPos[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);

            if (skeletonPos[1][2] != 0.0F)
                glRotatef(-skeletonPos[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);

            glBegin(3);
            glVertex3d(0.0D, 0.0D, 0.0D);
            glVertex3d(0.0D, -0.5D, 0.0D);
            glEnd();
            glPopMatrix();
            glPushMatrix();
            glTranslated(0.375D, (skeletonEntity.isSneaking() ? 0.6F : 0.75F) + 0.55D, 0.0D);

            if (skeletonPos[2][0] != 0.0F)
                glRotatef(skeletonPos[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);

            if (skeletonPos[2][1] != 0.0F)
                glRotatef(skeletonPos[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);

            if (skeletonPos[2][2] != 0.0F)
                glRotatef(-skeletonPos[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);

            glBegin(3);
            glVertex3d(0.0D, 0.0D, 0.0D);
            glVertex3d(0.0D, -0.5D, 0.0D);
            glEnd();
            glPopMatrix();
            glRotatef((skeletonEntity.prevRenderYawOffset + (skeletonEntity.renderYawOffset - skeletonEntity.prevRenderYawOffset) * event.getPartialTicks()) - skeletonEntity.rotationYawHead, 0.0F, 1.0F, 0.0F);
            glPushMatrix();
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(0.0D, (skeletonEntity.isSneaking() ? 0.6F : 0.75F) + 0.55D, 0.0D);

            if (skeletonPos[0][0] != 0.0F)
                glRotatef(skeletonPos[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);

            glBegin(3);
            glVertex3d(0.0D, 0.0D, 0.0D);
            glVertex3d(0.0D, 0.3D, 0.0D);
            glEnd();
            glPopMatrix();
            glPopMatrix();
            glRotatef(skeletonEntity.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);
            glTranslated(0.0D, skeletonEntity.isSneaking() ? -0.16175D : 0.0D, skeletonEntity.isSneaking() ? -0.48025D : 0.0D);
            glPushMatrix();
            glTranslated(0.0D, skeletonEntity.isSneaking() ? 0.6F : 0.75F, 0.0D);
            glBegin(3);
            glVertex3d(-0.125D, 0.0D, 0.0D);
            glVertex3d(0.125D, 0.0D, 0.0D);
            glEnd();
            glPopMatrix();
            glPushMatrix();
            setColor(FriendManager.isFriend(skeletonEntity.getName()) ? friendColor.getValue() : color.getValue());
            glTranslated(0.0D, skeletonEntity.isSneaking() ? 0.6F : 0.75F, 0.0D);
            glBegin(3);
            glVertex3d(0.0D, 0.0D, 0.0D);
            glVertex3d(0.0D, 0.55D, 0.0D);
            glEnd();
            glPopMatrix();
            glPushMatrix();
            glTranslated(0.0D, (skeletonEntity.isSneaking() ? 0.6F : 0.75F) + 0.55D, 0.0D);
            glBegin(3);
            glVertex3d(-0.375D, 0.0D, 0.0D);
            glVertex3d(0.375D, 0.0D, 0.0D);
            glEnd();
            glPopMatrix();
            glPopMatrix();
        }
    }

    void startEnd(boolean revert) {
        if (revert) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            glEnable(GL_LINE_SMOOTH);
            GlStateManager.disableDepth();
            GlStateManager.disableTexture2D();
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
        }

        else {
            GlStateManager.disableBlend();
            GlStateManager.enableTexture2D();
            glDisable(GL_LINE_SMOOTH);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }

        GlStateManager.depthMask(!revert);
    }

    Vec3d getVec3(RenderWorldLastEvent event, EntityPlayer skeletonEntity) {
        double x = skeletonEntity.lastTickPosX + (skeletonEntity.posX - skeletonEntity.lastTickPosX) * event.getPartialTicks();
        double y = skeletonEntity.lastTickPosY + (skeletonEntity.posY - skeletonEntity.lastTickPosY) * event.getPartialTicks();
        double z = skeletonEntity.lastTickPosZ + (skeletonEntity.posZ - skeletonEntity.lastTickPosZ) * event.getPartialTicks();
        return new Vec3d(x, y, z);
    }

    public static void addEntity(EntityPlayer skeletonEntity, ModelPlayer model) {
        entities.put(skeletonEntity, new float[][] {{
                model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ }, { model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ }, { model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ }, { model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ
        }});
    }

    boolean doesNotContain(EntityPlayer player) {
        return !mc.world.playerEntities.contains(player);
    }

    public static void setColor(Color c) {
        glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
    }
}
