package me.ninethousand.fate.api.ui.click.theme;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleCategory;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public abstract class Theme {
    private final Minecraft mc = Minecraft.getMinecraft();

    private final String name = getAnnotation().name();

    private final int width = getAnnotation().width();
    private final int height = getAnnotation().height();

    private ThemeAnnotation getAnnotation() {
        if (getClass().isAnnotationPresent(ThemeAnnotation.class)) {
            return getClass().getAnnotation(ThemeAnnotation.class);
        }

        throw new IllegalStateException("'ThemeAnnotation' not found!");
    }

    public abstract void drawTitles(String name, int left, int top, ModuleCategory category);

    public abstract void drawModules(ArrayList<Module> modules, int left, int top, int mouseX, int mouseY);

    public String getName() {
        return this.name;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
