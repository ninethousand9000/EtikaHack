package me.ninethousand.etikahack.api.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

import java.util.ArrayList;

public abstract class Command {
    private String label, description;

    private static String prefix = "@";
    private static final Minecraft mc = Minecraft.getMinecraft();

    public Command(String label, String description) {
        this.label = label;
        this.description = description;
    }

    public abstract void runCommand(ArrayList<String> args);

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        Command.prefix = prefix;
    }

    public static void sendClientMessageDefault(String message) {
        mc.player.sendMessage(new TextComponentString(getMessage(message)));
    }

    public static void sendClientMessageLine(String message) {
        final ITextComponent itc = new TextComponentString(getMessage(message)).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("fate.cc"))));
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
    }

    private static String getMessage(String textIn) {
        String textOut = "";

        if (Customise.useClientPrefix.getValue()) {
            textOut += Customise.clientBracketColor.getValue().getFormatting() + "[" + Customise.clientnameColor.getValue().getFormatting() + Customise.clientName.getValue() + Customise.clientBracketColor.getValue().getFormatting() + "] " + ChatFormatting.RESET;
        }

        textOut += textIn;

        return textOut;
    }

    private static String getClient() {
        return ChatFormatting.BLUE + "<" + Customise.clientName.getValue() + "> " + ChatFormatting.WHITE;
    }
}
