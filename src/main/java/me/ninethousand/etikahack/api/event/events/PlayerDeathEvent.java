package me.ninethousand.etikahack.api.event.events;

import me.ninethousand.etikahack.api.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerDeathEvent extends EventStage {
    private EntityPlayer player;

    public PlayerDeathEvent(EntityPlayer player) {
        this.player = player;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }
}
