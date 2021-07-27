package me.ninethousand.fate.impl.modules.client;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.ui.click.screen.ClickScreen;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(category = ModuleCategory.Client, bind = Keyboard.KEY_RCONTROL)
public class ClickGUI extends Module {
    public static final Setting<ThemeModes> theme = new Setting<>("Theme", ThemeModes.Default);
    public static final Setting<BackgroundModes> backgroundMode = new Setting<>("Background", BackgroundModes.Blur);
    public static final Setting<Boolean> windowOverflow = new Setting<>("Window Overflow", false);
    public static final NumberSetting<Integer> scrollSpeed = new NumberSetting<>("Scroll Speed", 0, 10, 20, 0);

    public static final Setting<NameModes> nameMode = new Setting<>("Names", NameModes.Shrink);
    public static final Setting<IndicatorModes> indicatorMode = new Setting<>("Indicators", IndicatorModes.Shrink);
    public static final Setting<PauseModes> pauseGame = new Setting<>("Pause Game", PauseModes.Continue);

    public static ClickScreen screen = null;

    public ClickGUI() {
        addSettings(
                theme,
                backgroundMode,
                windowOverflow,
                scrollSpeed,
                nameMode,
                indicatorMode,
                pauseGame
        );
    }

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        mc.displayGuiScreen(new ClickScreen());


        if (OpenGlHelper.shadersSupported) {
            try {
                if (backgroundMode.getValue() == BackgroundModes.Blur) {
                    mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
                }
            } catch (Exception ignored) {}
        }

        this.toggle();
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
