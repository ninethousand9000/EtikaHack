package me.ninethousand.fate.api.util.render.gl;

import net.minecraft.client.renderer.GlStateManager;

public class GlStateHelper extends GlStateManager {
    public static void scale(float scale) {
        scale(scale, scale, scale);
    }
}
