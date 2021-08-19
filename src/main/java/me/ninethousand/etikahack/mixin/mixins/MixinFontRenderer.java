package me.ninethousand.etikahack.mixin.mixins;

import me.ninethousand.etikahack.api.module.ModuleManager;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.impl.modules.client.ClientFont;
import me.ninethousand.etikahack.impl.modules.misc.Media;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At(value = "HEAD"), cancellable = true)
    public void onDrawStringHead(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
        if (mc.world != null && ClientFont.overrideMinecraft.getValue() && ModuleManager.getModule(ClientFont.class).isEnabled()) cir.setReturnValue((int) FontUtil.drawText(text, x, y, color));
    }

    @Inject(method = "getStringWidth", at = @At("HEAD"), cancellable = true)
    public void onGetStringWidthInvoke(String text, CallbackInfoReturnable<Integer> cir) {
        if (mc.world != null && ClientFont.overrideMinecraft.getValue() && ModuleManager.getModule(ClientFont.class).isEnabled()) cir.setReturnValue((int) FontUtil.getStringWidth(text));
    }

    @Redirect(method = {"renderString(Ljava/lang/String;FFIZ)I"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
    public void renderStringAtPosHook(FontRenderer renderer, String text, boolean shadow) {
        try {
            if (ModuleManager.getModule(Media.class).isEnabled()) {
                this.renderStringAtPos(text.replace(Minecraft.getMinecraft().getSession().getUsername(), Media.name.getValue()), shadow);
                return;
            }
        }

        catch (Exception e) {}

        this.renderStringAtPos(text, shadow);
    }

    @Shadow
    protected abstract void renderStringAtPos(String var1, boolean var2);
}
