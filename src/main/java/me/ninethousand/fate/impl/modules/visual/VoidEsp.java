package me.ninethousand.fate.impl.modules.visual;

import me.ninethousand.fate.api.event.events.RenderEvent3d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil3d;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class VoidEsp extends Module {
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 6, 15, 1);
    public static final Setting<Color> color = new Setting<>("Color", new Color(0x2A93B3));
    public static final Setting<GraphicsUtil3d.RenderBoxMode> voidMode = new Setting<>("Void", GraphicsUtil3d.RenderBoxMode.OutlineFilled);
    public static final Setting<GraphicsUtil3d.RenderBoxMode> blockMode = new Setting<>("Block", GraphicsUtil3d.RenderBoxMode.OutlineFilled);

    public VoidEsp() {
        addSettings(
                range,
                color,
                voidMode,
                blockMode
        );
    }

    public final List<BlockPos> voidBlocks = new ArrayList<>();

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        voidBlocks.clear();

        final Vec3i player_pos = new Vec3i(mc.player.posX, mc.player.posY, mc.player.posZ);

        for (int x = player_pos.getX() - range.getValue(); x < player_pos.getX() + range.getValue(); x++) {
            for (int z = player_pos.getZ() - range.getValue(); z < player_pos.getZ() + range.getValue(); z++) {
                for (int y = player_pos.getY() + range.getValue(); y > player_pos.getY() - range.getValue(); y--) {
                    final BlockPos blockPos = new BlockPos(x, y, z);

                    if (isVoidHole(blockPos) == HoleType.Void || isVoidHole(blockPos) == HoleType.Block)
                        voidBlocks.add(blockPos);
                }
            }
        }
    }

    @Override
    public void onWorldRender(RenderEvent3d event3d) {
        new ArrayList<>(voidBlocks).forEach(pos -> {
            if (isVoidHole(pos) == HoleType.Void) GraphicsUtil3d.renderStandardBox(pos, color.getValue(), voidMode.getValue(), 0.0f, 1f);
            GraphicsUtil3d.renderStandardBox(pos, color.getValue(), blockMode.getValue(), 0.0f, 1f);
        });
    }

    private HoleType isVoidHole(BlockPos blockPos) {
        if (blockPos.getY() != 0) return HoleType.None;

        if (mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK) return HoleType.None;

        if (mc.world.getBlockState(blockPos).getBlock() == Blocks.AIR) return HoleType.Void;

        return HoleType.Block;
    }

    private enum HoleType {
        Void,
        Block,
        None
    }
}
