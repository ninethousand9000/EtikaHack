package me.ninethousand.etikahack.impl.modules.hud;

import me.ninethousand.etikahack.EtikaHack;
import me.ninethousand.etikahack.api.event.events.RenderEvent2d;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.GlStateHelper;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.renderer.GlStateManager;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class Watermark extends Module {
    public static final NumberSetting<Float> scale = new NumberSetting<>("Scale", 0.1f, 1.0f, 10.0f, 1);

    public Watermark() {
        addSettings(scale);
    }

    @Override
    public void onHudRender(RenderEvent2d event, VertexHelper vertexHelper) {
        if (nullCheck()) return;

        GlStateManager.pushMatrix();
        GlStateHelper.scale(scale.getValue());

        FontUtil.drawTextHUD(Customise.clientName.getValue() + " v" + EtikaHack.VERSION , 2 / scale.getValue(), 2 / scale.getValue());

        GlStateManager.popMatrix();
    }
}
