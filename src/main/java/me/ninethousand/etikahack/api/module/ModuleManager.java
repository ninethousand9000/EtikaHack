package me.ninethousand.etikahack.api.module;

import me.ninethousand.etikahack.impl.modules.client.*;
import me.ninethousand.etikahack.impl.modules.combat.BlockNuker;
import me.ninethousand.etikahack.impl.modules.combat.Offhand;
import me.ninethousand.etikahack.impl.modules.combat.SelfFill;
import me.ninethousand.etikahack.impl.modules.combat.SelfWeb;
import me.ninethousand.etikahack.impl.modules.hud.*;
import me.ninethousand.etikahack.impl.modules.misc.*;
import me.ninethousand.etikahack.impl.modules.movement.AntiVoid;
import me.ninethousand.etikahack.impl.modules.movement.Strafe;
import me.ninethousand.etikahack.impl.modules.player.*;
import me.ninethousand.etikahack.impl.modules.visual.*;

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
                new Customise(),
                new RPC(),
                new ClientConfig(),
                //
                //Combat
                new BlockNuker(),
                new SelfFill(),
                new Offhand(),
                new SelfWeb(),
                //Movement
                new Strafe(),
                new AntiVoid(),
                //Hud
                new Watermark(),
                new Coords(),
                new Welcomer(),
                new TargetHud(),
                new InfoList(),
                new Armor(),
                new ShulkerViewer(),
                //Misc
                new FakePlayer(),
                new TotemPop(),
                new VisualRange(),
                new AutoDuper(),
                new Media(),
                new DebugTools(),
                //Visual
                new PlayerChams(),
                new BoxEsp(),
                new SkyColor(),
                new Swing(),
                new EtikaMode(),
                new VoidEsp(),
                new CrystalChams(),
                new HoleESP(),
                new ViewModel(),
                new DragonWings(),
                new BlockOutline(),
                //Player
                new XCarry(),
                new EnderBackpack(),
                new AntiHunger(),
                new ClickPearl(),
                new NoRotate(),
                new FootXP(),
                new NoHitbox()
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

    public static Module getModulesByName(String name) {
        for (Module module : ModuleManager.modules) {
            if (module.getName().equalsIgnoreCase(name)) return module;
        }

        throw new NoSuchElementException();
    }

    public static <T extends Module> Module getModule(Class<T> clazz) {
        for (Module module : modules) {
            if (module.getClass().isAssignableFrom(clazz)) return module;
        }

        throw new NoSuchElementException();
    }
}
