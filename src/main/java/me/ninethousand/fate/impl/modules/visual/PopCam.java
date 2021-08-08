package me.ninethousand.fate.impl.modules.visual;

import me.ninethousand.fate.api.event.events.RenderEvent2d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class PopCam extends Module {
    private static final ResourceLocation VIGNETTE_TEXTURE = new ResourceLocation("textures/fate/cool/vignette.png");

    public static final Setting<Color> popColor = new Setting<>("PopColor", new Color(0xBCC62D));

    public PopCam() {
        addSettings(popColor);
    }

    @Override
    public void onHudRender(RenderEvent2d event, VertexHelper vertexHelper) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR);
        GlStateManager.color(popColor.getValue().getRed(), popColor.getValue().getGreen(), popColor.getValue().getBlue());

        GraphicsUtil2d.drawImage(VIGNETTE_TEXTURE, 0, 0, 0, 0, mc.displayWidth, mc.displayHeight, mc.displayWidth, mc.displayHeight);

        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
