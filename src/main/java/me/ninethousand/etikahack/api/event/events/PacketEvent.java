package me.ninethousand.etikahack.api.event.events;

import me.ninethousand.etikahack.api.event.EventStage;
import net.minecraft.network.Packet;

public class PacketEvent extends EventStage {
    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public <T extends Packet<?>> T getPacket() {
        return (T) this.packet;
    }

    public static class Send extends PacketEvent {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }
}
