package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.impl.modules.visual.ViewModel;
import me.ninethousand.etikahack.mixin.accessors.IItemRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer implements IItemRenderer {
    @Shadow
    @Final
    private RenderItem itemRenderer;

    @Shadow
    private float equippedProgressMainHand;

    @Shadow
    private float prevEquippedProgressMainHand;

    @Shadow
    private ItemStack itemStackMainHand;

    @Inject(method = "renderItemSide", at = @At("HEAD"))
    public void renderItemSide(EntityLivingBase entitylivingbaseIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform, boolean leftHanded, CallbackInfo ci) {
        if (ModuleManager.getModule(ViewModel.class).isEnabled()) {
            GlStateManager.scale(ViewModel.scaleX.getValue() - 1, ViewModel.scaleY.getValue() - 1, ViewModel.scaleZ.getValue() - 1);
            if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND) {
                GlStateManager.translate(ViewModel.translateX.getValue() - 1, ViewModel.translateY.getValue() - 1, ViewModel.translateZ.getValue() - 1);
                GlStateManager.rotate(ViewModel.rotateX.getValue(), 1, 0, 0);
                GlStateManager.rotate(ViewModel.rotateY.getValue(), 0, 1, 0);
                GlStateManager.rotate(ViewModel.rotateZ.getValue(), 0, 0, 1);
            } else if (transform == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
                GlStateManager.translate(-(ViewModel.translateX.getValue() - 1), ViewModel.translateY.getValue() - 1, ViewModel.translateZ.getValue() - 1);
                GlStateManager.rotate(-ViewModel.rotateX.getValue(), 1, 0, 0);
                GlStateManager.rotate(ViewModel.rotateY.getValue(), 0, 1, 0);
                GlStateManager.rotate(ViewModel.rotateZ.getValue(), 0, 0, 1);
            }
        }
    }

    @Override
    public RenderItem getRenderItem() {
        return itemRenderer;
    }

    @Override
    public float getPrevEquippedProgressMainHand() {
        return prevEquippedProgressMainHand;
    }

    @Override
    public void setEquippedProgressMainHand(float equippedProgressMainHand) {
        this.equippedProgressMainHand = equippedProgressMainHand;
    }

    @Override
    public void setItemStackMainHand(ItemStack itemStackMainHand) {
        this.itemStackMainHand = itemStackMainHand;
    }
}
