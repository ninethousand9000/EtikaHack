package me.ninethousand.fate.api.ui.newgui;

import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.ui.click.screen.ClickWindow;
import me.ninethousand.fate.api.ui.newgui.components.panel.PanelComponent;
import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.impl.modules.client.ClickGUI;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;

import java.io.IOException;
import java.util.ArrayList;

public final class ClickGUIScreen extends GuiScreen {
    public final ArrayList<PanelComponent> panels = new ArrayList<>();
    public final int startX = 30, startY = 20, gap = 10, width = 110, height = 18;
    public static float rotation = 0;

    @Override
    public void initGui() {
        int drawX = startX;
        int drawY = startY;

        for (ModuleCategory category : ModuleCategory.values()) {
            panels.add(new PanelComponent(category, drawX, drawY, width, height));
            drawX += (gap + width);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        panels.forEach(panelComponent -> {
            panelComponent.drawComponent(mouseX, mouseY);
        });

        GuiUtil.updateMousePos(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            GuiUtil.updateLeftClick();
        }

        if (mouseButton == 1) {
            GuiUtil.updateRightClick();
        }

        panels.forEach(panelComponent -> panelComponent.onClicked(mouseX, mouseY));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (state == 0) {
            GuiUtil.updateMouseState();
        }
    }

    @Override
    public void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        GuiUtil.updateKeyState(keyCode);
    }

    @Override
    public void onGuiClosed() {

    }

    @Override
    public boolean doesGuiPauseGame() {
        return ClickGUI.pauseGame.getValue() == ClickGUI.PauseModes.Pause;
    }
}
