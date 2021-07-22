package me.ninethousand.fate.api.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

public class Command {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void sendClientMessageDefault(String message) {
        mc.player.sendMessage(new TextComponentString(ChatFormatting.DARK_PURPLE + "[" + ChatFormatting.LIGHT_PURPLE + "Fate" + ChatFormatting.DARK_PURPLE + "]" + ChatFormatting.WHITE + message));

    }

    public static void sendClientMessageLine(String message) {
        final ITextComponent itc = new TextComponentString(ChatFormatting.DARK_PURPLE + "[" + ChatFormatting.LIGHT_PURPLE + "Fate" + ChatFormatting.DARK_PURPLE + "]" + ChatFormatting.WHITE + message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("fate.cc"))));
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
    }
}
