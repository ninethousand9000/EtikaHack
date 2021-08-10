package me.ninethousand.etikahack.api.util.render.gl;

import me.ninethousand.etikahack.api.util.math.Quad;
import me.ninethousand.etikahack.api.util.math.Vec2d;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class GlScissorHelper {
    private static Minecraft mc = Minecraft.getMinecraft();

    public static ArrayList<Quad> scissorList = new ArrayList<>();
    public static Quad lastScissor = null;

    public static void scissor(double x, double y, double width, double height) {
        lastScissor = new Quad(new Vec2d(x, y), new Vec2d(width, height));
        GL11.glScissor((int) x, (int) y, (int) width, (int) height);
    }

    public static void enable() {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    public static void disable() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
