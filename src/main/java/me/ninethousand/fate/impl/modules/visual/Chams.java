package me.ninethousand.fate.impl.modules.visual;

import com.mojang.authlib.GameProfile;
import me.ninethousand.fate.api.event.events.RenderEvent3d;
import me.ninethousand.fate.api.event.events.TotemPopEvent;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.NumberSetting;
import me.ninethousand.fate.api.settings.Setting;
import me.ninethousand.fate.api.util.misc.popghost.EntityPopGhost;
import me.ninethousand.fate.api.util.misc.popghost.PopGhost;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;
import java.util.UUID;

@ModuleAnnotation(category = ModuleCategory.Visual)
public class Chams extends Module {
    public static final Setting<Boolean> players = new Setting<>("Players", true);
    public static final Setting<Boolean> playerModel = new Setting<>(players, "PlayerModel", true);
    public static final Setting<ChamMode> playerMode = new Setting<>(players, "PlayerMode", ChamMode.Fill);
    public static final Setting<Color> playerColor = new Setting<>(players,"PlayerColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> playerfriendColor = new Setting<>(players,"FriendColor", new Color(0xB300FFE3, true));
    public static final NumberSetting<Float> playerOutlineWidth = new NumberSetting<>(players,"PlayerOutlineWidth", 0.1f, 1.0f, 5.0f, 1);

    public static final Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static final Setting<Boolean> crystalModel = new Setting<>(crystals, "CrystalModel", true);
    public static final Setting<ChamMode> crystalMode = new Setting<>(crystals, "CrystalMode", ChamMode.Fill);
    public static final Setting<Color> crystalColor = new Setting<>(crystals,"CrystalColor", new Color(0xB3FFFFFF, true));
    public static final NumberSetting<Float> crystalOutlineWidth = new NumberSetting<>(crystals,"CrystalOutlineWidth", 0.1f, 1.0f, 5.0f, 1);

    public static final Setting<Boolean> pops = new Setting<>("Pops", true);
    public static final Setting<ChamMode> popMode = new Setting<>(pops, "PopMode", ChamMode.Fill);
    public static final Setting<Color> popColor = new Setting<>(pops,"PopColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> friendPopColor = new Setting<>(pops,"PopColor", new Color(0xB300FFE3, true));
    public static final Setting<Color> selfPopColor = new Setting<>(pops,"SelfPopColor", new Color(0xA8FF0000, true));
    public static final NumberSetting<Float> popOutlineWidth = new NumberSetting<>(pops,"PopOutlineWidth", 0.1f, 1.0f, 5.0f, 1);
    public static final NumberSetting<Float> popFadeSpeed = new NumberSetting<>(pops,"FadeSpeed", 0.1f, 0.7f, 2.0f, 2);

    public Chams() {
        addSettings(
                players,
                crystals,
                pops
        );
    }

    private static ArrayList<EntityPopGhost> playersToRender = new ArrayList<>();
    private static ArrayList<EntityPopGhost> playersToDelete = new ArrayList<>();

    @Override
    public void onWorldRender(RenderEvent3d event3d) {
        playersToDelete.clear();

        try {
            for (EntityPopGhost ghost : playersToRender) {
                if (ghost.alpha - popFadeSpeed.getValue() > 0) {
                    ghost.alpha -= popFadeSpeed.getValue();
                    mc.getRenderManager().renderEntity(ghost, ghost.posX - mc.getRenderManager().viewerPosX, ghost.posY - mc.getRenderManager().viewerPosY, ghost.posZ - mc.getRenderManager().viewerPosZ, ghost.rotationYawHead, 1.0f, false);
                }
                else {
                    playersToDelete.add(ghost);
                }
            }

            for (EntityPopGhost ghost : playersToDelete) {
                playersToRender.remove(ghost);
            }
        }

        catch (ConcurrentModificationException fuckOffCunt) {
            // bastard
        }
    }

    @SubscribeEvent
    public void onTotemPop(TotemPopEvent event) {
        EntityPopGhost ghostyBoi = new EntityPopGhost(mc.world, new GameProfile(UUID.fromString("e4ea5edb-e317-433f-ac90-ef304841d8c8"), event.getName()));
        ghostyBoi.copyLocationAndAnglesFrom(event.getPlayer());
        playersToRender.add(ghostyBoi);
    }

    public enum ChamMode {
        Normal,
        Fill,
        Wireframe,
        Pretty
    }
}
