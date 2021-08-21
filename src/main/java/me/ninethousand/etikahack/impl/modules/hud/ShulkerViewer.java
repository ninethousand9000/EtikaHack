package me.ninethousand.etikahack.impl.modules.hud;

import me.ninethousand.etikahack.api.event.events.HudRenderEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.util.misc.Timer;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.api.util.render.graphics.GraphicsUtil2d;
import me.ninethousand.etikahack.mixin.accessors.IItemRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class ShulkerViewer extends Module {
    private static final ResourceLocation SHULKER_GUI_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");
    public Map<EntityPlayer, ItemStack> spiedPlayers = new ConcurrentHashMap<EntityPlayer, ItemStack>();
    public Map<EntityPlayer, Timer> playerTimers = new ConcurrentHashMap<EntityPlayer, Timer>();

    @Override
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == null || !(player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox) || mc.player == player)
                continue;
            ItemStack stack = player.getHeldItemMainhand();
            spiedPlayers.put(player, stack);
        }
    }

    @Override
    public void onHudRender(HudRenderEvent event, VertexHelper vertexHelper) {
        if (nullCheck()) {
            return;
        }

        int x = -3;
        int y = 124;

        for (EntityPlayer player : mc.world.playerEntities) {
            Timer playerTimer;
            if (spiedPlayers.get(player) == null) continue;
            if (player.getHeldItemMainhand() == null || !(player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox)) {
                playerTimer = playerTimers.get(player);
                if (playerTimer == null) {
                    Timer timer = new Timer();
                    timer.reset();
                    playerTimers.put(player, timer);
                } else if (playerTimer.passedS(3.0)) {
                    continue;
                }
            } else if (player.getHeldItemMainhand().getItem() instanceof ItemShulkerBox && (playerTimer = playerTimers.get(player)) != null) {
                playerTimer.reset();
                playerTimers.put(player, playerTimer);
            }
            ItemStack stack = spiedPlayers.get(player);
            renderShulkerToolTip(stack, x, y, player.getName());
        }
    }

    public void renderShulkerToolTip(ItemStack stack, int x, int y, String name) {
        NBTTagCompound blockEntityTag;
        NBTTagCompound tagCompound = stack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("BlockEntityTag", 10) && (blockEntityTag = tagCompound.getCompoundTag("BlockEntityTag")).hasKey("Items", 9)) {
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            mc.getTextureManager().bindTexture(SHULKER_GUI_TEXTURE);
            drawTexturedRect(x, y, 0, 0, 176, 16, 500);
            drawTexturedRect(x, y + 16, 0, 16, 176, 57, 500);
            drawTexturedRect(x, y + 16 + 54, 0, 160, 176, 8, 500);
            GlStateManager.disableDepth();
            FontUtil.drawTextHUD(name == null ? stack.getDisplayName() : name, x + 8, y + 6);
            GlStateManager.enableDepth();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableRescaleNormal();
            GlStateManager.enableColorMaterial();
            GlStateManager.enableLighting();
            NonNullList nonnulllist = NonNullList.withSize(27, (Object) ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(blockEntityTag, nonnulllist);
            for (int i = 0; i < nonnulllist.size(); ++i) {
                int iX = x + i % 9 * 18 + 8;
                int iY = y + i / 9 * 18 + 18;
                ItemStack itemStack = (ItemStack) nonnulllist.get(i);
                ((IItemRenderer)mc.getItemRenderer()).getRenderItem().zLevel = 501.0f;
                GraphicsUtil2d.drawItem(itemStack, iX, iY, itemStack.getCount(), true);
                ((IItemRenderer)mc.getItemRenderer()).getRenderItem().zLevel = 0.0f;
            }
            GlStateManager.disableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void drawTexturedRect(final int x, final int y, final int textureX, final int textureY, final int width, final int height, final int zLevel) {
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder BufferBuilder2 = tessellator.getBuffer();
        BufferBuilder2.begin(7, DefaultVertexFormats.POSITION_TEX);
        BufferBuilder2.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex((double)((textureX + 0) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + height), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + height) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex((double)((textureX + width) * 0.00390625f), (double)((textureY + 0) * 0.00390625f)).endVertex();
        BufferBuilder2.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex((double)((textureX + 0) * 0.00390625f), (double)((textureY + 0) * 0.00390625f)).endVertex();
        tessellator.draw();
    }
}
