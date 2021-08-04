package me.ninethousand.fate.impl.modules.hud;

import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.event.events.RenderEvent2d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.gl.GlStateHelper;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import me.ninethousand.fate.impl.modules.client.Customise;
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

        FontUtil.drawText(Customise.clientName.getValue() + " v" + Fate.VERSION , 2 / scale.getValue(), 2 / scale.getValue(), Customise.clientColor.getValue().getRGB());

        GlStateManager.popMatrix();
    }
}
