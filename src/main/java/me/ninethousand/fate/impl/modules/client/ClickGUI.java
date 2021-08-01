package me.ninethousand.fate.impl.modules.client;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.ui.newgui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(category = ModuleCategory.CLIENT, bind = Keyboard.KEY_RCONTROL)
public class ClickGUI extends Module {
    public static final Setting<Boolean> topAccent = new Setting<>("TopAccent", true);
    public static final Setting<BackgroundModes> backgroundMode = new Setting<>("Background", BackgroundModes.Blur);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", 0, 10, 20, 0);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", PauseModes.Continue);

    public ClickGUI() {
        addSettings(
                topAccent,
                backgroundMode,
                scrollSpeed,
                pauseGame
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        mc.displayGuiScreen(new ClickGUIScreen());
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
