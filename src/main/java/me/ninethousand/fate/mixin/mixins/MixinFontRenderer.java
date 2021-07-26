package me.ninethousand.fate.mixin.mixins;

import me.ninethousand.fate.api.module.ModuleManager;
import me.ninethousand.fate.api.util.render.font.CFontRenderer;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.impl.modules.client.ClientFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(FontRenderer.class)
public final class MixinFontRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At(value = "HEAD"), cancellable = true)
    public void onDrawStringHead(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        if (mc.world != null && ClientFont.overrideMinecraft.getValue() && ModuleManager.getModule(ClientFont.class).isEnabled()) cir.setReturnValue(FontUtil.drawText(text, x, y, color));
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
    public void onGetStringWidthInvoke(String text, CallbackInfoReturnable<Integer> cir) {
        if (mc.world != null && ClientFont.overrideMinecraft.getValue() && ModuleManager.getModule(ClientFont.class).isEnabled()) cir.setReturnValue((int) FontUtil.getStringWidth(text));
    }
}
