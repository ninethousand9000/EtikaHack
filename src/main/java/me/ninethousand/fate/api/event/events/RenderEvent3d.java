package me.ninethousand.fate.api.event.events;

import me.ninethousand.fate.api.event.EventStage;

public class RenderEvent3d extends EventStage {
    private float partialTicks;

    public RenderEvent3d(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
