package me.ninethousand.fate.impl.modules.visual;

import com.mojang.authlib.GameProfile;
import me.ninethousand.fate.Fate;
import me.ninethousand.fate.api.event.events.RenderEvent3d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.mixin.accessors.IRenderManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityPig;

import java.util.UUID;

@ModuleAnnotation(category = ModuleCategory.Visual)
public class PopEsp extends Module {
    @Override
    public void onEnable() {
        Entity pig = new EntityPig(mc.world);

        pig.copyLocationAndAnglesFrom(mc.player);

        mc.world.addEntityToWorld(1010, pig);
//        mc.getRenderManager().renderEntity(pig, mc.player.posX, mc.player.posY, mc.player.posZ, mc.player.rotationYaw, 1.0f, true);
        Fate.log("Rendering zhu");
    }
}
