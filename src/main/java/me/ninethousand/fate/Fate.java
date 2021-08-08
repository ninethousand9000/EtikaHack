package me.ninethousand.fate;

import me.ninethousand.fate.api.command.CommandManager;
import me.ninethousand.fate.api.config.Config;
import me.ninethousand.fate.api.event.EventTracker;
import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.util.render.IconUtil;
import me.ninethousand.fate.impl.modules.client.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.util.Arrays;

@Mod(modid = Fate.MODID, name = Fate.NAME, version = Fate.VERSION)
public class Fate {
    public static final String MODID = "etikahack";
    public static final String NAME = "EtikaHack";
    public static final String VERSION = "1.2.1";

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
