package me.ninethousand.etikahack;

import me.ninethousand.etikahack.api.command.CommandManager;
import me.ninethousand.etikahack.api.config.Config;
import me.ninethousand.etikahack.api.event.EventTracker;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.util.game.ServerManager;
import me.ninethousand.etikahack.api.util.render.IconUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.launcher.FMLTweaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = EtikaHack.MODID, name = EtikaHack.NAME, version = EtikaHack.VERSION)
public class EtikaHack {
    public static final String MODID = "etikahack";
    public static final String NAME = "EtikaHack";
    public static final String VERSION = "1.5"; // sexy

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final EventTracker EVENT_TRACKER = new EventTracker();

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

        EVENT_TRACKER.init();
        log("Events Loaded");

        ModuleManager.init();
        log("Modules Loaded");

        CommandManager.init();
        log("Commands Loaded");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Config.saveConfig();
            log("Config Saved");
        }));

        Config.loadConfig();
        log("Config Loaded");
    }

    public static void log(String message) {
        LOGGER.info(message);
    }
}
