package me.ninethousand.etikahack.api.event.events;

import me.ninethousand.etikahack.api.event.EventStage;
import net.minecraft.client.gui.ScaledResolution;

public class HudRenderEvent extends EventStage {
    public float partialTicks;
    public ScaledResolution scaledResolution;

    public HudRenderEvent(float partialTicks, ScaledResolution scaledResolution) {
        this.partialTicks = partialTicks;
        this.scaledResolution = scaledResolution;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public void setScaledResolution(ScaledResolution scaledResolution) {
        this.scaledResolution = scaledResolution;
    }

    public double getScreenWidth() {
        return this.scaledResolution.getScaledWidth_double();
    }

    public double getScreenHeight() {
        return this.scaledResolution.getScaledHeight_double();
    }
}
