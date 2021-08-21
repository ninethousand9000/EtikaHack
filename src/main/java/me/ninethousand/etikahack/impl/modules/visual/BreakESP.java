package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.misc.ColorUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil3d;
import me.ninethousand.etikahack.api.util.render.render.RenderBuilder;
import me.ninethousand.etikahack.mixin.accessors.IPlayerControllerMP;
import me.ninethousand.etikahack.mixin.accessors.IRenderGlobal;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.awt.*;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class BreakESP extends Module {
    public static final Setting<BreakVisualMode> visualMode = new Setting<>("Mode", BreakVisualMode.Box);
    public static final Setting<BoxMode> boxMode = new Setting<>(visualMode, "BoxMode", BoxMode.Both);
    public static final Setting<Boolean> boxGradient = new Setting<>(boxMode, "BoxFadeGradient", true);
    public static final Setting<Color> boxColor1 = new Setting<>(boxMode, "BoxStart", new Color(0xD001FF));
    public static final Setting<Color> boxColor2 = new Setting<>(boxMode, "BoxEnd", new Color(0x03F7FF));
    public static final Setting<Color> boxColorO = new Setting<>(boxMode, "BoxColorO", new Color(0xFFFFFF));
    public static final NumberSetting<Float> boxOutlineWidth = new NumberSetting<>(boxMode, "OutlineWidthBox", 0.1f, 1f, 5f, 2);
    public static final Setting<BarMode> barMode = new Setting<>(visualMode, "BarGradient", BarMode.Gradient);
    public static final Setting<Boolean> barUseHighlight = new Setting<>(boxMode, "BlockHighlight", true);
    public static final Setting<Color> barColor1 = new Setting<>(barMode, "BarStart", new Color(0xD001FF));
    public static final Setting<Color> barColor2 = new Setting<>(barMode, "BarEnd", new Color(0x03F7FF));
    public static final Setting<Color> barColorO = new Setting<>(barMode, "BarColorO", new Color(0xFFFFFF));
    public static final Setting<Color> barBlockColor = new Setting<>(barMode, "BlockColor", new Color(0xD001FF));
    public static final NumberSetting<Float> barOutlineWidth = new NumberSetting<>(barMode, "OutlineWidthBar", 0.1f, 1f, 5f, 2);

    public static final Setting<Boolean> nametag = new Setting<>("NameTag", true);
    public static final Setting<Color> nametagColor = new Setting<>(nametag, "NameTagColor", new Color(0xFFFFFF));

    public BreakESP() {
        addSettings(visualMode, nametag);
    }

    @Override
    public void onWorldRender(WorldRenderEvent event3d) {
        Map<Integer, Integer> colorMapBox = ColorUtil.getInterpolatedValues(100, boxColor1.getValue(), boxColor2.getValue());
        Map<Integer, Integer> colorMapBar = ColorUtil.getInterpolatedValues(100, barColor1.getValue(), barColor2.getValue());

        ((IRenderGlobal) mc.renderGlobal).getDamagedBlocks().forEach((entityId, destroyBlockProgress) -> {
            float percentage = entityId == mc.player.getEntityId() ? ((IPlayerControllerMP) mc.playerController).getCurBlockDamageMP() : (destroyBlockProgress.getPartialBlockDamage() / 10F);

            BlockPos blockPos = destroyBlockProgress.getPosition();

            if (visualMode.getValue() == BreakVisualMode.Box) {
                AxisAlignedBB aabb = new AxisAlignedBB(
                        blockPos.getX() - mc.getRenderManager().viewerPosX,
                        blockPos.getY() - mc.getRenderManager().viewerPosY,
                        blockPos.getZ() - mc.getRenderManager().viewerPosZ,
                        blockPos.getX() + 1 - mc.getRenderManager().viewerPosX,
                        blockPos.getY() + 1 - mc.getRenderManager().viewerPosY,
                        blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ
                );

                double minX;
                double minY;
                double minZ;
                double maxX;
                double maxY;
                double maxZ;

                switch (boxMode.getValue()) {
                    case In:
                        minX = aabb.minX + percentage / 2;
                        minY = aabb.minY + percentage / 2;
                        minZ = aabb.minZ + percentage / 2;
                        maxX = aabb.maxX - percentage / 2;
                        maxY = aabb.maxY - percentage / 2;
                        maxZ = aabb.maxZ - percentage / 2;

                        break;

                    case Out:
                    default:
                        minX = aabb.minX + 0.5 - percentage / 2;
                        minY = aabb.minY + 0.5 - percentage / 2;
                        minZ = aabb.minZ + 0.5 - percentage / 2;
                        maxX = aabb.maxX - 0.5 + percentage / 2;
                        maxY = aabb.maxY - 0.5 + percentage / 2;
                        maxZ = aabb.maxZ - 0.5 + percentage / 2;

                        break;

                    case Both:
                        minX = aabb.minX + percentage;
                        minY = aabb.minY + percentage;
                        minZ = aabb.minZ + percentage;
                        maxX = aabb.maxX - percentage;
                        maxY = aabb.maxY - percentage;
                        maxZ = aabb.maxZ - percentage;
                }

                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder builder = tessellator.getBuffer();

                RenderBuilder.glSetup();
                builder.begin(GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION_COLOR);
                GraphicsUtil3d.addChainedFilledBoxVertices(builder, minX, minY, minZ, maxX, maxY, maxZ, !boxGradient.getValue() ? boxColor1.getValue() : new Color(colorMapBox.get((int) (percentage * 100))));
                tessellator.draw();
                RenderBuilder.glRelease();

                AxisAlignedBB alignedBB = new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);

                GraphicsUtil3d.drawBlockOutlineFromAABB(alignedBB, boxColorO.getValue(), boxOutlineWidth.getValue());

                if (nametag.getValue()) {
                    GraphicsUtil3d.drawNameTagOnBlock(destroyBlockProgress.getPosition(), 0.2f, (int) (percentage * 100) + "%", nametagColor.getValue());
                }
            }

            else {
                if (barUseHighlight.getValue()) {
                    AxisAlignedBB aabb = new AxisAlignedBB(
                            blockPos.getX() - mc.getRenderManager().viewerPosX,
                            blockPos.getY() - mc.getRenderManager().viewerPosY,
                            blockPos.getZ() - mc.getRenderManager().viewerPosZ,
                            blockPos.getX() + 1 - mc.getRenderManager().viewerPosX,
                            blockPos.getY() + 1 - mc.getRenderManager().viewerPosY,
                            blockPos.getZ() + 1 - mc.getRenderManager().viewerPosZ
                    );

                    GraphicsUtil3d.drawBlockOutline(aabb, barBlockColor.getValue(), barOutlineWidth.getValue());
                }

                GraphicsUtil3d.drawProgressBarOnBlock(destroyBlockProgress.getPosition(), 0.2f, (40 / 100f) * (percentage * 100), 10f, barColor1.getValue(), barMode.getValue() != BarMode.Gradient ? barColor1.getValue() : new Color(colorMapBar.get((int) (percentage * 100))), barColorO.getValue(), barOutlineWidth.getValue());
            }

            if (nametag.getValue()) {
                GraphicsUtil3d.drawNameTagOnBlock(destroyBlockProgress.getPosition(), 0.7f, mc.world.getEntityByID(entityId).getName(), nametagColor.getValue());
            }
        });
    }

    private enum BreakVisualMode {
        Box,
        Bar
    }

    private enum BoxMode {
        In,
        Out,
        Both
    }

    private enum BarMode {
        Gradient,
        Normal
    }
}
