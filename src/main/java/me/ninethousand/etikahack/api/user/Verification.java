package me.ninethousand.etikahack.api.user;

import me.ninethousand.etikahack.EtikaHack;

import java.awt.*;
import java.io.IOException;

public class Verification {
    public static void declareUserStart(String user) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/876822433242419250/6aA7T-Px5bfp6GiBuX2UOzkoNbOk3M52O3sq6KAGOP1-LdgQOEZp0gzwpsOKCC3Lihem");
        webhook.setUsername("EtikaHack Verification");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.GREEN)
                .setDescription(user + " has started " + EtikaHack.NAME + " Version: " + EtikaHack.VERSION + " Serial: " + EtikaHack.BUILDNO));
        webhook.execute();
    }

    public static void declareUserQuit(String user) throws IOException {
        DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/876822433242419250/6aA7T-Px5bfp6GiBuX2UOzkoNbOk3M52O3sq6KAGOP1-LdgQOEZp0gzwpsOKCC3Lihem");
        webhook.setUsername("EtikaHack Verification");
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                .setColor(Color.RED)
                .setDescription(user + " has closed " + EtikaHack.NAME + " Version: " + EtikaHack.VERSION + " Serial: " + EtikaHack.BUILDNO));
        webhook.execute();
    }
}
