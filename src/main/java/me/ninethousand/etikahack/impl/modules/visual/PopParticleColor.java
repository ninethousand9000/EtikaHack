package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.event.events.RenderEvent2d;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class PopParticleColor extends Module {
    public static final Setting<Color> popColor = new Setting<>("PopColor", new Color(0xBCC62D));

    public PopParticleColor() {
        addSettings(popColor);
    }
}
