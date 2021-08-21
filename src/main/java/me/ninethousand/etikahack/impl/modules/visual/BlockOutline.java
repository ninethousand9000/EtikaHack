package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil3d;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class BlockOutline extends Module {
    public static final Setting<GraphicsUtil3d.RenderBoxMode> mode = new Setting<>("RenderMode", GraphicsUtil3d.RenderBoxMode.Fancy);
    public static final Setting<Color> boxColor = new Setting<>("BoxColor", Color.white);
    public static final NumberSetting<Float> outlineWidth = new NumberSetting<>("OutlineWidth", 0.5f, 2.0f, 5.0f, 1);

    public BlockOutline() {
        addSettings(mode, boxColor, outlineWidth);
    }

    @Override
    public void onWorldRender(WorldRenderEvent worldRenderEvent) {
        if (nullCheck()) return;

        RayTraceResult result = mc.objectMouseOver;

        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();

            GlStateManager.disableAlpha();
            drawSelectionBox(mc.player, this.mc.objectMouseOver, 0, worldRenderEvent.getPartialTicks());
            GlStateManager.enableAlpha();
        }
    }

    public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks)
    {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            IBlockState iblockstate = mc.world.getBlockState(blockpos);

            if (iblockstate.getMaterial() != Material.AIR && mc.world.getWorldBorder().contains(blockpos))
            {
                double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)partialTicks;
                double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)partialTicks;
                double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)partialTicks;
                GraphicsUtil3d.drawBlockOutlineFromAABB(iblockstate.getSelectedBoundingBox(mc.world, blockpos).grow(0.0020000000949949026D).offset(-d3, -d4, -d5), boxColor.getValue(), outlineWidth.getValue());
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

//    public static void drawSelectionBoundingBox(AxisAlignedBB bb) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
//
//        bufferBuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
//        bufferBuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
//
//        drawBoundingBox(bb.minX, bb.minY, bb.minZ, bb.maxX, bb.maxY, bb.maxZ, boxColor.getValue().getRed() / 255f, boxColor.getValue().getGreen() / 255f, boxColor.getValue().getBlue() / 255f, boxColor.getValue().getAlpha() / 255f);
//    }
//
//    public static void drawBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
//    {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
//        drawBoundingBox(bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
//        tessellator.draw();
//    }
//
//    public static void drawBoundingBox(BufferBuilder bufferBuilder, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha)
//    {
//
//    }
}
