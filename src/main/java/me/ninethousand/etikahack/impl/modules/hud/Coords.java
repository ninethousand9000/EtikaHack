package me.ninethousand.etikahack.impl.modules.hud;

import me.ninethousand.etikahack.api.event.events.HudRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.GlStateHelper;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.api.util.rotation.RotationUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class Coords extends Module {
    public static final Setting<Boolean> direction = new Setting<>("Direction", true);
    public static final Setting<Boolean> position = new Setting<>("Coords", true);
    public static final NumberSetting<Float> scale = new NumberSetting<>("Scale", 0.1f, 1.0f, 10.0f, 1);

    public Coords() {
        addSettings(direction, position, scale);
    }

    @Override
    public void onHudRender(HudRenderEvent event, VertexHelper vertexHelper) {
        if (nullCheck()) return;

        GlStateManager.pushMatrix();
        GlStateHelper.scale(scale.getValue());

        final boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
        final int posX = (int) mc.player.posX;
        final int posY = (int) mc.player.posY;
        final int posZ = (int) mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int) (mc.player.posX * nether);
        final int hposZ = (int) (mc.player.posZ * nether);

        final String coordinates = "XYZ " + posX + ", " + posY + ", " + posZ + " " + "[" + hposX + ", " + hposZ + "]";

        final String dir = RotationUtil.getFacing() + " [" + RotationUtil.getTowards() + "]";

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        int items = 1;

        if (position.getValue()) {
            FontUtil.drawTextHUD(coordinates, 2 / scale.getValue(), (scaledResolution.getScaledHeight() - (items * (FontUtil.getStringHeight("[") + 2))) / scale.getValue());
            items++;
        }

        if (direction.getValue()) {
            FontUtil.drawTextHUD(dir, 2 / scale.getValue(), (scaledResolution.getScaledHeight() - (items * (FontUtil.getStringHeight("[") + 2))) / scale.getValue());
            items++;
        }

        GlStateManager.popMatrix();
    }
}
