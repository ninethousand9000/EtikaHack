package me.ninethousand.fate.api.event.events;

import me.ninethousand.fate.api.event.EventStage;
import me.ninethousand.fate.api.util.math.Vec2d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
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
