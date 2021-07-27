package me.ninethousand.fate.impl.modules.misc;

import com.mojang.authlib.GameProfile;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

import java.util.UUID;

@ModuleAnnotation(category = ModuleCategory.Misc)
public class FakePlayer extends Module {
    private EntityOtherPlayerMP fakePlayer;

    @Override
    public void onEnable() {
        fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a9ebe2ab-3ba9-45c6-8708-3e29ba35758e"), "II Incel"));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        mc.world.addEntityToWorld(-100, fakePlayer);
    }

    @Override
    public void onUpdate() {
        fakePlayer.setHealth(mc.player.getHealth());
        fakePlayer.rotationYawHead = mc.player.rotationYawHead;
        fakePlayer.inventory = mc.player.inventory;
    }

    @Override
    public void onDisable() {
        try {
            mc.world.removeEntity(fakePlayer);
        } catch (Exception ignored) {}
    }
}
