package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.game.BlockUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil3d;
import me.ninethousand.etikahack.api.util.world.hole.Hole;
import me.ninethousand.etikahack.api.util.world.hole.HoleUtil;
import net.minecraft.util.math.BlockPos;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class HoleESP extends Module {
    public static final Setting<Boolean> doubles = new Setting<>("Double", true);
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 6, 15, 1);
    public static final Setting<Boolean> render = new Setting<>("Render", true);

    public static final Setting<HoleRenderMode> renderMode = new Setting<>(render, "Mode", HoleRenderMode.Gradient);
    public static final NumberSetting<Float> renderHeight = new NumberSetting<>(renderMode,"Height", 0f, 1f, 5f, 2);
    public static final Setting<Boolean> useOutline = new Setting<>(renderMode, "Outline", true);
    public static final NumberSetting<Float> outlineWidth = new NumberSetting<>(useOutline,"OutlineWidth", 0f, 1f, 5f, 2);
    public static final Setting<Boolean> useFill = new Setting<>(renderMode, "Fill", true);

    public static final NumberSetting<Integer> updateTicks = new NumberSetting<>(render,"UpdateRate", 0, 10, 20, 1);

    public static final Setting<Boolean> bedrockRender = new Setting<>(render, "Bedrock", true);
    public static final Setting<Color> bedrockColor = new Setting<>(bedrockRender,"BedrockColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> bedrockColorO = new Setting<>(bedrockRender,"BedrockColorO", new Color(0xB3FFFFFF, true));

    public static final Setting<Boolean> obsidianRender = new Setting<>(render, "Obsidian", true);
    public static final Setting<Color> obsidianColor = new Setting<>(obsidianRender,"ObsidianColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> obsidianColorO = new Setting<>(obsidianRender,"ObsidianColorO", new Color(0xB3FFFFFF, true));

    public static final Setting<Boolean> doublesRender = new Setting<>(render, "Doubles", true);
    public static final Setting<Color> doublesColor = new Setting<>(doublesRender,"DoublesColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> doublesColorO = new Setting<>(doublesRender,"DoublesColorO", new Color(0xB3FFFFFF, true));

    int currentTick;
    boolean clearRender = false;

    public HoleESP() {
        addSettings(
                doubles, range, render
        );
    }

    @Override
    public void onWorldRender(WorldRenderEvent event3d) {
        if (nullCheck())
            return;

        currentTick++;

        if (currentTick < updateTicks.getValue()) return;

        if (clearRender) return;

        for (BlockPos potentialHole : BlockUtil.getNearbyBlocks(mc.player, range.getValue(), false)) {
            if (HoleUtil.isBedRockHole(potentialHole))
                renderHole(Hole.Type.Bedrock, potentialHole, Hole.Facing.None);
            else if (HoleUtil.isObsidianHole(potentialHole))
                renderHole(Hole.Type.Obsidian, potentialHole, Hole.Facing.None);

            if (doubles.getValue()) {
                if (HoleUtil.isDoubleObsidianHoleX(potentialHole.west()))
                    renderHole(Hole.Type.Double, potentialHole, Hole.Facing.East);
                else if (HoleUtil.isDoubleObsidianHoleZ(potentialHole.north()))
                    renderHole(Hole.Type.Double, potentialHole, Hole.Facing.South);
                else if (HoleUtil.isDoubleBedrockHoleX(potentialHole.west()))
                    renderHole(Hole.Type.Double, potentialHole, Hole.Facing.West);
                else if (HoleUtil.isDoubleBedrockHoleZ(potentialHole.north()))
                    renderHole(Hole.Type.Double, potentialHole, Hole.Facing.North);
            }
        }
    }

    public void renderHole( Hole.Type type, BlockPos pos, Hole.Facing facing) {
        GraphicsUtil3d.renderHoleESP(new Hole(type, facing, pos));
    }

    public enum HoleRenderMode {
        Normal,
        Gradient
    }
}
