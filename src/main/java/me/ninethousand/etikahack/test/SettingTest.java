package me.ninethousand.etikahack.test;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.etikahack.api.config.Config;
import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.settings.Setting;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class SettingTest {
    public static Setting<Boolean> mainSetting = new Setting<>("Main", true);
    public static Setting<ChatFormatting> sub1 = new Setting<>(mainSetting, "Sub1", ChatFormatting.BLUE);
    public static Setting<Boolean> subSub1 = new Setting<>(sub1, "SubSub1", true);
    public static Setting<Boolean> sub2 = new Setting<>(mainSetting, "Sub2", true);
    public static Setting<Boolean> sub3 = new Setting<>(mainSetting, "Sub3", true);


    public static void main(String[] args) throws IOException {
        System.out.println(Keyboard.getKeyIndex("P"));
    }

    public static void configSetup() throws IOException {
        ModuleManager.init();

        Config.createDirectory();

        Config.saveConfig();
    }

    public static void configLoad() throws IOException {
        ModuleManager.init();

        Config.loadConfig();
    }
}
