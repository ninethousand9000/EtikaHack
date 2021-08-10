package me.ninethousand.etikahack.api.util.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.ninethousand.etikahack.EtikaHack;
import me.ninethousand.etikahack.impl.modules.client.RPC;
import net.minecraft.client.Minecraft;

public class DiscordUtil {
    // 872175802962288640

    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    private static String details;
    private static String state;

    public static void start() {
        EtikaHack.log("Starting Rich Presence");

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        discordRPC.Discord_Initialize("872175802962288640", handlers, true, "");

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.largeImageKey = "etika";
        discordRichPresence.largeImageText = EtikaHack.VERSION ;

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
        EtikaHack.log("Stopping Rich Presence");

        discordRPC.Discord_Shutdown();
    }
}
