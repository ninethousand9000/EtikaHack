package me.ninethousand.etikahack.impl.modules.client;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.ui.newgui.ClickGUIScreen;
import me.ninethousand.etikahack.api.ui.newgui.GuiColors;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.CLIENT, bind = Keyboard.KEY_RCONTROL)
public class ClickGUI extends Module {
    public static final Setting<Color> accentColor = new Setting<>("AccentColor", new Color(0x2A93B3));
    public static final Setting<Color> backgroundColor = new Setting<>("BackGroundColor", new Color(0x131822));
    public static final Setting<Boolean> topAccent = new Setting<>("TopAccent", true);
    public static final Setting<BackgroundModes> backgroundMode = new Setting<>("Background", BackgroundModes.Blur);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", 0, 10, 20, 0);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", PauseModes.Continue);

    public ClickGUI() {
        addSettings(
                accentColor,
                backgroundColor,
                topAccent,
                backgroundMode,
                scrollSpeed,
                pauseGame
        );
    }

    private static ClickGUIScreen screen = null;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        if (screen == null) {
            screen = new ClickGUIScreen();
        }

        mc.displayGuiScreen(screen);
    }

    @Override
    public void onUpdate() {
        if (GuiColors.accent != accentColor.getValue()) GuiColors.accent = accentColor.getValue();
        if (GuiColors.normal != backgroundColor.getValue()) GuiColors.normal = backgroundColor.getValue();
    }

    public enum ThemeModes {
        Default
    }

    public enum BackgroundModes {
        Blur,
        Vanilla,
        None
    }

    public enum NameModes {
        Shrink,
        Stay
    }

    public enum IndicatorModes {
        Shrink,
        Stay,
        None
    }

    public enum PauseModes {
        Pause,
        Continue
    }
}
