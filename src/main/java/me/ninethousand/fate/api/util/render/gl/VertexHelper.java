package me.ninethousand.fate.api.util.render.gl;

import me.ninethousand.fate.api.util.math.Vec2d;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class VertexHelper {
    private Tessellator tessellator = Tessellator.getInstance();
    private BufferBuilder bufferBuilder = tessellator.getBuffer();

    private boolean useBuffer;

    public VertexHelper(boolean useBuffer) {
        this.useBuffer = useBuffer;
    }

    public void begin(int mode) {
        if (useBuffer) {
            bufferBuilder.begin(mode, DefaultVertexFormats.POSITION_COLOR);
        } else {
            GL11.glBegin(mode);
        }
    }

    public void put(Vec2d pos, Color color) {
        put(pos.x, pos.y, 0, color);
    }

    public void put(double x, double y, double z, Color color) {
        put(new Vec3d(x, y, z), color);
    }

    public void put(Vec3d pos, Color color) {
        if (useBuffer) {
            bufferBuilder.pos(pos.x, pos.y, pos.z).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        } else {
            GL11.glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
            GL11.glVertex3d(pos.x, pos.y, pos.z);
        }
    }

    public void end() {
        if (useBuffer) {
            tessellator.draw();
        } else {
            GL11.glEnd();
        }
    }
}
