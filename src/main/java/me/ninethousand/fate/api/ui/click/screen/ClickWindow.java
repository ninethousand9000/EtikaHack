package me.ninethousand.fate.api.ui.click.screen;

import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.ui.click.theme.Theme;
import me.ninethousand.fate.api.ui.click.theme.ThemeManager;
import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.impl.modules.client.ClickGUI;
import me.ninethousand.fate.impl.ui.click.themes.DefaultTheme;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public final class ClickWindow {
    protected static final Minecraft mc = Minecraft.getMinecraft();

    private final String name;

    public int x;
    public int y;
    private int dragX;
    private int dragY;

    private boolean dragging = false;
    public boolean open = true;

    public ModuleCategory category;

    private final ArrayList<Module> modules;
    public static final ArrayList<ClickWindow> windows = new ArrayList<>();

    public Theme currentTheme;

    public ClickWindow(String name, int x, int y, ModuleCategory category) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.category = category;
        this.modules = ModuleManager.getModulesByCategory(category);
    }

    public static void initGui() {
        int xOffset = 12;

        for (ModuleCategory category : ModuleCategory.values()) {
            windows.add(new ClickWindow(category.toString(), xOffset, 20, category));
            xOffset += 110;
        }
    }

    public void drawGui(int mouseX, int mouseY) {
        currentTheme = new DefaultTheme();

        updateMousePos();
        scroll();
        collide();

        currentTheme.drawTitles(name, x, y, category);

        if (open) {
            y+=2;
            currentTheme.drawModules(modules, x, y, mouseX, mouseY);
            y-=2;
        }
    }

    private void updateMousePos() {
        if (dragging) {
            x = GuiUtil.mouseX - (dragX - x);
            y = GuiUtil.mouseY - (dragY - y);
        }

        dragX = GuiUtil.mouseX;
        dragY = GuiUtil.mouseY;
    }

    public void scroll() {
        int scrollWheel = Mouse.getDWheel();

        for (ClickWindow window : windows) {
            if (scrollWheel < 0) {
                window.setY(window.getY() - ClickGUI.scrollSpeed.getValue());

                continue;
            }

            if (scrollWheel <= 0) continue;

            window.setY(window.getY() + ClickGUI.scrollSpeed.getValue());
        }
    }

    public void collide() {
        if (!ClickGUI.windowOverflow.getValue()) {
            ScaledResolution scaledResolution = new ScaledResolution(mc);

            if (getX() <= 0) {
                setX(0);
            }

            if (getX() >= scaledResolution.getScaledWidth() - currentTheme.getWidth()) {
                setX(scaledResolution.getScaledWidth() - currentTheme.getWidth());
            }

            if (getY() <= 0) {
                setY(0);
            }

            if (getY() >= scaledResolution.getScaledHeight() - currentTheme.getHeight()) {
                setY(scaledResolution.getScaledHeight() - currentTheme.getHeight());
            }
        }
    }

    public void updateLeftClick() {
        if (GuiUtil.mouseOver(x, y, x + currentTheme.getWidth(), y + currentTheme.getHeight())) {
            dragging = true;
        }
    }

    public void updateRightClick() {
        if (GuiUtil.mouseOver(x, y, x + currentTheme.getWidth(), y + currentTheme.getHeight())) {
            open = !open;
        }
    }

    private String getTheme() {
        return "Default";
    }

    public void updateMouseState() {
        dragging = false;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
