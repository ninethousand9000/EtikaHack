package me.ninethousand.etikahack.impl.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.etikahack.api.event.events.RenderEvent2d;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class Armor extends Module {
    @Override
    public void onHudRender(RenderEvent2d event, VertexHelper vertexHelper) {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        final int width = scaledResolution.getScaledWidth(), height = scaledResolution.getScaledHeight();

        final int i = width / 2;
        int iteration = 0;
        final int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);

        for (final ItemStack is : mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }

            final int x = i - 90 + (9 - iteration) * 20 + 2;

            GraphicsUtil2d.drawItem(is, x, y, 1, false);

            final int itemDurability = is.getMaxDamage() - is.getItemDamage();
            final float green = (is.getMaxDamage() - (float) is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            int dmg = 100 - (int) (red * 100.0f);

            ChatFormatting chatForm = getArmorColor(dmg);

            FontUtil.drawText("" + chatForm + dmg, x + 17 - FontUtil.getStringWidth("" + chatForm + dmg), y - 9, Color.white.getRGB());
        }
    }

    private ChatFormatting getArmorColor(int health) {
        if (health > 90) return ChatFormatting.GREEN;
        if (health > 70) return ChatFormatting.DARK_GREEN;
        if (health > 50) return ChatFormatting.YELLOW;
        if (health > 30) return ChatFormatting.RED;
        else return ChatFormatting.DARK_RED;
    }
}
