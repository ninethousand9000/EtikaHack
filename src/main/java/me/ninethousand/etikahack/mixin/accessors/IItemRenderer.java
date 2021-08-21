package me.ninethousand.etikahack.mixin.accessors;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface IItemRenderer {
    @Accessor("itemRenderer")
    RenderItem getRenderItem();

    @Accessor("prevEquippedProgressMainHand")
    float getPrevEquippedProgressMainHand();

    @Accessor("equippedProgressMainHand")
    void setEquippedProgressMainHand(float equippedProgressMainHand);

    @Accessor("itemStackMainHand")
    void setItemStackMainHand(ItemStack itemStackMainHand);
}
