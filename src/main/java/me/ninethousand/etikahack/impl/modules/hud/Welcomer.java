package me.ninethousand.etikahack.impl.modules.hud;

import me.ninethousand.etikahack.api.event.events.HudRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.math.TimeUtil;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.GlStateHelper;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class Welcomer extends Module {
    public static final Setting<WelcomeMode> welcomeMode = new Setting<>("Mode", WelcomeMode.Default);
    public static final Setting<String> customText = new Setting<>(welcomeMode, "Text","Hi, $player");
    public static final NumberSetting<Float> scale = new NumberSetting<>("Scale", 0.1f, 1.0f, 10.0f, 1);

    public Welcomer() {
        addSettings(welcomeMode, scale);
    }

    @Override
    public void onHudRender(HudRenderEvent event, VertexHelper vertexHelper) {
        if (nullCheck()) return;

        GlStateManager.pushMatrix();
        GlStateHelper.scale(scale.getValue());

        String text = "Hey, " + mc.player.getName();

        if (welcomeMode.getValue() == WelcomeMode.Time) {
            int hour = TimeUtil.getHour();

            if (hour >= 0 && hour < 12)
                text = "Morning, " + mc.player.getName();

            else if (hour >= 12 && hour < 17)
                text = "Afternoon, " + mc.player.getName();

            else if (hour >= 17 && hour < 19)
                text = "Evening, " + mc.player.getName();

            else if (hour >= 17 && hour < 19)
                text = "Good Night, " + mc.player.getName();

            else
                text = "Welcome, " + mc.player.getName();
        }

        else if (welcomeMode.getValue() == WelcomeMode.Custom) {
            text = customText.getValue().replace("$player", mc.getSession().getUsername());
        }

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        FontUtil.drawTextHUD(text, (scaledResolution.getScaledWidth() / 2 - FontUtil.getStringWidth(text) / 2) / scale.getValue(), 2 / scale.getValue());

        GlStateManager.popMatrix();
    }

    private enum WelcomeMode {
        Time,
        Custom,
        Default
    }
}
