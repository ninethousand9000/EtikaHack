package me.ninethousand.etikahack.impl.modules.visual;

import me.ninethousand.etikahack.api.command.Command;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.NumberSetting;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.math.MathUtil;
import me.ninethousand.etikahack.impl.modules.misc.VisualRange;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

@ModuleAnnotation(category = ModuleCategory.VISUAL)
public class Freecam extends Module {
    public static final Setting<Modes> mode = new Setting<>("Mode", Modes.Normal);
    public static final Setting<Boolean> noPackets = new Setting<>("PacketCancel", false);
    public static final NumberSetting<Integer> speed = new NumberSetting<>("Speed", 1, 5, 10, 1);

    public Freecam() {
        addSettings(mode, noPackets, speed);
    }

    private Entity riding;
    private EntityOtherPlayerMP Camera;
    private Vec3d position;
    private float yaw;
    private float pitch;


    private enum Modes {
        Normal,
        Camera,
    }
}
