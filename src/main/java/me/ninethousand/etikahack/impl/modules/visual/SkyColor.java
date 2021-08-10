package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class SkyColor extends Module {
    public static final Setting<Color> skyColor = new Setting<>("SkyColor", new Color(0x87CEEB));

    public SkyColor() {
        addSettings(skyColor);
    }

    @SubscribeEvent
    public void setFogColor(final EntityViewRenderEvent.FogColors event) {
        event.setRed(skyColor.getValue().getRed() / 255f);
        event.setGreen(skyColor.getValue().getGreen() / 255f);
        event.setBlue(skyColor.getValue().getBlue() / 255f);
    }

    @SubscribeEvent
    public void setFogDensity(final EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
}
