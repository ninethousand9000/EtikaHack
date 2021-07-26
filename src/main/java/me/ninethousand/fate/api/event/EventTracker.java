package me.ninethousand.fate.api.event;

import me.ninethousand.fate.api.event.events.RenderEvent2d;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventTracker {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                for (Module module : ModuleManager.getModules()) {
                    if (module.getKey() == Keyboard.getEventKey()) {
                        module.toggle();
                    }
                }
                try {
                    MinecraftForge.EVENT_BUS.register(event);
                } catch (Exception ignored) {}
            }
        }
    }

    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (nullCheck()) return;
        for (Module module : ModuleManager.getModules())
            if (module.isEnabled()) module.onUpdate();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck()) return;
        for (Module module : ModuleManager.getModules())
            if (module.isEnabled()) module.onTick();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        ScaledResolution resolution = new ScaledResolution(mc);
        RenderEvent2d renderEvent2D = new RenderEvent2d(event.getPartialTicks(), resolution);
        ModuleManager.getModules().stream().forEach(module -> {
            if (module.isEnabled()) module.onHudRender(renderEvent2D);
        });
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
