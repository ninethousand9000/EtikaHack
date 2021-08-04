package me.ninethousand.fate.impl.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.event.events.PlayerDeathEvent;
import me.ninethousand.fate.api.event.events.TotemPopEvent;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.util.game.PlayerManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class TotemPop extends Module {
    @SubscribeEvent
    public void onTotemPop(TotemPopEvent event) {
        int pops = 0;

        if (PlayerManager.totemPops.containsKey(event.getPlayer())) {
            pops = PlayerManager.totemPops.get(event.getPlayer());
            PlayerManager.totemPops.remove(event.getPlayer());
        }

        pops++;

        PlayerManager.totemPops.put(event.getPlayer(), pops);

        Command.sendClientMessageDefault(ChatFormatting.GRAY + event.getName() + " has popped " + ChatFormatting.RED +  pops + ChatFormatting.GRAY + " times");
    }

    @SubscribeEvent
    public void onDeath(PlayerDeathEvent event) {
        if (PlayerManager.totemPops.containsKey(event.getPlayer())) {
            Command.sendClientMessageDefault(ChatFormatting.GRAY + event.getPlayer().getName() + " has popped " + ChatFormatting.RED +  PlayerManager.totemPops.get(event) + ChatFormatting.GRAY + " times and died");
            PlayerManager.totemPops.remove(event.getPlayer());
        }
    }
}
