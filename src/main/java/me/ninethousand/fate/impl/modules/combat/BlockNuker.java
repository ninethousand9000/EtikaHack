package me.ninethousand.fate.impl.modules.combat;

import me.ninethousand.fate.api.event.events.PacketEvent;
import me.ninethousand.fate.api.event.events.RenderEvent3d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.ui.newgui.GuiColors;
import me.ninethousand.fate.api.util.game.InventoryUtil;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil3d;
import me.ninethousand.fate.api.util.rotation.RotationManager;
import me.ninethousand.fate.mixin.accessors.ICPacketPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.COMBAT)
public class BlockNuker extends Module {
    public static final Setting<Boolean> blocks = new Setting<>("Blocks", true);
    public static final Setting<Boolean> shulkers = new Setting<>(blocks, "ShulkerBox", true);
    public static final Setting<Boolean> hoppers = new Setting<>(blocks, "Hoppers", true);
    public static final NumberSetting<Float> range = new NumberSetting<>("Range", 0f, 5.0f, 6.0f, 1);
    public static final Setting<SwapMode> swap = new Setting<>("Swap", SwapMode.Normal);
    public static final Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static final Setting<Boolean> render = new Setting<>("Render", true);
    public static final Setting<GraphicsUtil3d.RenderBoxMode> renderMode = new Setting<>(render,"RenderMode", GraphicsUtil3d.RenderBoxMode.Outline);
    public static final NumberSetting<Float> outline = new NumberSetting<>(render, "OutlineWidth", 0f, 2.0f, 6.0f, 1);
    public static final Setting<Color> color = new Setting<>(render, "Color", GuiColors.accent);


    public BlockNuker() {
        addSettings(blocks, range, swap, rotate, render);
    }

    float yaw;
    float pitch;
    boolean isSpoofing;

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        if (getTargetBlock() != null) {
            if (swap.getValue() == SwapMode.Normal && InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE) != -1) {
                int pickSlot = InventoryUtil.findItemInHotbar(Items.DIAMOND_PICKAXE);
                mc.player.inventory.currentItem = pickSlot;
            }
            if (rotate.getValue()) {
                isSpoofing = true;
                lookAtPacket(getTargetBlock().getPos().getX() + .5, getTargetBlock().getPos().getY() - 1, getTargetBlock().getPos().getZ() + .5, mc.player);
            }
            mc.player.swingArm(EnumHand.MAIN_HAND);

            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, getTargetBlock().getPos(), EnumFacing.SOUTH));
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, getTargetBlock().getPos(), EnumFacing.SOUTH));
        }
        if (rotate.getValue()) {
            if (getTargetBlock() == null) {
                resetRotations();
            }
        }
    }

    public TileEntity getTargetBlock() {
        TileEntity target = null;

        try {
            if (shulkers.getValue()) {
                for (TileEntity shulker : mc.world.loadedTileEntityList) {
                    if (shulker instanceof TileEntityShulkerBox) {
                        if (shulker.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= (range.getValue() * range.getValue())) {
                            target = shulker;
                        }
                    }
                }
            }

            if (hoppers.getValue()) {
                for (TileEntity hopper : mc.world.loadedTileEntityList) {
                    if (hopper instanceof TileEntityHopper) {
                        if (hopper.getDistanceSq(mc.player.posX, mc.player.posY, mc.player.posZ) <= (range.getValue() * range.getValue())) {
                            target = hopper;
                        }
                    }
                }
            }
        }

        catch (Exception nah) {}

        return target;
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (nullCheck()) return;

        if (rotate.getValue()) {
            Packet packet = event.getPacket();
            if (packet instanceof CPacketPlayer) {
                ((ICPacketPlayer) packet).setYaw(yaw);
                ((ICPacketPlayer) packet).setPitch(pitch);
            }
        }
    }

    @Override
    public void onWorldRender(RenderEvent3d event3d) {
        if (nullCheck()) return;

        if (render.getValue() && getTargetBlock() != null) {
            GraphicsUtil3d.renderStandardBox(getTargetBlock().getPos(), color.getValue(), renderMode.getValue(), 0, outline.getValue());
        }
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        if (rotate.getValue()) {
            resetRotations();
        }
    }

    public void resetRotations() {
        yaw = mc.player.rotationYaw;
        pitch = mc.player.rotationPitch;
    }

    private void lookAtPacket(double px, double py, double pz, EntityPlayer me) {
        double[] v = RotationManager.calculateLookAt(px, py, pz, me);
        setYawAndPitch((float) v[0], (float) v[1]);
    }

    private void setYawAndPitch(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private enum SwapMode {
        None,
        Silent,
        Normal
    }
}
