package me.ninethousand.etikahack.impl.modules.misc;

import com.mojang.authlib.GameProfile;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;

import java.util.UUID;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class FakePlayer extends Module {
    public static final Setting<String> name = new Setting<>("Name", "9k");

    public FakePlayer() {
        addSettings(name);
    }

    private EntityOtherPlayerMP fakePlayer;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("a9ebe2ab-3ba9-45c6-8708-3e29ba35758e"), name.getValue()));
        fakePlayer.copyLocationAndAnglesFrom(mc.player);
        mc.world.addEntityToWorld(-100, fakePlayer);
    }

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        try {
            fakePlayer.setHealth(mc.player.getHealth());
            fakePlayer.inventory = mc.player.inventory;
        }

        catch (Exception ignored) {}
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        try {
            mc.world.removeEntity(fakePlayer);
        } catch (Exception ignored) {}
    }
}
