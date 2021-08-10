package me.ninethousand.etikahack.impl.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.CLIENT, alwaysEnabled = true)
public class Customise extends Module {
    public static final Setting<String> clientName = new Setting<>("Name", "EtikaHack");
    public static final NumberSetting<Integer> rainbowSpeed = new NumberSetting<>("RainbowSpeed", 0, 10, 100, 1);

    public static final Setting<Boolean> hudHeader = new Setting<>("HUD", true);
    public static final Setting<Color> hudColor = new Setting<>(hudHeader, "HudColor", new Color(0x2A93B3));
    public static final Setting<Boolean> rolling = new Setting<>(hudHeader,"Rolling", false);
    public static final NumberSetting<Integer> factor = new NumberSetting<>(hudHeader,"Factor", 0, 10, 200, 1);
    public static final Setting<Boolean> arrayListTop = new Setting<>(hudHeader,"ArrayTop", true);

    public static final Setting<Boolean> clientMessageOptions = new Setting<>("ClientMessageOptions", true);
    public static final Setting<Boolean> useClientPrefix = new Setting<>(clientMessageOptions, "UseClientName", true);

    public static final Setting<Boolean> clientMessageColors = new Setting<>(clientMessageOptions, "ClientMessageColors", true);
    public static final Setting<ChatColors> clientBracketColor = new Setting<>(clientMessageColors, "BracketColor", ChatColors.BLUE);
    public static final Setting<ChatColors> clientnameColor = new Setting<>(clientMessageColors, "NameColor", ChatColors.BLUE);
    public static final Setting<ChatColors> moduleColor = new Setting<>(clientMessageColors, "ModuleColor", ChatColors.BLUE);

    public Customise() {
        addSettings(clientName, rainbowSpeed, hudHeader, clientMessageOptions);
    }

    public enum ChatColors {
        BLACK(ChatFormatting.BLACK),
        DARK_BLUE(ChatFormatting.DARK_BLUE),
        DARK_GREEN(ChatFormatting.DARK_GREEN),
        DARK_AQUA(ChatFormatting.DARK_AQUA),
        DARK_RED(ChatFormatting.DARK_RED),
        DARK_PURPLE(ChatFormatting.DARK_PURPLE),
        GOLD(ChatFormatting.GOLD),
        GRAY(ChatFormatting.GRAY),
        DARK_GRAY(ChatFormatting.DARK_GRAY),
        BLUE(ChatFormatting.BLUE),
        GREEN(ChatFormatting.GREEN),
        AQUA(ChatFormatting.AQUA),
        RED(ChatFormatting.RED),
        LIGHT_PURPLE(ChatFormatting.LIGHT_PURPLE),
        YELLOW(ChatFormatting.YELLOW),
        WHITE(ChatFormatting.WHITE),
        BOLD(ChatFormatting.BOLD),
        UNDERLINE(ChatFormatting.UNDERLINE),
        ITALIC(ChatFormatting.ITALIC),
        OBF(ChatFormatting.OBFUSCATED);

        private ChatFormatting formatting;

        ChatColors(ChatFormatting formatting) {
            this.formatting = formatting;
        }

        public ChatFormatting getFormatting() {
            return formatting;
        }
    }
}
