package me.ninethousand.fate.api.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.settings.Bind;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.settings.SettingBuilder;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final Minecraft mc = Minecraft.getMinecraft();

    public final List<Setting<?>> settings = new ArrayList<>();

    protected final String name = this.getClass().getSimpleName();
    protected ModuleCategory category = getAnnotation().category();

    protected final boolean enabledByDefault = getAnnotation().enabledByDefault();
    protected final boolean alwaysEnabled = getAnnotation().alwaysEnabled();

    protected boolean enabled = alwaysEnabled || enabledByDefault;
    protected boolean opened = false;
    protected boolean binding = false;

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public final Setting<Bind> bind = new SettingBuilder<>(new Bind(getAnnotation().bind()))
            .id("Bind")
            .callback(value -> {
                if (value.getKey() == Keyboard.KEY_DELETE || value.getKey() == Keyboard.KEY_ESCAPE || value.getKey() == Keyboard.KEY_BACK) value.setKey(0);

                return true;
            }).construct();

    public final Setting<Boolean> drawn = new SettingBuilder<>(true)
            .id("Drawn")
            .construct();

    public Module() {

    }

    private ModuleAnnotation getAnnotation() {
        if (getClass().isAnnotationPresent(ModuleAnnotation.class)) return getClass().getAnnotation(ModuleAnnotation.class);
        throw new IllegalStateException("ModuleAnnotation not found");
    }

    public void enable() {
        enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
        Command.sendClientMessageDefault(ChatFormatting.DARK_PURPLE + getName() + ChatFormatting.LIGHT_PURPLE + " was" + ChatFormatting.GREEN + " enabled.");
        onEnable();
    }

    public void disable() {
        if (!alwaysEnabled) {
            enabled = false;
            MinecraftForge.EVENT_BUS.unregister(this);
            Command.sendClientMessageDefault(ChatFormatting.DARK_PURPLE + getName() + ChatFormatting.LIGHT_PURPLE + " was" + ChatFormatting.RED + " disabled.");
            onDisable();
        }
    }

    public void toggle() {
        if (!alwaysEnabled) {
            setEnabled(!enabled);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        if (enabled) enable();
        else disable();
    }

    public String getName() {
        return name;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isBinding() {
        return binding;
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    public void onEnable() {}

    public void onDisable() {}
}
