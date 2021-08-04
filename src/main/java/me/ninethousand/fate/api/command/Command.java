package me.ninethousand.fate.api.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.impl.modules.client.Customise;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

public abstract class Command {
    protected static String prefix = ",";
    protected static final Minecraft mc = Minecraft.getMinecraft();

    protected String alias;
    protected String[] commands;

    public Command(String alias) {
        this.alias = alias;
        this.commands = new String[]{""};
    }

    public Command(String alias, String[] commands) {
        this.alias = alias;
        this.commands = commands;
    }

    public abstract void doCommand(String[] commands);

    public static void sendClientMessageDefault(String message) {
        mc.player.sendMessage(new TextComponentString(getMessage(message)));
    }

    public static void sendClientMessageLine(String message) {
        if (mc.player == null || mc.world == null) return;

        final ITextComponent itc = new TextComponentString(getMessage(message)).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("fate.cc"))));
        mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
    }

    public static String getPrefix() {
        return prefix;
    }

    public static void setPrefix(String prefix) {
        Command.prefix = prefix;
    }

    public static String getMessage(String textIn) {
        String textOut = "";

        if (Customise.useClientPrefix.getValue()) {
            textOut += Customise.clientBracketColor.getValue().getFormatting() + "[" + Customise.clientnameColor.getValue().getFormatting() + Customise.clientName.getValue() + Customise.clientBracketColor.getValue().getFormatting() + "] " + ChatFormatting.RESET;
        }

        textOut += textIn;

        return textOut;
    }

    public static String getClient() {
        return ChatFormatting.BLUE + "<" + Customise.clientName.getValue() + "> " + ChatFormatting.WHITE;
    }

    public String getAlias() {
        return alias;
    }
}
