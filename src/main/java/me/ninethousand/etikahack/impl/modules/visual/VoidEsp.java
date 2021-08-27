package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.game.BlockUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class VoidEsp extends Module {
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 6, 15, 1);
    public static final NumberSetting<Integer> updateTicks = new NumberSetting<>("UpdateRate", 0, 10, 20, 1);
    public static final Setting<Color> color = new Setting<>("Color", new Color(0x2A93B3));
    public static final Setting<GraphicsUtil3d.RenderBoxMode> voidMode = new Setting<>("Void", GraphicsUtil3d.RenderBoxMode.Pretty);
    public static final Setting<GraphicsUtil3d.RenderBoxMode> blockMode = new Setting<>("Block", GraphicsUtil3d.RenderBoxMode.Pretty);

    public VoidEsp() {
        addSettings(
                range,
                color,
                voidMode,
                blockMode
        );
    }

    int currentTick;
    boolean clearRender = false;

    @Override
    public void onWorldRender(WorldRenderEvent event3d) {
        if (nullCheck())
            return;

        currentTick++;

        if (currentTick < updateTicks.getValue()) return;

        if (clearRender) return;

        for (BlockPos pos : BlockUtil.getNearbyBlocks(mc.player, range.getValue(), false)) {
            if (pos.getY() != 0) return;

            if (mc.world.getBlockState(pos).getBlock() == Blocks.AIR) {
                int height = 0;
                BlockPos temp = pos;

                for (int i = 0; i < 4; i++) {
                    if (mc.world.getBlockState(temp.up()).getBlock() == Blocks.AIR) {
                        height++;
                        temp = temp.up();
                    }
                }

                GraphicsUtil3d.renderStandardBox(pos, color.getValue(), voidMode.getValue(), 0, 1f);
            }

            else if (mc.world.getBlockState(pos).getBlock() != Blocks.BEDROCK) {
                GraphicsUtil3d.renderStandardBox(pos, color.getValue(), blockMode.getValue(), 0, 1f);
            }
        }
    }
}
