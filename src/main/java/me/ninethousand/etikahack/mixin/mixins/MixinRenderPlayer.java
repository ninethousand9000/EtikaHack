package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.impl.modules.visual.EtikaMode;
import me.ninethousand.etikahack.impl.modules.visual.PlayerChams;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value={RenderPlayer.class})
public abstract class MixinRenderPlayer {
    @Overwrite
    public ResourceLocation getEntityTexture(final AbstractClientPlayer entity) {
        if (ModuleManager.getModule(EtikaMode.class).isEnabled()) {
            GL11.glColor4f(1f, 1f, 1f, 1f);
            return new ResourceLocation("textures/fate/skins/etikafun.png");
        }
        return entity.getLocationSkin();
    }

    @Overwrite
    private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
        ModelPlayer modelplayer = this.getMainModel();

        if (clientPlayer.isSpectator())
        {
            modelplayer.setVisible(false);
            modelplayer.bipedHead.showModel = true;
            modelplayer.bipedHeadwear.showModel = true;
        }
        else
        {
            ItemStack itemstack = clientPlayer.getHeldItemMainhand();
            ItemStack itemstack1 = clientPlayer.getHeldItemOffhand();
            modelplayer.setVisible(true);
            if (PlayerChams.simpleModel.getValue()) {
                modelplayer.bipedHeadwear.showModel = false;
                modelplayer.bipedBodyWear.showModel = false;
                modelplayer.bipedLeftLegwear.showModel = false;
                modelplayer.bipedRightLegwear.showModel = false;
                modelplayer.bipedLeftArmwear.showModel = false;
                modelplayer.bipedRightArmwear.showModel = false;
            }
            else {
                modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
                modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
                modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
                modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
                modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
                modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
            }

            modelplayer.isSneak = clientPlayer.isSneaking();
            ModelBiped.ArmPose modelbiped$armpose = ModelBiped.ArmPose.EMPTY;
            ModelBiped.ArmPose modelbiped$armpose1 = ModelBiped.ArmPose.EMPTY;

            if (!itemstack.isEmpty())
            {
                modelbiped$armpose = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0)
                {
                    EnumAction enumaction = itemstack.getItemUseAction();

                    if (enumaction == EnumAction.BLOCK)
                    {
                        modelbiped$armpose = ModelBiped.ArmPose.BLOCK;
                    }
                    else if (enumaction == EnumAction.BOW)
                    {
                        modelbiped$armpose = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }

            if (!itemstack1.isEmpty())
            {
                modelbiped$armpose1 = ModelBiped.ArmPose.ITEM;

                if (clientPlayer.getItemInUseCount() > 0)
                {
                    EnumAction enumaction1 = itemstack1.getItemUseAction();

                    if (enumaction1 == EnumAction.BLOCK)
                    {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BLOCK;
                    }
                    // FORGE: fix MC-88356 allow offhand to use bow and arrow animation
                    else if (enumaction1 == EnumAction.BOW)
                    {
                        modelbiped$armpose1 = ModelBiped.ArmPose.BOW_AND_ARROW;
                    }
                }
            }

            if (clientPlayer.getPrimaryHand() == EnumHandSide.RIGHT)
            {
                modelplayer.rightArmPose = modelbiped$armpose;
                modelplayer.leftArmPose = modelbiped$armpose1;
            }
            else
            {
                modelplayer.rightArmPose = modelbiped$armpose1;
                modelplayer.leftArmPose = modelbiped$armpose;
            }
        }
    }

    @Shadow
    public abstract ModelPlayer getMainModel();

}
