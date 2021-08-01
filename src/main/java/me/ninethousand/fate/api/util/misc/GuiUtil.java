package me.ninethousand.fate.api.util.misc;

import org.lwjgl.input.Keyboard;

public final class GuiUtil {
    public static int mouseX;
    public static int mouseY;
    public static int keyDown;

    public static boolean leftClicked;
    public static boolean leftDown;
    public static boolean rightClicked;

    public static boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseY >= minY && mouseX <= maxX && mouseY <= maxY;
    }

    public static void updateMousePos(int mouseXPos, int mouseYPos) {
        mouseX = mouseXPos;
        mouseY = mouseYPos;

        leftClicked = false;
        rightClicked = false;

        keyDown = Keyboard.KEY_NONE;
    }

    public static void updateLeftClick() {
        leftClicked = true;
        leftDown = true;
    }

    public static void updateRightClick() {
        rightClicked = true;
    }

    public static void updateMouseState() {
        leftDown = false;
    }

    public static void updateKeyState(int key) {
        keyDown = key;
    }
}
