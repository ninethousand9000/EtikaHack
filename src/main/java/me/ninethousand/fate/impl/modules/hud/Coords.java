package me.ninethousand.fate.impl.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.fate.api.event.events.RenderEvent2d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.gl.GlStateHelper;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import me.ninethousand.fate.api.util.rotation.RotationUtil;
import me.ninethousand.fate.impl.modules.client.ClientColor;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

@ModuleAnnotation(category = ModuleCategory.Hud)
public class Coords extends Module {
    public static final Setting<Boolean> direction = new Setting<>("Direction", true);
    public static final Setting<Boolean> position = new Setting<>("Coords", true);
    public static final NumberSetting<Float> scale = new NumberSetting<>("Scale", 0.1f, 1.0f, 10.0f, 1);

    public Coords() {
        addSettings(direction, position, scale);
    }

    @Override
    public void onHudRender(RenderEvent2d event, VertexHelper vertexHelper) {
        GlStateManager.pushMatrix();
        GlStateHelper.scale(scale.getValue());

        final boolean inHell = mc.world.getBiome(mc.player.getPosition()).getBiomeName().equals("Hell");
        final int posX = (int) mc.player.posX;
        final int posY = (int) mc.player.posY;
        final int posZ = (int) mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int) (mc.player.posX * nether);
        final int hposZ = (int) (mc.player.posZ * nether);

        final String coordinates = ChatFormatting.RESET + "XYZ " + ChatFormatting.WHITE + posX + ", " + posY + ", " + posZ + " " + ChatFormatting.RESET + "[" + ChatFormatting.WHITE + hposX + ", " + hposZ + ChatFormatting.RESET + "]";

        final String dir = RotationUtil.getFacing() + " [" + RotationUtil.getTowards() + "]";

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        int items = 1;

        if (position.getValue()) {
            FontUtil.drawText(coordinates, 2 / scale.getValue(), (scaledResolution.getScaledHeight() - (items * (FontUtil.getStringHeight("[") + 2))) / scale.getValue(), ClientColor.clientColor.getValue().getRGB());
            items++;
        }

        if (direction.getValue()) {
            FontUtil.drawText(dir, 2 / scale.getValue(), (scaledResolution.getScaledHeight() - (items * (FontUtil.getStringHeight("[") + 2))) / scale.getValue(), ClientColor.clientColor.getValue().getRGB());
            items++;
        }

        GlStateManager.popMatrix();
    }
}
