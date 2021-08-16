package me.ninethousand.etikahack.api.util.game;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;

import java.util.Arrays;

// Credit To Reap and Ace

public class InventoryUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void switchToHotbarSlot(int slot, boolean silent) {
        if (InventoryUtil.mc.player.inventory.currentItem == slot || slot < 0) {
            return;
        }
        if (silent) {
            InventoryUtil.mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            InventoryUtil.mc.playerController.updateController();
        } else {
            InventoryUtil.mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
            InventoryUtil.mc.player.inventory.currentItem = slot;
            InventoryUtil.mc.playerController.updateController();
        }
    }

    public static int findBlockInHotbar(Block blockToFind) {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            // filter out non-block items
            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof ItemBlock)) {
                continue;
            }

            Block block = ((ItemBlock) stack.getItem()).getBlock();
            if (block.equals(blockToFind)) {
                slot = i;
                break;
            }

        }

        return slot;

    }

    public static int findItemInHotbar(Item itemToFind) {

        // search blocks in hotbar
        int slot = -1;
        for (int i = 0; i < 9; i++) {

            ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack == ItemStack.EMPTY || !(stack.getItem() instanceof Item)) {
                continue;
            }

            Item item = (stack.getItem());
            if (item.equals(itemToFind)) {
                slot = i;
                break;
            }

        }

        return slot;

    }

    public static int getItems(Item i) {
        return mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum() + mc.player.inventory.offHandInventory.stream().filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }

    public static boolean hasItem(Item item) {
        return mc.player.inventoryContainer.inventorySlots.stream()
                .filter(Slot::getHasStack)
                .filter(slot -> slot.getStack().getItem() == item)
                .mapToInt(slot -> slot.getStack().getCount())
                .sum() > 0;
    }


    public static int getBlocks(Block block) {
        return getItems(Item.getItemFromBlock(block));
    }

    private static int spoofedPos = -1;

    public static Item getSpoofedItem() {
        if (spoofedPos == -1) return null;

        return mc.player.inventory.getStackInSlot(spoofedPos).getItem();
    }

    public static boolean hasItemCount(Class<? extends Item> item) {
        return hasItemCount(item, 0);
    }

    public static boolean hasItemCount(Class<? extends Item> item, int count) {
        return mc.player.inventoryContainer.inventorySlots.stream()
                .filter(Slot::getHasStack)
                .filter(slot -> slot.getStack().getItem().getClass().isAssignableFrom(item))
                .mapToInt(slot -> slot.getStack().getCount())
                .sum() > count;
    }

    public static boolean hasItemCount(Item item) {
        return hasItemCount(item, 0);
    }

    public static boolean hasItemCount(Item item, int count) {
        return mc.player.inventoryContainer.inventorySlots.stream()
                .filter(Slot::getHasStack)
                .filter(slot -> slot.getStack().getItem() == item)
                .mapToInt(slot -> slot.getStack().getCount())
                .sum() > count;
    }

    @SafeVarargs
    public static boolean switchToEitherItem(SwitchMode mode, Class<? extends Item>... possibleItems) {
        if (Arrays.stream(possibleItems).anyMatch(item -> mc.player.getHeldItemMainhand().getItem().getClass().isAssignableFrom(item))) return true;

        for (Class<? extends Item> item : possibleItems) {
            int itemSlot = getHotbarSlot(item);

            if (itemSlot != -1) {
                if (mode == SwitchMode.NORMAL)  mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem = itemSlot));
                else if (mode == SwitchMode.GHOST) mc.player.connection.sendPacket(new CPacketHeldItemChange(spoofedPos = itemSlot));

                return true;
            }
        }

        return false;
    }

    public static boolean switchToEitherItem(SwitchMode mode, Item... possibleItems) {
        if (Arrays.stream(possibleItems).anyMatch(item -> mc.player.getHeldItemMainhand().getItem() == item)) return true;

        for (int i = 0; i < 9; i++) {
            Item current = mc.player.inventory.getStackInSlot(i).getItem();

            for (Item item : possibleItems) {
                if (item instanceof ItemBlock) {
                    if (!(current instanceof ItemBlock)) continue;

                    if (((ItemBlock) item).getBlock() != ((ItemBlock) current).getBlock()) continue;
                } else if (item != current) continue;

                if (mode == SwitchMode.NORMAL)  mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem = i));
                else if (mode == SwitchMode.GHOST) mc.player.connection.sendPacket(new CPacketHeldItemChange(spoofedPos = i));

                return true;
            }
        }

        return false;
    }

    @SafeVarargs
    public static boolean isNotHoldingEitherItem(Class<? extends Item>... possibleItems) {
        for (Class<? extends Item> item : possibleItems)
            if (mc.player.getHeldItemMainhand().getItem().getClass().isAssignableFrom(item) || (getSpoofedItem() != null && item.isAssignableFrom(getSpoofedItem().getClass())))
                return false;

        return true;
    }

    public static boolean switchToFirstBlock(SwitchMode switchMode) {
        if (mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock) return true;

        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock) {
                if (switchMode == SwitchMode.NORMAL) mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem = i));
                else if (switchMode == SwitchMode.GHOST) mc.player.connection.sendPacket(new CPacketHeldItemChange(spoofedPos = i));
            }
        }

        return false;
    }

    public static void replaceSlots(int slot1, int slot2) {
        if (slot1 > 45 || slot1 < 0 || slot2 > 45 || slot2 < 0) return;

        clickSlot(slot1);
        clickSlot(slot2);
        clickSlot(slot1);
    }

    public static void putItemInOffhand(Item item, boolean searchHotbar) {
        if (mc.player.getHeldItemOffhand().getItem() == item) return;

        int itemSlot = getItemSlot(item, searchHotbar);

        if (itemSlot == -1) return;

        replaceSlots(itemSlot, 45);
    }

    private static int getItemSlot(Item item, boolean searchHotbar) {
        for (int i = searchHotbar ? 0 : 9; i < 45; i++)
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) return i < 9 ? i + 36 : i;

        return -1;
    }

    private static int getHotbarSlot(Class<? extends Item> item) {
        for (int i = 0; i < 9; i++) if (mc.player.inventory.getStackInSlot(i).getItem().getClass().isAssignableFrom(item)) return i;

        return -1;
    }

    private static void clickSlot(int slot) {
        if (slot > 45 || slot == 0) return;

        mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 0, ClickType.PICKUP, mc.player);
    }

    public enum SwitchMode {
        GHOST,
        NONE,
        NORMAL
    }

    private InventoryUtil() {
        throw new UnsupportedOperationException();
    }
}


