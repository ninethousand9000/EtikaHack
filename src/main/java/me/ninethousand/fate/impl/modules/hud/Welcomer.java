package me.ninethousand.fate.impl.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.event.events.RenderEvent2d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.math.TimeUtil;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.gl.GlStateHelper;
import me.ninethousand.fate.api.util.rotation.RotationUtil;
import me.ninethousand.fate.impl.modules.client.ClientColor;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

@ModuleAnnotation(category = ModuleCategory.Hud)
public class Welcomer extends Module {
    public static final Setting<Boolean> time = new Setting<>("Time", true);
    public static final NumberSetting<Float> scale = new NumberSetting<>("Scale", 0.1f, 1.0f, 10.0f, 1);

    public Welcomer() {
        addSettings(time, scale);
    }

    @Override
    public void onHudRender(RenderEvent2d event) {
        GlStateManager.pushMatrix();
        GlStateHelper.scale(scale.getValue());

        String text = "Hey, " + mc.player.getName();

        if (time.getValue()) {
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

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        FontUtil.drawText(text, (scaledResolution.getScaledWidth() / 2 - FontUtil.getStringWidth(text)) / scale.getValue(), 2 / scale.getValue(), ClientColor.clientColor.getValue().getRGB());

        GlStateManager.popMatrix();
    }
}
