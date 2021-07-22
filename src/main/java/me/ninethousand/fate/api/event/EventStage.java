package me.ninethousand.fate.api.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventStage extends Event {
    private EventType stage;

    public EventStage() {
    }

    public EventStage(EventType stage) {
        this.stage = stage;
    }

    public EventType getStage() {
        return this.stage;
    }

    public void setStage(EventType stage) {
        this.stage = stage;
    }
}
