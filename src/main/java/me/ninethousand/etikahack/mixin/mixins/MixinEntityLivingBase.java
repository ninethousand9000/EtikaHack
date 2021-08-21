package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.impl.modules.visual.Swing;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase {
    @Overwrite
    private int getArmSwingAnimationEnd() {
        if (this.isPotionActive(MobEffects.HASTE)) {
            return 6 - (1 + this.getActivePotionEffect(MobEffects.HASTE).getAmplifier());
        }

        else if (ModuleManager.getModule(Swing.class).isEnabled()) {
            return 6 + (1 + Swing.factor.getValue()) * 2;
        }

        else
        {
            return this.isPotionActive(MobEffects.MINING_FATIGUE) ? 6 + (1 + this.getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) * 2 : 6;
        }
    }

    @Shadow
    public abstract boolean isPotionActive(Potion potionIn);

    @Shadow
    public abstract PotionEffect getActivePotionEffect(Potion potionIn);
}
