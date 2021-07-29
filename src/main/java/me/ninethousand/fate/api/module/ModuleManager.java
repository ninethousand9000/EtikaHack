package me.ninethousand.fate.api.module;

import me.ninethousand.fate.impl.modules.client.ClickGUI;
import me.ninethousand.fate.impl.modules.client.ClientColor;
import me.ninethousand.fate.impl.modules.client.ClientFont;
import me.ninethousand.fate.impl.modules.hud.Coords;
import me.ninethousand.fate.impl.modules.hud.TargetHud;
import me.ninethousand.fate.impl.modules.hud.Watermark;
import me.ninethousand.fate.impl.modules.hud.Welcomer;
import me.ninethousand.fate.impl.modules.misc.FakePlayer;
import me.ninethousand.fate.impl.modules.movement.Strafe;
import me.ninethousand.fate.impl.modules.visual.Chams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class ModuleManager {
    protected static final ArrayList<Module> modules = new ArrayList<>();

    public static void init() {
        modules.addAll(Arrays.asList(
                //Client
                new ClickGUI(),
                new ClientFont(),
                new ClientColor(),
                //Movement
                new Strafe(),
                //Hud
                new Watermark(),
                new Coords(),
                new Welcomer(),
                new TargetHud(),
                //Misc
                new FakePlayer(),
                //Visual
                new Chams()
        ));

        modules.sort(ModuleManager::order);
    }

    private static int order(Module module1, Module module2) {
        return module1.getName().compareTo(module2.getName());
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public static ArrayList<Module> getModulesByCategory(ModuleCategory category) {
        ArrayList<Module> modules = new ArrayList<>();
        for (Module module : ModuleManager.modules) {
            if (module.getCategory() == category) modules.add(module);
        }
        return modules;
    }

    public static <T extends Module> Module getModule(Class<T> clazz) {
        for (Module module : modules) {
            if (module.getClass().isAssignableFrom(clazz)) return module;
        }

        throw new NoSuchElementException();
    }
}
