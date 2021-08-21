package me.ninethousand.etikahack.impl.modules.misc;

import com.sun.org.apache.xpath.internal.operations.Mod;
import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import net.minecraft.entity.player.EntityPlayer;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class DebugTools extends Module {
    public static final Setting<Boolean> mcRenderPosPlayer = new Setting<>("RenderPos", true);
    public static final NumberSetting<Integer> ticksDelay = new NumberSetting<>("Delay", 0, 10, 100, 1);

    public DebugTools() {
        addSettings(
                mcRenderPosPlayer,
                ticksDelay
        );
    }

    private int ticksPassed = 0;

    @Override
    public void onTick() {
        if (ticksPassed == ticksDelay.getValue()) {
            if (mcRenderPosPlayer.getValue()) {
                mc.world.getLoadedEntityList()
                        .stream()
                        .filter(entity -> entity instanceof EntityPlayer)
                        .forEach(entityPlayer -> Command.sendClientMessageDefault("POS:" + (mc.player.posX - mc.getRenderManager().viewerPosX) + " " + (mc.player.posZ - mc.getRenderManager().viewerPosY) + " " + (mc.player.posZ - mc.getRenderManager().viewerPosZ)));
            }

            ticksPassed = 0;
        }

        else {
            ticksPassed++;
        }
    }
}
