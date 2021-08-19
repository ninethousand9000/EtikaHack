package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.RenderEvent3d;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class HoleESP extends Module {
    public static final Setting<Boolean> doubles = new Setting<>("Double", true);
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 6, 15, 1);
    public static final Setting<Boolean> render = new Setting<>("Render", true);

    public static final Setting<HoleRenderMode> renderMode = new Setting<>(render, "Mode", HoleRenderMode.Gradient);
    public static final NumberSetting<Float> renderHeight = new NumberSetting<>(renderMode,"Height", 0f, 1f, 5f, 2);
    public static final Setting<Boolean> useOutline = new Setting<>(renderMode, "Outline", true);
    public static final Setting<Boolean> useFill = new Setting<>(renderMode, "Fill", true);

    public static final NumberSetting<Integer> updateTicks = new NumberSetting<>(render,"UpdateRate", 0, 10, 20, 1);

    public static final Setting<Boolean> bedrockRender = new Setting<>(render, "Bedrock", true);
    public static final Setting<Color> bedrockColor = new Setting<>(bedrockRender,"BedrockColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> bedrockColorO = new Setting<>(bedrockRender,"BedrockColorO", new Color(0xB3FFFFFF, true));

    public static final Setting<Boolean> obsidianRender = new Setting<>(render, "Obsidian", true);
    public static final Setting<Color> obsidianColor = new Setting<>(obsidianRender,"ObsidianColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> obsidianColorO = new Setting<>(obsidianRender,"ObsidianColorO", new Color(0xB3FFFFFF, true));

    int currentTick;
    boolean clearRender = false;

    public HoleESP() {
        addSettings(
                doubles, range, render
        );
    }

    @Override
    public void onWorldRender(RenderEvent3d event3d) {
        if (nullCheck())
            return;

        currentTick++;

        if (currentTick < updateTicks.getValue())
            return;

        if (clearRender)
            return;
    }

    public enum HoleRenderMode {
        Normal,
        Gradient
    }
}
