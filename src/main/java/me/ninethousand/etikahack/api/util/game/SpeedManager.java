package me.ninethousand.etikahack.api.util.game;

import me.ninethousand.etikahack.api.event.EventType;
import me.ninethousand.etikahack.api.event.events.WalkingPlayerEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpeedManager {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private static double currentSpeed = 0.0;

    public static void update() {
        double distTraveledLastTickX = mc.player.posX - mc.player.prevPosX;
        double distTraveledLastTickZ = mc.player.posZ - mc.player.prevPosZ;
        currentSpeed = distTraveledLastTickX * distTraveledLastTickX + distTraveledLastTickZ * distTraveledLastTickZ;
    }

    public static double getCurrentSpeedKMH() {
        double kmh = (double) MathHelper.sqrt(currentSpeed) * 71.2729367892;
        return (double) Math.round(10.0 * kmh) / 10.0;
    }

    @SubscribeEvent
    public void onPlayerWalk(WalkingPlayerEvent event) {
        if (event.getStage() == EventType.Pre) {
            update();
        }
    }
}