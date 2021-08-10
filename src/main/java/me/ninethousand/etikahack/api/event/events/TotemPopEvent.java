package me.ninethousand.etikahack.api.event.events;

import me.ninethousand.etikahack.api.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class TotemPopEvent extends EventStage {
    private EntityPlayer player;

    public TotemPopEvent(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public String getName() {
        return player.getName();
    }

    public Vec3d getPos() {
        return player.getPositionVector();
    }
}
