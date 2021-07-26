package me.ninethousand.fate.api.ui.click.screen;

import me.ninethousand.fate.api.util.misc.GuiUtil;
import me.ninethousand.fate.impl.modules.client.ClickGUI;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;

import java.io.IOException;

public final class ClickScreen extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if (ClickGUI.backgroundMode.getValue() == ClickGUI.BackgroundModes.Vanilla) {
            this.drawDefaultBackground();
        }

        for (ClickWindow window : ClickWindow.windows) {
            window.drawGui(mouseX, mouseY);
        }

        GuiUtil.updateMousePos(mouseX, mouseY);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            for (ClickWindow window : ClickWindow.windows) {
                window.updateLeftClick();
            }

            GuiUtil.updateLeftClick();
        }

        if (mouseButton == 1) {
            for (ClickWindow window : ClickWindow.windows) {
                window.updateRightClick();
            }

            GuiUtil.updateRightClick();
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        if (state == 0) {
            for (ClickWindow window : ClickWindow.windows) {
                window.updateMouseState();
            }

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
        if (OpenGlHelper.shadersSupported) {
            try {
                mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return ClickGUI.pauseGame.getValue() == ClickGUI.PauseModes.Pause;
    }
}
