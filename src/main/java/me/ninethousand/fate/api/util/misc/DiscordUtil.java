package me.ninethousand.fate.api.util.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.ninethousand.fate.Fate;
import me.ninethousand.fate.impl.modules.client.RPC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

import java.util.Objects;

public class DiscordUtil {
    // 872175802962288640

    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    private static String details;
    private static String state;

    public static void start() {
        Fate.log("Starting Rich Presence");

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        discordRPC.Discord_Initialize("872175802962288640", handlers, true, "");

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.largeImageKey = "etika";
        discordRichPresence.largeImageText = Fate.VERSION ;

        discordRPC.Discord_UpdatePresence(discordRichPresence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = "Balling";

                    if (Minecraft.getMinecraft().getCurrentServerData() != null) {
                        details = Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase();
                    }

                    state = RPC.message.getValue();


                    discordRichPresence.details = details;
                    discordRichPresence.state = state;

                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } try {
                    Thread.sleep(5000L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void stop() {
        Fate.log("Stopping Rich Presence");

        discordRPC.Discord_Shutdown();
    }
}
