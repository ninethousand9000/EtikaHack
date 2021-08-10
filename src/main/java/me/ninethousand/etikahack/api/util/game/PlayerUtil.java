package me.ninethousand.etikahack.api.util.game;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class PlayerUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float getHealth() {
        return mc.player.getHealth() + mc.player.getAbsorptionAmount();
    }

    public static BlockPos getPlayerBlockPos() {
        return getPlayerBlockPos(mc.player);
    }

    public static BlockPos getPlayerBlockPos(EntityPlayer player) {
        return new BlockPos(Math.floor(player.posX), Math.floor(player.posY), Math.floor(player.posZ));
    }

    public static Vec3d getCenteredPos() {
        return new Vec3d(Math.floor(mc.player.posX) + 0.5, Math.floor(mc.player.posY) + 0.5, Math.floor(mc.player.posZ) + 0.5);
    }

    public static Vec3d getPlayerEyeVec() {
        return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
    }

    public static BlockPos[] getSurroundingBlocks() {
        Vec3d centeredPos = getCenteredPos();
        BlockPos[] returnValue = new BlockPos[4];

        int position = 0;
        for (EnumFacing enumFacing : EnumFacing.HORIZONTALS) {
            returnValue[position++] = new BlockPos(centeredPos).offset(enumFacing);
        }

        return returnValue;
    }

    public static boolean canSeeEntity(Entity entity) {
        return mc.world.rayTraceBlocks(
                new Vec3d(
                        mc.player.posX,
                        mc.player.posY + mc.player.getEyeHeight(),
                        mc.player.posZ
                ),
                new Vec3d(
                        entity.posX,
                        entity.posY + (entity.height / 2),
                        entity.posZ)
        ) == null;
    }

    private PlayerUtil() {
        throw new UnsupportedOperationException();
    }

    public static ItemStack getItemStackFromItem(Item item) {
        if (mc.player == null) return null;
        for (int slot = 0; slot <= 9; slot++) {
            if (mc.player.inventory.getStackInSlot(slot).getItem() == item)
                return mc.player.inventory.getStackInSlot(slot);
        }
        return null;
    }

    public static Item getBestItem(Block block) {
        String tool = block.getHarvestTool(block.getDefaultState());
        if (tool != null) {
            switch (tool) {
                case "axe":
                    return Items.DIAMOND_AXE;
                case "shovel":
                    return Items.DIAMOND_SHOVEL;
                default:
                    return Items.DIAMOND_PICKAXE;
            }
        } else {
            return Items.DIAMOND_PICKAXE;
        }

    }

    public static boolean isMoving(EntityLivingBase entity) {
        return entity.moveForward != 0 || entity.moveStrafing != 0;
    }

    public static void setSpeed(final EntityLivingBase entity, final double speed) {
        double[] dir = forward(speed);
        entity.motionX = dir[0];
        entity.motionZ = dir[1];
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    public static BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
    }

    public static double[] forward(final double speed) {
        float forward = mc.player.movementInput.moveForward;
        float side = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.prevRotationYaw + (mc.player.rotationYaw - mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        final double posX = forward * speed * cos + side * speed * sin;
        final double posZ = forward * speed * sin - side * speed * cos;
        return new double[]{posX, posZ};
    }
}
