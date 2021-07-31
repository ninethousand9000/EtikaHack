package me.ninethousand.fate.impl.modules.combat;

import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import net.minecraft.util.EnumHand;

public class AutoCrystal {
    public static final Setting<Boolean> place = new Setting<>("Place", true);
    public static final NumberSetting<Float> placeRange = new NumberSetting<>("PlaceRange", 0.0f, 5.0f, 6.0f, 1);
    public static final NumberSetting<Integer> placeDelay = new NumberSetting<>("PlaceDelay", 0, 1, 5, 1);
    public static final NumberSetting<Float> placeRangeWall = new NumberSetting<>("PlaceWallsRange", 0.0f, 3.0f, 6.0f, 1);
    public static final Setting<PlaceMode> placeMode = new Setting<>("PlaceMode", PlaceMode.Standard);

    public static final Setting<Boolean> destroy = new Setting<>("Break", true);
    public static final NumberSetting<Float> destroyRange = new NumberSetting<>("BreakRange", 0.0f, 5.0f, 6.0f, 1);
    public static final NumberSetting<Integer> destroyDelay = new NumberSetting<>("BreakDelay", 0, 1, 5, 1);
    public static final NumberSetting<Float> destroyRangeWall = new NumberSetting<>("BreakWallsRange", 0.0f, 3.0f, 6.0f, 1);

    public static final Setting<Boolean> predict = new Setting<>("Predict", true);
    public static final NumberSetting<Integer> predictTicks = new NumberSetting<>("PredictTicks", 0, 1, 5, 1);

    public static final NumberSetting<Integer> minDMG = new NumberSetting<>("MinPlaceDMG", 0, 5, 36, 1);
    public static final NumberSetting<Integer> maxSelfDMG = new NumberSetting<>("MaxSelfDMG", 0, 5, 36, 1);
    public static final Setting<Boolean> ignoreSelfDMG = new Setting<>("IgnoreSelfDMG", false);
    public static final Setting<Boolean> antiSuicide = new Setting<>("AntiSuicide", true);

    public static final Setting<Boolean> facePlace = new Setting<>("FacePlace", true);
    public static final NumberSetting<Integer> facePlaceHealth = new NumberSetting<>("FacePlaceHealth", 0, 10, 36, 1);

    public static final Setting<Boolean> armorBreak = new Setting<>("ArmorBreaker", true);
    public static final NumberSetting<Integer> armorBreakHealth = new NumberSetting<>("ArmorBreaker%", 0, 10, 100, 1);

    public static final Setting<RotateMode> rotate = new Setting<>("Rotate", RotateMode.None);
    public static final Setting<SwapMode> swap = new Setting<>("Rotate", SwapMode.Target);
    public static final Setting<Boolean> raytrace = new Setting<>("Raytrace", true);
    public static final Setting<EnumHand> swingHand = new Setting<>("SwingHand", EnumHand.MAIN_HAND);
    public static final Setting<Boolean> terrain = new Setting<>("IgnoreTerrain", true);

    public static final Setting<Boolean> debug = new Setting<>("Debug", true);

    public AutoCrystal() {

    }

    public enum PlaceMode {
        Standard,
        Protocol
    }

    public enum RotateMode {
        None,
        Full
    }

    public enum SwapMode {
        None,
        Silent,
        Target
    }
}
