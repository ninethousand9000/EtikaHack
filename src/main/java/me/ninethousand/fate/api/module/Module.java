package me.ninethousand.fate.api.module;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.settings.Bind;
import me.ninethousand.fate.api.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module {
    protected final Minecraft mc = Minecraft.getMinecraft();

    public final List<Setting<?>> settings = new ArrayList<>();

    protected final String name = this.getClass().getSimpleName();
    protected ModuleCategory category = getAnnotation().category();
    protected int key = getAnnotation().bind();

    protected final boolean enabledByDefault = getAnnotation().enabledByDefault();
    protected final boolean alwaysEnabled = getAnnotation().alwaysEnabled();

    protected boolean enabled = alwaysEnabled || enabledByDefault;
    protected boolean opened = false;
    protected boolean binding = false;
    protected boolean drawn = true;

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public Module() {

    }

    private ModuleAnnotation getAnnotation() {
        if (getClass().isAnnotationPresent(ModuleAnnotation.class)) return getClass().getAnnotation(ModuleAnnotation.class);
        throw new IllegalStateException("ModuleAnnotation not found");
    }

    public void enable() {
        enabled = true;
        MinecraftForge.EVENT_BUS.register(this);
        Command.sendClientMessageLine(ChatFormatting.DARK_PURPLE + getName() + ChatFormatting.LIGHT_PURPLE + " was" + ChatFormatting.GREEN + " enabled.");
        onEnable();
    }

    public void disable() {
        if (!alwaysEnabled) {
            enabled = false;
            MinecraftForge.EVENT_BUS.unregister(this);
            Command.sendClientMessageLine(ChatFormatting.DARK_PURPLE + getName() + ChatFormatting.LIGHT_PURPLE + " was" + ChatFormatting.RED + " disabled.");
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

    public List<Setting<?>> getSettings() {
        return settings;
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void onEnable() {}

    public void onDisable() {}
}
