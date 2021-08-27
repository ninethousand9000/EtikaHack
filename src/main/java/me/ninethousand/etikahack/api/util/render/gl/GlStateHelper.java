package me.ninethousand.etikahack.api.util.render.gl;

import net.minecraft.client.renderer.GlStateManager;

public class GlStateHelper extends GlStateManager {
    public GlStateHelper() {}

    public static void scale(float scale) {
        scale(scale, scale, scale);
    }
}
