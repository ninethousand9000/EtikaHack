package me.ninethousand.fate.api.util.render.graphics;

import me.ninethousand.fate.api.util.math.Pair;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class GraphicsUtil2d {
    public static void drawRoundedQuadOutline(VertexHelper vertexHelper, Vec2d topVertex, Vec2d bottomVertex, double radius, int segments, float lineWidth, Color color) {
        Vec2d vertex1 = new Vec2d(bottomVertex.x, topVertex.y);
        Vec2d vertex2 = new Vec2d(topVertex.x, bottomVertex.y);

        drawArcOutline(vertexHelper, topVertex.plus(radius), radius, new Pair(-90f, 0f), segments, lineWidth, color); // Top left
        drawArcOutline(vertexHelper, vertex1.plus(-radius, radius), radius, new Pair(0f, 90f), segments, lineWidth, color);// Top right
        drawArcOutline(vertexHelper, bottomVertex.minus(radius), radius, new Pair(90f, 180f), segments, lineWidth, color); // Bottom right
        drawArcOutline(vertexHelper, vertex2.plus(radius, -radius), radius, new Pair(180f, 270f), segments, lineWidth, color); // Bottom left

        drawLine(vertexHelper, topVertex.plus(radius, 0.0), vertex1.minus(radius, 0.0), lineWidth, color); // Top
        drawLine(vertexHelper, topVertex.plus(0.0, radius), vertex2.minus(0.0, radius), lineWidth, color); // Left
        drawLine(vertexHelper, vertex1.plus(0.0, radius), bottomVertex.minus(0.0, radius), lineWidth, color); // Right
        drawLine(vertexHelper, vertex2.plus(radius, 0.0), bottomVertex.minus(radius, 0.0), lineWidth, color); // Bottom
    }

    private static void drawArcOutline(VertexHelper vertexHelper, Vec2d center, double radius, Pair<Float, Float> angleRange, int segments, float lineWidth, Color color) {
        drawLineStrip(vertexHelper, getArcVertices(center, radius, angleRange, segments), lineWidth, color);
    }

    private static void drawLineStrip(VertexHelper vertexHelper, ArrayList<Vec2d> vertices, float lineWidth, Color color) {
        prepareGl();
        GL11.glLineWidth(lineWidth);

        vertexHelper.begin(GL11.GL_LINE_STRIP);
        for (Vec2d vertex : vertices) {
            vertexHelper.put(vertex, color);
        }
        vertexHelper.end();

        releaseGl();
        GL11.glLineWidth(1f);
    }

    private static void drawLine(VertexHelper vertexHelper, Vec2d begin, Vec2d end, float lineWidth, Color color) {
        prepareGl();
        GL11.glLineWidth(lineWidth);

        vertexHelper.begin(GL11.GL_LINES);
        vertexHelper.put(begin, color);
        vertexHelper.put(end, color);
        vertexHelper.end();

        releaseGl();
        GL11.glLineWidth(1f);
    }

    private static ArrayList<Vec2d> getArcVertices(Vec2d center, double radius, Pair<Float, Float> angleRange, int segments) {
        double range = Math.max(angleRange.first, angleRange.last) - Math.min(angleRange.first, angleRange.last);
        int seg = calculateSegments(segments, radius, range);
        double segAngle = range / seg;

        ArrayList<Vec2d> vec2ds = new ArrayList<>();

        for (int i = 0; i < seg; i++) {
            double angle = Math.toRadians(i * segAngle + angleRange.first);
            vec2ds.add(new Vec2d(Math.sin(angle), -Math.cos(angle)).times(radius).plus(center));
        }

        return vec2ds;
    }

    private static int calculateSegments(int segmentsIn, double radius, double range) {
        if (segmentsIn != -0) return segmentsIn;
        double segments = radius * 0.5 * Math.PI * (range / 360);
        return Math.round(Math.max(Math.round(segments), 16));
    }

    private static void prepareGl() {
        GlStateManager.disableAlpha();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GlStateManager.disableCull();
    }

    private static void releaseGl() {
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.enableCull();
    }
}
