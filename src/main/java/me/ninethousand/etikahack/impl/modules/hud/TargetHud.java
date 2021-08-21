package me.ninethousand.etikahack.impl.modules.hud;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.etikahack.api.event.events.HudRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.math.MathUtil;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.api.util.render.graphics.DrawUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.Comparator;
import java.util.Objects;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class TargetHud extends Module {
    public static final Setting<Boolean> potionEffects = new Setting<>("Potions", true);
        public static final Setting<Boolean> speed = new Setting<>(potionEffects, "Speed", true);
        public static final Setting<Boolean> weakness = new Setting<>(potionEffects, "Weakness", true);
        public static final Setting<Boolean> strength = new Setting<>(potionEffects, "Strength", true);

    public static final Setting<Boolean> armor = new Setting<>("Armor", true);
        public static final Setting<Boolean> showCount = new Setting<>(armor, "Count", true);

    public static final Setting<Boolean> items = new Setting<>("Items", true);
        public static final Setting<Boolean> totem = new Setting<>(items, "Totems", true);
        public static final Setting<Boolean> xp = new Setting<>(items, "XP", true);
        public static final Setting<Boolean> crystal = new Setting<>(items, "Crystals", true);
        public static final Setting<Boolean> gapple = new Setting<>(items, "Gapples", true);


    public TargetHud() {
        addSettings(potionEffects, armor, items);
    }

    @Override
    public void onHudRender(HudRenderEvent event, VertexHelper vertexHelper) {
        if (nullCheck()) return;


        EntityPlayer player = mc.world.playerEntities.stream()
                .filter(otherPlayer -> otherPlayer != mc.player)
                .min(Comparator.comparing(otherPlayer -> mc.player.getDistance(otherPlayer)))
                .orElse(null);

        if (player == null) return;

        GraphicsUtil2d.drawRectFill(vertexHelper, new Vec2d(2, 50), new Vec2d(200, 160), new Color(0xB9494949, true));
        GraphicsUtil2d.drawRectOutline(vertexHelper, new Vec2d(2, 50), new Vec2d(200, 160), 3f, new Color(0xFF494949, true));

        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.color(1, 1, 1 ,1);

        DrawUtil.drawEntityOnScreen(30, 150, 50, 100, player);

        GlStateManager.enableRescaleNormal();
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

        int responseTime;

        try {
            responseTime = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(player.getUniqueID()).getResponseTime();
        } catch (Exception exception) {
            responseTime = 0;
        }

        float totalHP = player.getHealth() + player.getAbsorptionAmount();
        boolean hasSpeed = player.isPotionActive(MobEffects.SPEED);
        boolean hasWeakness = player.isPotionActive(MobEffects.WEAKNESS);
        boolean hasStrength = player.isPotionActive(MobEffects.STRENGTH);

        String text = ChatFormatting.WHITE + player.getName() + " | " + getPingColor(responseTime) + responseTime + " ms" +ChatFormatting.WHITE + " | " + getHpColor(totalHP) + MathUtil.roundNumber(totalHP, 1) + " HP";

        FontUtil.drawText(text, 62, 58, Customise.hudColor.getValue().getRGB());

        int y = 78;

        if (potionEffects.getValue()) {
            if (speed.getValue()) {
                FontUtil.drawText("Speed: " + (hasSpeed ? ChatFormatting.GREEN + " Active" : ChatFormatting.RED + " Inactive"), 62, y, new Color(124, 175, 198).getRGB());
                y += 12;
            }

            if (weakness.getValue()) {
                FontUtil.drawText("Weakness: " + (hasWeakness ? ChatFormatting.GREEN + " Active" : ChatFormatting.RED + " Inactive"), 62, y, new Color(72, 77, 72).getRGB());
                y += 12;
            }

            if (strength.getValue()) {
                FontUtil.drawText("Strength: " + (hasStrength ? ChatFormatting.GREEN + " Active" : ChatFormatting.RED + " Inactive"), 62, y, new Color(147, 36, 35).getRGB());
                y += 12;
            }
        }

        int x = 122;

        if (armor.getValue()) {
            for (ItemStack armorStack : player.inventory.armorInventory) {
                int pieces = player.inventory.mainInventory.stream().filter(stack -> stack.getItem() == armorStack.getItem()).mapToInt(ItemStack::getCount).sum() + player.inventory.armorInventory.stream().filter(stack -> stack.getItem() == armorStack.getItem()).mapToInt(ItemStack::getCount).sum();
                GraphicsUtil2d.drawItem(armorStack, x, y, pieces, showCount.getValue());
                x -= 20;
            }

            y += 20;
        }

        x = 62;

        if (items.getValue()) {
            if (totem.getValue()) {
                int totems = player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
                GraphicsUtil2d.drawItem(new ItemStack(Items.TOTEM_OF_UNDYING), x, y, totems, true);
                x+=20;
            }

            if (xp.getValue()) {
                int xp = player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE).mapToInt(ItemStack::getCount).sum();
                GraphicsUtil2d.drawItem(new ItemStack(Items.EXPERIENCE_BOTTLE), x, y, xp, true);
                x += 20;
            }

            if (crystal.getValue()) {
                int crystals = player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum();
                GraphicsUtil2d.drawItem(new ItemStack(Items.END_CRYSTAL), x, y, crystals, true);
                x += 20;
            }

            if (gapple.getValue()) {
                int gaps = player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum();
                GraphicsUtil2d.drawItem(new ItemStack(Items.GOLDEN_APPLE), x, y, gaps, true);
                x += 20;
            }
        }

    }

    private ChatFormatting getPingColor(float ping) {
        if (ping <= 50) return ChatFormatting.GREEN;
        if (ping <= 150) return ChatFormatting.YELLOW;
        else return ChatFormatting.RED;
    }

    private ChatFormatting getHpColor(float hp) {
        if (hp > 18) return ChatFormatting.GREEN;
        if (hp > 16) return ChatFormatting.DARK_GREEN;
        if (hp > 12) return ChatFormatting.YELLOW;
        if (hp > 16) return ChatFormatting.RED;
        else return ChatFormatting.DARK_RED;
    }
}
