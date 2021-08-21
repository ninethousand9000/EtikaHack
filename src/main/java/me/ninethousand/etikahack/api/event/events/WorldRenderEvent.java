package me.ninethousand.etikahack.api.event.events;

import me.ninethousand.etikahack.api.event.EventStage;

public class WorldRenderEvent extends EventStage {
    private float partialTicks;

    public WorldRenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
