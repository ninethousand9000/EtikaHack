package me.ninethousand.fate.api.util.misc.popghost;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.world.World;

public class EntityPopGhost extends EntityOtherPlayerMP {
    public float alpha;

    public EntityPopGhost(World worldIn, GameProfile gameProfileIn) {
        super(worldIn, gameProfileIn);
        this.alpha = 255;
    }
}
