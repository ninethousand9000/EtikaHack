package me.ninethousand.etikahack.impl.modules.player;

import me.ninethousand.etikahack.api.event.events.PacketEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.network.play.client.CPacketCloseWindow;

@ModuleAnnotation(category = ModuleCategory.PLAYER)
public class XCarry extends Module {
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketCloseWindow && mc.currentScreen instanceof GuiInventory) {
            event.setCanceled(true);
        }
    }
}
