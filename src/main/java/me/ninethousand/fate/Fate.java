package me.ninethousand.fate;

import me.ninethousand.fate.api.event.EventTracker;
import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.util.render.IconUtil;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid = Fate.MODID, name = Fate.NAME, version = Fate.VERSION)
public class Fate {
    public static final String MODID = "fate";
    public static final String NAME = "Fate";
    public static final String VERSION = "1.1";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final EventTracker EVENT_TRACKER = new EventTracker();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Display.setTitle("JingGod.cc " + VERSION);
        IconUtil.setWindowIcon();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        log("=============================");
        log("       Welcome To Fate       ");
        log("                             ");

        ModuleManager.init();

        log("     Modules Initialised     ");

        EVENT_TRACKER.init();

        log("     Events Initialised      ");

        log("=============================");


    }

    public static void log(String message) {
        LOGGER.info(message);
    }
}
