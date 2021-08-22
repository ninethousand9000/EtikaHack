package me.ninethousand.etikahack.api.user;

import me.ninethousand.etikahack.EtikaHack;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Session {
    private String accountName;
    private ArrayList<String> aliases = new ArrayList<>();

    public Session(String accountName) {
        this.accountName = accountName;
        this.openSession();
    }

    public void openSession() {
        try {
            declareSessionStart();
        }

        catch (Exception e) {
            EtikaHack.log("Failed To Start Session, Shutting down");
            Minecraft.getMinecraft().shutdown();
        }
    }

    public void closeSession(int code) {
        try {
            declareSessionEnd();
        }

        catch (Exception e) {
            EtikaHack.log("Failed To End Session, Shutting down");
            Minecraft.getMinecraft().shutdown();
        }
    }

    public void declareSessionStart() throws IOException {
        AuthWebhook auth = new AuthWebhook("https://discord.com/api/webhooks/879049261138993183/QmZ1Ed3bd38167v_viK-t2fVktf5QZKcNzE9ggUsyL2jWl1g8aTuOKsmkB1IPbxRluEW");
        auth.setAvatarUrl("https://www.pngitem.com/pimgs/m/136-1363102_etika-transparent-background-hd-png-download.png");
        auth.setUsername("EtikaBot");
        auth.addEmbed(new AuthWebhook.EmbedObject()
                .setDescription("Player " + this.getAccountName() + " has started " + EtikaHack.NAME + " v" + EtikaHack.VERSION)
                .setColor(Color.GREEN)
                .addField("PC", this.getSessionPC(), true)
                .setThumbnail("https://cravatar.eu/helmavatar/" + this.getAccountName() + "/190.png")
                .setAuthor(EtikaHack.NAME + " v" + EtikaHack.VERSION, "https://discord.gg/Ym39SSuueD", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSedBh4nskevtc_DDL9BgbZ2zjbJDUVCeJWEQ&usqp=CAU"));
        auth.execute(); //Handle exception
    }

    public void declareSessionEnd() throws IOException {
        AuthWebhook auth = new AuthWebhook("https://discord.com/api/webhooks/879049261138993183/QmZ1Ed3bd38167v_viK-t2fVktf5QZKcNzE9ggUsyL2jWl1g8aTuOKsmkB1IPbxRluEW");
        auth.setAvatarUrl("https://www.pngitem.com/pimgs/m/136-1363102_etika-transparent-background-hd-png-download.png");
        auth.setUsername("EtikaBot");
        auth.addEmbed(new AuthWebhook.EmbedObject()
                .setDescription("Player " + this.getAccountName() + " has started " + EtikaHack.NAME + " v" + EtikaHack.VERSION)
                .setColor(Color.RED)
                .addField("PC", this.getSessionPC(), true)
                .addField("Aliases", this.getAliases().toString(), false)
                .setThumbnail("https://cravatar.eu/helmavatar/" + this.getAccountName() + "/190.png")
                .setAuthor(EtikaHack.NAME + " v" + EtikaHack.VERSION, "https://discord.gg/Ym39SSuueD", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSedBh4nskevtc_DDL9BgbZ2zjbJDUVCeJWEQ&usqp=CAU"));
        auth.execute(); //Handle exception
    }

    public void updateSession() {
        if (!this.aliases.contains(Minecraft.getMinecraft().getSession().getUsername())) {
            this.aliases.add(Minecraft.getMinecraft().getSession().getUsername());
        }
    }

    public String getSessionPC() {
        return Hash.getHash(System.getenv("COMPUTERNAME"));
    }

    public String getAccountName() {
        return accountName;
    }

    public ArrayList<String> getAliases() {
        return aliases;
    }
}
