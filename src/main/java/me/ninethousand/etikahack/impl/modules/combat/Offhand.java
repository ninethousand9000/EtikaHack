package me.ninethousand.etikahack.impl.modules.combat;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.game.InventoryUtil;
import me.ninethousand.etikahack.api.util.game.PlayerUtil;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

@ModuleAnnotation(category = ModuleCategory.COMBAT)
public class Offhand extends Module {
    public static final Setting<Boolean> itemHeader = new Setting<>("Item", true);
    public static final Setting<OffhandItem> item1 = new Setting<>(itemHeader, "Primary", OffhandItem.TOTEM);
    public static final Setting<OffhandItem> item2 = new Setting<>(itemHeader, "Secondary", OffhandItem.CRYSTAL);

    public static final Setting<Boolean> checks = new Setting<>("Checks", true);
    public static final Setting<Boolean> elytraCheck = new Setting<>(checks, "ElytraCheck", true);
    public static final NumberSetting<Float> swapHealth = new NumberSetting<>(checks, "HPCheck", 1.0f, 16.5f, 36.0f, 1);
    public static final NumberSetting<Integer> fallHeight = new NumberSetting<>(checks, "FallCheck", 3, 10, 50, 1);

    public static final Setting<Boolean> hotBarPriority = new Setting<>("HotbarPriority", false);
    public static final Setting<Boolean> strict = new Setting<>("Strict", false);
    public static final NumberSetting<Integer> delay = new NumberSetting<>("DelayTicks", 0, 0, 6, 1);

    private int ticksWaited = 0;

    public Offhand() {
        addSettings(itemHeader, checks, hotBarPriority, strict, delay);
    }

    @Override
    public void onDisable() {
        ticksWaited = delay.getValue();
    }

    @Override
    public void onUpdate() {
        if (ticksWaited++ <= delay.getValue()) return;

        ticksWaited = 0;
        InventoryUtil.putItemInOffhand(getWantedItem(), hotBarPriority.getValue());
    }

    private Item getWantedItem() {
        if (InventoryUtil.hasItemCount(Items.TOTEM_OF_UNDYING.getClass()) &&
                (swapHealth.getValue() > PlayerUtil.getHealth() ||
                        checkTriggered() ||
                        (strict.getValue() && (mc.player.getHeldItemMainhand().getItem() == Items.GOLDEN_APPLE ||
                                mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL))))
            return Items.TOTEM_OF_UNDYING;

        if (InventoryUtil.hasItemCount(item1.getValue().item.getClass())) return item1.getValue().item;
        if (InventoryUtil.hasItemCount(item2.getValue().item.getClass())) return item1.getValue().item;

        return Items.TOTEM_OF_UNDYING;
    }

    private boolean checkTriggered() {
        return mc.player.fallDistance >= fallHeight.getValue() || (elytraCheck.getValue() && mc.player.isElytraFlying());
    }

    @SuppressWarnings("unused")
    private enum OffhandItem {
        CRYSTAL(Items.END_CRYSTAL),
        GAPPLE(Items.GOLDEN_APPLE),
        TOTEM(Items.TOTEM_OF_UNDYING);

        private final Item item;

        OffhandItem(Item item) {
            this.item = item;
        }

        public Item getItem() {
            return item;
        }
    }
}
