package me.ninethousand.etikahack.impl.modules.movement;

import me.ninethousand.etikahack.api.event.events.MoveEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.util.game.EntityUtil;
import me.ninethousand.etikahack.api.util.game.PlayerUtil;
import me.ninethousand.etikahack.api.util.misc.Timer;
import me.ninethousand.etikahack.mixin.accessors.IEntity;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleAnnotation(category = ModuleCategory.MOVEMENT)
public class Strafe extends Module {
    public static final NumberSetting<Float> speed = new NumberSetting<>("Speed", 0.0f, 0.4f, 1.0f, 2);
    public static final NumberSetting<Float> timerSpeed = new NumberSetting<>("Timer", 1.0f, 1.15f, 1.5f, 2);

    public Strafe() {
        addSettings(speed, timerSpeed);
    }

    private boolean slowdown;
    private double playerSpeed;
    private final Timer timer = new Timer();

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        playerSpeed = getBaseMoveSpeed();
    }

    @Override
    public void onDisable() {
//        EntityUtil.resetTimer();
        timer.reset();
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) disable();
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (mc.player.isInLava() || mc.player.isInWater() || mc.player.isOnLadder() || ((IEntity)mc.player).getIsInWeb()) {
            return;
        }

        double speedY = speed.getValue();

        if (mc.player.onGround && PlayerUtil.isMoving(mc.player) && timer.passedMs(300)) {
//            EntityUtil.setTimer(timerSpeed.getValue().floatValue());
            if (mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                speedY += (mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
            }
            event.setY(mc.player.motionY = speedY);
            playerSpeed = PlayerUtil.getBaseMoveSpeed() * (EntityUtil.isColliding(0, -0.5, 0) instanceof BlockLiquid && !EntityUtil.isInLiquid() ? 0.9 : 1.901);
            slowdown = true;
            timer.reset();
        } else {
//            EntityUtil.resetTimer();
            if (slowdown || mc.player.collidedHorizontally) {
                playerSpeed -= (EntityUtil.isColliding(0, -0.8, 0) instanceof BlockLiquid && !EntityUtil.isInLiquid()) ? 0.4 : 0.7 * (playerSpeed = PlayerUtil.getBaseMoveSpeed());
                slowdown = false;
            } else {
                playerSpeed -= playerSpeed / 159.0;
            }
        }
        playerSpeed = Math.max(playerSpeed, PlayerUtil.getBaseMoveSpeed());
        double[] dir = PlayerUtil.forward(playerSpeed);
        event.setX(dir[0]);
        event.setZ(dir[1]);
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player != null && mc.player.isPotionActive(Potion.getPotionById(1))) {
            final int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
