package me.ninethousand.etikahack.impl.modules.player;

import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;

@ModuleAnnotation(category = ModuleCategory.PLAYER)
public class EnderBackpack extends Module {
    private GuiScreen echestScreen = null;

    @Override
    public void onUpdate() {
        InventoryBasic basic;
        Container container;
        if (mc.currentScreen instanceof GuiContainer && (container = ((GuiContainer) mc.currentScreen).inventorySlots) instanceof ContainerChest && ((ContainerChest) container).getLowerChestInventory() instanceof InventoryBasic && (basic = (InventoryBasic) ((ContainerChest) container).getLowerChestInventory()).getName().equalsIgnoreCase("Ender Chest")) {
            this.echestScreen = mc.currentScreen;
            mc.currentScreen = null;
        }
    }

    @Override
    public void onDisable() {
        if (!nullCheck() && this.echestScreen != null) {
            mc.displayGuiScreen(this.echestScreen);
        }
        this.echestScreen = null;
    }
}
