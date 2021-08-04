package me.ninethousand.fate.test;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.config.Config;
import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.impl.modules.misc.SettingTestModule;

import java.awt.*;
import java.io.IOException;

public class SettingTest {
    public static Setting<Boolean> mainSetting = new Setting<>("Main", true);
    public static Setting<ChatFormatting> sub1 = new Setting<>(mainSetting, "Sub1", ChatFormatting.BLUE);
    public static Setting<Boolean> subSub1 = new Setting<>(sub1, "SubSub1", true);
    public static Setting<Boolean> sub2 = new Setting<>(mainSetting, "Sub2", true);
    public static Setting<Boolean> sub3 = new Setting<>(mainSetting, "Sub3", true);


    public static void main(String[] args) throws IOException {
        configLoad();
        System.out.println("String1: " + SettingTestModule.string2.getValue());
    }

    public static void configSetup() {
        ModuleManager.init();

        Config.saveConfig();
    }

    public static void configLoad() throws IOException {
        ModuleManager.init();

        Config.loadModules();
    }
}
