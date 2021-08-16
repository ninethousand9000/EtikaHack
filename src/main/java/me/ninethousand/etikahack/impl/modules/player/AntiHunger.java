package me.ninethousand.etikahack.impl.modules.player;

import me.ninethousand.etikahack.api.event.events.PacketEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.mixin.accessors.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleAnnotation(category = ModuleCategory.PLAYER)
public class AntiHunger extends Module {
    public static final Setting<Boolean> cancelSprinting = new Setting<>("CancelSprint", false);

    public AntiHunger() {
        addSettings(cancelSprinting);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packet = event.getPacket();
            ((ICPacketPlayer)packet).setOnGround((mc.player.fallDistance >= 0.0F || mc.playerController.getIsHittingBlock()));
        }
        if (cancelSprinting.getValue().booleanValue() && event.getPacket() instanceof CPacketEntityAction) {
            CPacketEntityAction packet = event.getPacket();
            if (packet.getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)
                event.setCanceled(true);
        }
    }
}
