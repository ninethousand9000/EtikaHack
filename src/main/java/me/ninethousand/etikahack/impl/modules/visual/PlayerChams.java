package me.ninethousand.etikahack.impl.modules.visual;

import com.mojang.authlib.GameProfile;
import me.ninethousand.etikahack.api.event.events.WorldRenderEvent;
import me.ninethousand.etikahack.api.event.events.TotemPopEvent;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.math.Pair;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class PlayerChams extends Module {
    public static final Setting<Boolean> players = new Setting<>("Players", true);
    public static final Setting<Boolean> playerModel = new Setting<>(players, "RenderPlayerModel", true);
    public static final Setting<Boolean> simpleModel = new Setting<>(players, "BasicModel", true);
    public static final Setting<Boolean> customTexture = new Setting<>(players, "Texture", true);
    public static final NumberSetting<Float> textureAlpha = new NumberSetting<>(players,"TextureAlpha", 0f, 150f, 255f, 1);
    public static final Setting<Boolean> walls = new Setting<>(players, "Depth", true);
    public static final Setting<Boolean> middleWall = new Setting<>(walls, "ModelThroughWalls", true);
    public static final Setting<Boolean> lineWall = new Setting<>(walls, "LineThroughWalls", true);
    public static final Setting<ChamMode> playerMode = new Setting<>(players, "PlayerMode", ChamMode.Fill);
    public static final Setting<Boolean> playerColors = new Setting<>(players, "PlayerColors", true);
    public static final Setting<Color> playerColor = new Setting<>(playerColors,"PlayerColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> playerColorO = new Setting<>(playerColors,"PlayerColorO", new Color(0xB3FFFFFF, true));
    /*public static final Setting<Color> playerfriendColor = new Setting<>(playerColors,"FriendColor", new Color(0xB300FFE3, true));
    public static final Setting<Color> playerfriendColorO = new Setting<>(playerColors,"FriendColorO", new Color(0xB300FFE3, true));*/
    public static final NumberSetting<Float> playerOutlineWidth = new NumberSetting<>(players,"PlayerOutlineWidth", 0.1f, 1.0f, 5.0f, 1);

    public static final Setting<Boolean> pops = new Setting<>("Pops", true);
    public static final Setting<ChamModePop> popMode = new Setting<>(pops, "PopMode", ChamModePop.Fill);
    public static final Setting<Boolean> popColors = new Setting<>(pops, "PopColors", true);
    public static final Setting<Color> popColor = new Setting<>(popColors,"PopColor", new Color(0xB3FFFFFF, true));
    public static final Setting<Color> popColorO = new Setting<>(popColors,"PopColorO", new Color(0xB3FFFFFF, true));
    /*public static final Setting<Color> friendPopColor = new Setting<>(popColors,"FriendColor", new Color(0xB300FFE3, true));
    public static final Setting<Color> friendPopColorO = new Setting<>(popColors,"FriendColorO", new Color(0xB300FFE3, true));
    public static final Setting<Color> selfPopColor = new Setting<>(popColors,"SelfPopColor", new Color(0xA8FF0000, true));
    public static final Setting<Color> selfPopColorO = new Setting<>(popColors,"SelfPopColorO", new Color(0xA8FF0000, true));*/
    public static final NumberSetting<Float> popOutlineWidth = new NumberSetting<>(pops,"PopOutlineWidth", 0.1f, 1.0f, 5.0f, 1);
    public static final NumberSetting<Float> popFadeSpeed = new NumberSetting<>(pops,"FadeSpeed", 0.1f, 2.0f, 10.0f, 1);
    public static final Setting<Boolean> multipleGhosts = new Setting<>(pops, "MultiGhost", false);

    public PlayerChams() {
        addSettings(
                players,
                pops
        );
    }

    private static ArrayList<Pair<EntityOtherPlayerMP, Float>> playersToRender = new ArrayList<>();
    private static ArrayList<Pair<EntityOtherPlayerMP, Float>> playersToDelete = new ArrayList<>();

    @Override
    public void onWorldRender(WorldRenderEvent event3d) {
        if (nullCheck()) return;

        try {
            playersToRender.forEach(pair -> {
                mc.getRenderManager().renderEntity(pair.first,
                        pair.first.posX - mc.getRenderManager().viewerPosX,
                        pair.first.posY - mc.getRenderManager().viewerPosY,
                        pair.first.posZ - mc.getRenderManager().viewerPosZ,
                        pair.first.rotationYaw,
                        1.0f, false);

                if (popFadeSpeed.getValue() <= pair.last && pair.last >= 0) pair.last -= popFadeSpeed.getValue();

                else playersToDelete.add(pair);
            });

            playersToDelete.clear();
        }

        catch (Exception ignored) {}
    }

    @SubscribeEvent
    public void onTotemPop(TotemPopEvent event) {
        EntityOtherPlayerMP popGhost = new EntityOtherPlayerMP(mc.world, new GameProfile(UUID.fromString("e4ea5edb-e317-433f-ac90-ef304841d8c8"), "人"));
        popGhost.copyLocationAndAnglesFrom(event.getPlayer());

        if (!multipleGhosts.getValue()) playersToRender.clear();

        playersToRender.add(new Pair<>(popGhost, (float) popColor.getValue().getAlpha()));
    }

    public static float getAlpha(Entity entity) {
        for (Pair<EntityOtherPlayerMP, Float> pair : playersToRender) {
            if (entity == pair.first) {
                return (float) pair.last;
            }
        }
        return 0;
    }

    public enum ChamMode {
        Fill,
        Wireframe,
        Pretty,
        Normal
    }

    public enum ChamModePop {
        Fill,
        Wireframe,
        Pretty
    }
}

