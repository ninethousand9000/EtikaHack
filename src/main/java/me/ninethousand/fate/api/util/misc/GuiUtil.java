package me.ninethousand.fate.api.util.misc;

import org.lwjgl.input.Keyboard;

public final class GuiUtil {
    public static int mouseX;
    public static int mouseY;
    public static int keyDown;

    public static boolean leftDown;
    public static boolean leftHeld;
    public static boolean rightDown;

    public static boolean mouseOver(int minX, int minY, int maxX, int maxY) {
        return mouseX >= minX && mouseY >= minY && mouseX <= maxX && mouseY <= maxY;
    }

    public static void updateMousePos(int mouseXPos, int mouseYPos) {
        mouseX = mouseXPos;
        mouseY = mouseYPos;

        leftDown = false;
        rightDown = false;

        keyDown = Keyboard.KEY_NONE;
    }

    public static void updateLeftClick() {
        leftDown = true;
        leftHeld = true;
    }

    public static void updateRightClick() {
        rightDown = true;
    }

    public static void updateMouseState() {
        leftHeld = false;
    }

    public static void updateKeyState(int key) {
        keyDown = key;
    }
}
