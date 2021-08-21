package me.ninethousand.etikahack.impl.modules.misc;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.event.events.PlayerDamageBlockEvent;
import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.game.BlockInteractionHelper;
import me.ninethousand.etikahack.api.util.game.InventoryUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil3d;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class AutoDuper extends Module {
    public static final Setting<DirectionCompass> directionFacing = new Setting<>("Dir", DirectionCompass.NORTH);
    public static final Setting<Boolean> start = new Setting<>("Start", false);
    public static final Setting<Boolean> reset = new Setting<>("Reset", false);

    public AutoDuper() {
        addSettings(directionFacing, start, reset);
    }

    private static BlockPos chestPos, standPos;
    private static boolean chestOpen = false, moved = false, duping = false, chestPlaced = false, minecartPlaced = false,
    swappedItem = false, chestMinecart = false, exploded = false, atLoot = false;

    @Override
    public void onEnable() {
        reset();
    }

    int walkingTicks = 0;

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        if (chestPos == null || standPos == null) return;

        if (start.getValue() || duping) {
            start.setValue(false);
            duping = true;

            if (!chestPlaced) {
                if (mc.world.getBlockState(chestPos).getBlock() == Blocks.AIR) {
                    int slot = InventoryUtil.findBlockInHotbar(Blocks.CHEST);
                    if (slot != -1) {
                        mc.player.inventory.currentItem = slot;
                        BlockInteractionHelper.placeBlockScaffold(chestPos);
                    }
                }

                if (mc.world.getBlockState(chestPos).getBlock() == Blocks.CHEST) {
                    chestPlaced = true;
                    return;
                }
            }

            if (chestPlaced && !minecartPlaced) {
                int slot = InventoryUtil.findItemInHotbar(Items.TNT_MINECART);
                if (slot != -1) {
                    mc.player.inventory.currentItem = slot;
                    mc.playerController.processRightClickBlock(mc.player, mc.world, standPos.offset(directionFacing.getValue().enumFacing).up(), directionFacing.getValue().enumFacing, mc.player.getLookVec(), EnumHand.MAIN_HAND);
                    minecartPlaced = true;
                    return;
                }
            }

            if (minecartPlaced && !moved) {
                if (directionFacing.getValue() == DirectionCompass.NORTH) {
                    mc.player.motionZ = -0.1;
                }

                if (directionFacing.getValue() == DirectionCompass.EAST) {
                    mc.player.motionX = 0.1;
                }

                if (directionFacing.getValue() == DirectionCompass.SOUTH) {
                    mc.player.motionZ = 0.1;
                }

                if (directionFacing.getValue() == DirectionCompass.WEST) {
                    mc.player.motionX = -0.1;
                }

                if (walkingTicks == 5) {
                    moved = true;
                }

                walkingTicks++;

                return;
            }

            if (moved && !chestOpen) {
                mc.playerController.processRightClickBlock(mc.player, mc.world, chestPos, directionFacing.getValue().enumFacing, mc.player.getLookVec(), EnumHand.MAIN_HAND);
                chestOpen = true;

                return;
            }

            if (mc.currentScreen instanceof GuiChest && chestOpen) {
                GuiChest chest = (GuiChest) mc.currentScreen;

                for (int i = 27; i <= 62; i++) {
                    ItemStack stack = chest.inventorySlots.getInventory().get(i);

                    if (stack == ItemStack.EMPTY) {
                        continue;
                    }

                    if (stack.getItem() instanceof ItemBlock) {
                        if (((ItemBlock) stack.getItem()).getBlock() instanceof BlockShulkerBox) {
                            mc.playerController.windowClick(chest.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                            return;
                        }
                    }

                    if (stack.getItem() == Items.TNT_MINECART && !chestMinecart) {
                        mc.playerController.windowClick(chest.inventorySlots.windowId, i, 0, ClickType.QUICK_MOVE, mc.player);
                        chestMinecart = true;
                        return;
                    }
                }

                swappedItem = true;
            }

            if (mc.currentScreen == null && swappedItem & !exploded) {
                exploded = true;
                Command.sendClientMessageDefault("Chest Exploded, Going To Loot");
            }

            if (exploded) {

            }

            if (exploded && !atLoot) {
                if (directionFacing.getValue() == DirectionCompass.NORTH) {
                    mc.player.motionZ = -0.5;
                }

                if (directionFacing.getValue() == DirectionCompass.EAST) {
                    mc.player.motionX = 0.5;
                }

                if (directionFacing.getValue() == DirectionCompass.SOUTH) {
                    mc.player.motionZ = 0.5;
                }

                if (directionFacing.getValue() == DirectionCompass.WEST) {
                    mc.player.motionX = -0.5;
                }

                if (walkingTicks == 35) {
                    atLoot = true;
                    mc.player.motionX = 0;
                    mc.player.motionZ = 0;
                    Command.sendClientMessageDefault("At Loot, Going To Chest");
                    mc.player.sendChatMessage("#goto chest");
                    return;
                }

                walkingTicks++;
            }

            /*if (swappedItem) {
                moved = false;
                chestOpen = false;
                duping = false;
                chestPlaced = false;
                moved = false;
                chestOpen = false;
                minecartPlaced = false;
                swappedItem = false;
                chestMinecart = false;
            }*/

        }

        if (reset.getValue()) {
            reset.setValue(false);
            reset();
        }
    }

    @SubscribeEvent
    public void onDamageBlock(PlayerDamageBlockEvent event) {
        if (chestPos == null) {
            chestPos = event.pos().up();
        }

        else if (standPos == null) {
            standPos = event.pos();
        }
    }

    @Override
    public void onWorldRender(WorldRenderEvent event3d) {
        if (chestPos != null) GraphicsUtil3d.renderStandardBox(chestPos, Color.RED, GraphicsUtil3d.RenderBoxMode.Outline, 0, 1f);
        if (standPos != null) GraphicsUtil3d.renderStandardBox(standPos, Color.GREEN, GraphicsUtil3d.RenderBoxMode.Outline, 0, 1f);
    }

    @Override
    public void onDisable() {
        reset();
    }

    private void reset() {
        chestPos = null;
        standPos = null;
        moved = false;
        chestOpen = false;
        duping = false;
        chestPlaced = false;
        moved = false;
        chestOpen = false;
        minecartPlaced = false;
        swappedItem = false;
        chestMinecart = false;
        atLoot = false;
        walkingTicks = 0;
    }

    private enum DirectionCompass {
        NORTH(EnumFacing.NORTH),
        EAST(EnumFacing.EAST),
        SOUTH(EnumFacing.SOUTH),
        WEST(EnumFacing.WEST);

        private EnumFacing enumFacing;

        DirectionCompass(EnumFacing enumFacing) {
            this.enumFacing = enumFacing;
        }
    }

    /*if (mc.world.getBlockState(chestPos).getBlock() == Blocks.CHEST && !chestOpen) {
                    mc.playerController.processRightClickBlock(mc.player, mc.world, chestPos, directionFacing.getValue().enumFacing, mc.player.getLookVec(), EnumHand.MAIN_HAND);
                    chestOpen = true;
                }

                if (!moving) {
                    if (directionFacing.getValue() == DirectionCompass.NORTH) {
                        mc.player.motionZ = -0.3;
                        moving = true;
                    }

                    if (directionFacing.getValue() == DirectionCompass.EAST) {
                        mc.player.motionX = 0.3;
                        moving = true;
                    }

                    if (directionFacing.getValue() == DirectionCompass.SOUTH) {
                        mc.player.motionZ = 0.3;
                        moving = true;
                    }

                    if (directionFacing.getValue() == DirectionCompass.WEST) {
                        mc.player.motionX = -0.3;
                        moving = true;
                    }
                }*/
}
