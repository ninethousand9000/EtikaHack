package me.ninethousand.etikahack.impl.modules.combat;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.social.FriendManager;
import me.ninethousand.etikahack.api.util.game.BlockInteractionHelper;
import me.ninethousand.etikahack.api.util.game.PlayerUtil;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil3d;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.COMBAT)
public class SelfWeb extends Module {
    public static final Setting<Boolean> alwaysActive = new Setting<>("AlwaysActive", false);
    public static final Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public static final NumberSetting<Integer> enemyRange = new NumberSetting<>("EnemyRange", 0, 4, 8, 1);
    public static final Setting<Boolean> shouldRender = new Setting<>("Render", true);
    public static final Setting<Color> color = new Setting<>(shouldRender, "BoxColor", new Color(0x79E00909, true));
    public static final Setting<Color> colorO = new Setting<>(shouldRender, "OutlineColor", new Color(0xFFE00909, true));
    public static final NumberSetting<Integer> lineWidth = new NumberSetting<>(shouldRender, "OutlineWidth", 0, 2, 5, 1);
    public static final Setting<GraphicsUtil3d.RenderBoxMode> boxMode = new Setting<>(shouldRender, "Mode", GraphicsUtil3d.RenderBoxMode.Pretty);

    public SelfWeb() {
        addSettings(
                alwaysActive,
                rotate,
                enemyRange,
                shouldRender
        );
    }

    int new_slot = -1;

    boolean sneak = false;

    BlockPos posToRender = null;

    @Override
    public void onEnable() {

        if (mc.player != null) {

            posToRender = PlayerUtil.getPlayerBlockPos();

            new_slot = findInHotbar();

            if (new_slot == -1) {
                Command.sendClientMessageDefault("AutoWeb - No Webs in hotbar, toggling");
                toggle();
            }

        }

    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            posToRender = null;

            if (sneak) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                sneak = false;
            }
        }
    }

    @Override
    public void onUpdate() {

        if (mc.player == null) return;

        if (alwaysActive.getValue()) {

            EntityPlayer target = findTarget();
            if (target == null) return;

            if (mc.player.getDistance(target) < enemyRange.getValue() && isSurrounded()) {
                int last_slot = mc.player.inventory.currentItem;
                mc.player.inventory.currentItem = new_slot;
                mc.playerController.updateController();
                placeBlock(PlayerUtil.getPlayerBlockPos());
                mc.player.inventory.currentItem = last_slot;
            }

        } else {
            int last_slot = mc.player.inventory.currentItem;
            mc.player.inventory.currentItem = new_slot;
            mc.playerController.updateController();
            placeBlock(PlayerUtil.getPlayerBlockPos());
            mc.player.inventory.currentItem = last_slot;
            disable();
            posToRender = null;
        }


    }

    @Override
    public void onWorldRender(WorldRenderEvent event3d) {
        if (posToRender != null && shouldRender.getValue()) {
            GraphicsUtil3d.renderStandardBox(posToRender, color.getValue(), boxMode.getValue(), 0, lineWidth.getValue());
        }
    }

    public EntityPlayer findTarget()  {

        if (mc.world.playerEntities.isEmpty())
            return null;

        EntityPlayer closestTarget = null;

        for (final EntityPlayer target : mc.world.playerEntities)
        {
            if (target == mc.player)
                continue;

            if (FriendManager.isFriend(target.getName()))
                continue;

            if (!(target instanceof EntityLivingBase))
                continue;

            if (target.getHealth() <= 0.0f)
                continue;

            if (closestTarget != null)
                if (mc.player.getDistance(target) > mc.player.getDistance(closestTarget))
                    continue;

            closestTarget = target;
        }

        return closestTarget;
    }

    private int findInHotbar() {

        for (int i = 0; i < 9; ++i) {

            final ItemStack stack = mc.player.inventory.getStackInSlot(i);

            if (stack.getItem() == Item.getItemById(30)) {
                return i;
            }

        }
        return -1;
    }

    private boolean isSurrounded() {

        BlockPos player_block = PlayerUtil.getPlayerBlockPos();
        return mc.world.getBlockState(player_block.east()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block.west()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block.north()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block.south()).getBlock() != Blocks.AIR
                && mc.world.getBlockState(player_block).getBlock() == Blocks.AIR;

    }

    private void placeBlock(BlockPos pos) {

        if (!mc.world.getBlockState(pos).getMaterial().isReplaceable()) {
            return;
        }

        if (!BlockInteractionHelper.checkForNeighbours(pos)) {
            return;
        }

        for (EnumFacing side : EnumFacing.values()) {

            BlockPos neighbor = pos.offset(side);

            EnumFacing side2 = side.getOpposite();

            if (!BlockInteractionHelper.canBeClicked(neighbor)) continue;

            if (BlockInteractionHelper.blackList.contains(mc.world.getBlockState(neighbor).getBlock())) {
                mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
                sneak = true;
            }

            Vec3d hitVec = new Vec3d(neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));

            if (rotate.getValue()) {
                BlockInteractionHelper.faceVectorPacketInstant(hitVec);
            }

            mc.playerController.processRightClickBlock(mc.player, mc.world, neighbor, side2, hitVec, EnumHand.MAIN_HAND);
            mc.player.swingArm(EnumHand.MAIN_HAND);

            return;
        }

    }
}
