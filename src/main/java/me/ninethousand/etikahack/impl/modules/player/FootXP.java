package me.ninethousand.etikahack.impl.modules.player;

import me.ninethousand.etikahack.api.event.events.PacketEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.util.game.InventoryUtil;
import me.ninethousand.etikahack.api.util.rotation.RotationManager;
import me.ninethousand.etikahack.mixin.accessors.ICPacketPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

@ModuleAnnotation(category = ModuleCategory.PLAYER)
public class FootXP extends Module {
    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer && (mc.player.getHeldItemMainhand().getItem() == Items.EXPERIENCE_BOTTLE || InventoryUtil.getSpoofedItem() == Items.EXPERIENCE_BOTTLE) && Mouse.isButtonDown(1)) {
            RotationManager.resetRotations();
            CPacketPlayer cPacketPlayer = (CPacketPlayer) event.getPacket();
            ((ICPacketPlayer) cPacketPlayer).setPitch(180);
        }
    }
}
