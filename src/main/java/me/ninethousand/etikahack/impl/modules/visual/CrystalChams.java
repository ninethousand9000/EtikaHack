package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class CrystalChams extends Module {
    public static final Setting<Boolean> crystalModel = new Setting<>( "RenderModel", true);
    public static final Setting<Boolean> walls = new Setting<>( "Depth", true);
    public static final Setting<Boolean> middleWall = new Setting<>(walls, "ModelThroughWalls", true);
    public static final Setting<Boolean> lineWall = new Setting<>(walls, "LineThroughWalls", true);
    public static final Setting<ChamMode> crystalMode = new Setting<>( "CrystalMode", ChamMode.Fill);
    public static final Setting<Boolean> crystalColors = new Setting<>( "CrystalColors", true);
    public static final Setting<Color> crystalColor = new Setting<>(crystalColors,"CrystalColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> crystalColorO = new Setting<>(crystalColors,"CrystalColorO", new Color(0xB3FFFFFF, true));
    public static final NumberSetting<Float> crystalOutlineWidth = new NumberSetting<>("CrystalOutlineWidth", 0.1f, 1.0f, 5.0f, 1);

    public CrystalChams() {
        addSettings(
                crystalModel,
                walls,
                crystalMode,
                crystalColors,
                crystalOutlineWidth
        );
    }

    public enum ChamMode {
        Fill,
        Wireframe,
        Pretty,
        Normal
    }
}

