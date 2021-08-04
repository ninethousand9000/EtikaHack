package me.ninethousand.fate.impl.modules.misc;

import baritone.Baritone;
import baritone.api.BaritoneAPI;
import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.event.events.PlayerDamageBlockEvent;
import me.ninethousand.fate.api.event.events.RenderEvent3d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.game.BlockInteractionHelper;
import me.ninethousand.fate.api.util.game.InventoryUtil;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil3d;
import me.ninethousand.fate.api.util.rotation.RotationUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class AutoDuper extends Module {
    public static final Setting<DirectionCompass> directionFacing = new Setting<>("Dir", DirectionCompass.NORTH);

    public AutoDuper() {
        addSettings(directionFacing);
    }

    private static BlockPos chestPos, standPos;
    private static boolean chestOpen = false, moving = false;

    @Override
    public void onEnable() {
        chestPos = null;
        standPos = null;
        moving = false;
        chestOpen = false;
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        if (chestPos != null) {
            if (mc.world.getBlockState(chestPos).getBlock() == Blocks.AIR) {
                int slot = InventoryUtil.findBlockInHotbar(Blocks.CHEST);
                if (slot != -1) {
                    mc.player.inventory.currentItem = slot;
                    BlockInteractionHelper.placeBlockScaffold(chestPos);
                }
            }

            if (mc.world.getBlockState(chestPos).getBlock() == Blocks.CHEST && !chestOpen) {
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
            }
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
    public void onWorldRender(RenderEvent3d event3d) {
        if (chestPos != null) GraphicsUtil3d.renderStandardBox(chestPos, Color.RED, GraphicsUtil3d.RenderBoxMode.Outline, 0, 1f);
        if (standPos != null) GraphicsUtil3d.renderStandardBox(standPos, Color.GREEN, GraphicsUtil3d.RenderBoxMode.Outline, 0, 1f);
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
}
