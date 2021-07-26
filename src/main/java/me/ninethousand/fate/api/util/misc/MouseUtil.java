package me.ninethousand.fate.api.util.misc;

import me.ninethousand.fate.api.util.math.Vec2d;

public class MouseUtil {
    public static final boolean mouseHovering(final int posX, final int posY, final int width, final int height, final int mouseX, final int mouseY) {
        return mouseX > posX && mouseX < width && mouseY > posY && mouseY < height;
    }

    public static final boolean mouseHovering(final Vec2d start, final Vec2d end, final Vec2d mouse) {
        return mouse.x > start.x && mouse.x < end.x && mouse.y > start.y && mouse.y < end.y;
    }
}
