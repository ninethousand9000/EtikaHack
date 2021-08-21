package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.mixin.accessors.IItemRenderer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class Swing extends Module {
    public static final Setting<Hand> hand = new Setting<>("Hand", Hand.Offhand);
    public static final NumberSetting<Integer> factor = new NumberSetting<>("SlowFactor", 0, 1, 5, 1);
    public static final Setting<Boolean> animations = new Setting<>("OldAnimation", false);

    public Swing() {
        addSettings(hand, factor, animations);
    }

    private boolean readyToReturn = false;

    @Override
    public void onUpdate() {
        if (mc.world == null)
            return;
        if (hand.getValue().equals(Hand.Offhand)) {
            mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (hand.getValue().equals(Hand.Mainhand)) {
            mc.player.swingingHand = EnumHand.MAIN_HAND;
        }

        if (animations.getValue()) {
            final EntityPlayerSP Sp = mc.player;
            final ItemStack Stack = Sp.getHeldItemMainhand();
            if (Stack.getItem() instanceof ItemSword) {
                final EntityRenderer entity = mc.entityRenderer;
                final ItemRenderer itemRenderer = entity.itemRenderer;
                if (((IItemRenderer) itemRenderer).getPrevEquippedProgressMainHand() >= 0.9) {
                    readyToReturn = true;
                }
            }
            final ItemStack var70 = Sp.getHeldItemMainhand();

            if (readyToReturn) {
                EntityRenderer entity = mc.entityRenderer;
                ItemRenderer itemRenderer = entity.itemRenderer;
                ((IItemRenderer) itemRenderer).setEquippedProgressMainHand(1.0f);
                entity = mc.entityRenderer;
                itemRenderer = entity.itemRenderer;
                final EntityPlayerSP var71 = mc.player;
                ((IItemRenderer) itemRenderer).setItemStackMainHand(var71.getHeldItemMainhand());
                readyToReturn = false;
            }
        }

    }

    public enum Hand {
        Offhand,
        Mainhand,
    }
}
