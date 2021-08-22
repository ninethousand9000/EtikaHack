package me.ninethousand.etikahack;

import me.ninethousand.etikahack.api.command.CommandManager;
import me.ninethousand.etikahack.api.config.Config;
import me.ninethousand.etikahack.api.event.EventTracker;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.user.Session;
import me.ninethousand.etikahack.api.util.misc.DiscordUtil;
import me.ninethousand.etikahack.api.util.misc.WingsUtil;
import me.ninethousand.etikahack.api.util.render.IconUtil;
import me.ninethousand.etikahack.api.util.render.render.RenderWings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.IOException;

@Mod(modid = EtikaHack.MODID, name = EtikaHack.NAME, version = EtikaHack.VERSION)
public class EtikaHack {
    public static final String MODID = "etikahack";
    public static final String NAME = "EtikaHack";
    public static final String VERSION = "2.0-Pre"; // sexy
    public static final String BUILDNO = "1"; // pogger

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final EventTracker EVENT_TRACKER = new EventTracker();

    public static Session gameSession;

    public static final Minecraft mc = Minecraft.getMinecraft();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle("EtikaHack v" + VERSION);
        IconUtil.setWindowIcon();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        log("Welcome To EtikaHack " + VERSION);
        log("Proudly developed by ninethousand");
        log("https://discord.gg/mRGn35t4XE");

        gameSession = new Session(Minecraft.getMinecraft().getSession().getUsername());
        log("EtikaHack Session Loaded");

        EVENT_TRACKER.init();
        log("Events Loaded");

        ModuleManager.init();
        log("Modules Loaded");

        CommandManager.init();
        log("Commands Loaded");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Config.saveConfig();
            log("Config Saved");

            try {
                gameSession.declareSessionEnd();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        Config.loadConfig();
        log("Config Loaded");

        WingsUtil.init();
        MinecraftForge.EVENT_BUS.register(new RenderWings());
        log("Wings Loaded");

        DiscordUtil.start();
    }

    public static void log(String message) {
        LOGGER.info(message);
    }
}
