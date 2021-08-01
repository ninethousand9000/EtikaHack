package me.ninethousand.fate.api.util.render.graphics;

import me.ninethousand.fate.api.util.math.Pair;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.render.font.FontUtil;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class GraphicsUtil2d {
    private static final Minecraft mc = Minecraft.getMinecraft();
    public static final VertexHelper vertexHelperUB = new VertexHelper(true);
    public static final VertexHelper vertexHelperNB = new VertexHelper(false);

    public static void drawQuadFill(VertexHelper vertexHelper, Vec2d topLeft, Vec2d topRight, Vec2d bottomLeft, Vec2d bottomRight, Color color) {
        prepareGl();

        vertexHelper.begin(GL11.GL_QUADS);
        vertexHelper.put(topLeft, color);
        vertexHelper.put(topRight, color);
        vertexHelper.put(bottomLeft, color);
        vertexHelper.put(bottomRight, color);
        vertexHelper.end();

        releaseGl();
    }

    public static void drawRectFill(VertexHelper vertexHelper, Vec2d topLeft, Vec2d bottomRight, Color color) {
        Vec2d topRight = new Vec2d(bottomRight.x, topLeft.y);
        Vec2d bottomLeft = new Vec2d(topLeft.x, bottomRight.y);

        prepareGl();

        vertexHelper.begin(GL11.GL_QUAD_STRIP);
        vertexHelper.put(topLeft, color);
        vertexHelper.put(topRight, color);
        vertexHelper.put(bottomLeft, color);
        vertexHelper.put(bottomRight, color);
        vertexHelper.end();

        releaseGl();
    }

    public static void drawRectGradient(VertexHelper vertexHelper, Vec2d topLeft, Vec2d bottomRight, Color startColor, Color endColor, boolean horizontal) {
        Vec2d topRight = new Vec2d(bottomRight.x, topLeft.y);
        Vec2d bottomLeft = new Vec2d(topLeft.x, bottomRight.y);

        prepareGl();

        if (horizontal) {
            vertexHelper.begin(GL11.GL_QUAD_STRIP);
            vertexHelper.put(topLeft, startColor);
            vertexHelper.put(bottomLeft, startColor);
            vertexHelper.put(topRight, endColor);
            vertexHelper.put(bottomRight, endColor);
            vertexHelper.end();
        }

        else {
            vertexHelper.begin(GL11.GL_QUAD_STRIP);
            vertexHelper.put(topLeft, startColor);
            vertexHelper.put(topRight, startColor);
            vertexHelper.put(bottomLeft, endColor);
            vertexHelper.put(bottomRight, endColor);
            vertexHelper.end();
        }

        releaseGl();
    }

    public static void drawRoundedRectangleFilled(VertexHelper vertexHelper, Vec2d top, Vec2d bottom, double radius, int segments, Color color) {
        Vec2d vertex1 = new Vec2d(bottom.x, top.y);
        Vec2d vertex2 = new Vec2d(top.x, bottom.y);

        drawArcFilled(vertexHelper, top.plus(radius), radius, new Pair(-90f, 0f), segments, color); // Top left
        drawArcFilled(vertexHelper, vertex1.plus(-radius, radius), radius, new Pair(0f, 90f), segments, color);// Top right
        drawArcFilled(vertexHelper, bottom.minus(radius), radius, new Pair(90f, 180f), segments, color); // Bottom right
        drawArcFilled(vertexHelper, vertex2.plus(radius, -radius), radius, new Pair(180f, 270f), segments, color); // Bottom left

        drawRectFill(vertexHelper, top.plus(radius, 0.0), vertex1.plus(-radius, radius), color); // Top
        drawRectFill(vertexHelper, top.plus(0.0, radius), bottom.minus(0.0, radius), color); // Center
        drawRectFill(vertexHelper, vertex2.plus(radius, -radius), bottom.minus(radius, 0.0), color); // Bottom
    }

    public static void drawQuadOutline(VertexHelper vertexHelper, Vec2d topLeft, Vec2d topRight, Vec2d bottomLeft, Vec2d bottomRight, float lineWidth, Color color) {
        prepareGl();
        GL11.glLineWidth(lineWidth);

        vertexHelper.begin(GL11.GL_QUADS);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        vertexHelper.put(topLeft, color);
        vertexHelper.put(topRight, color);
        vertexHelper.put(bottomLeft, color);
        vertexHelper.put(bottomRight, color);
        vertexHelper.end();
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        releaseGl();
        GL11.glLineWidth(1f);
    }

    public static void drawRectOutline(VertexHelper vertexHelper, Vec2d topLeft, Vec2d bottomRight, float lineWidth, Color color) {
        Vec2d topRight = new Vec2d(bottomRight.x, topLeft.y);
        Vec2d bottomLeft = new Vec2d(topLeft.x, bottomRight.y);

        prepareGl();
        GL11.glLineWidth(lineWidth);

        vertexHelper.begin(GL11.GL_QUAD_STRIP);
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        vertexHelper.put(topLeft, color);
        vertexHelper.put(topRight, color);
        vertexHelper.put(bottomLeft, color);
        vertexHelper.put(bottomRight, color);
        vertexHelper.end();
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

        releaseGl();
        GL11.glLineWidth(1f);
    }

    public static void drawRoundedRectangleOutline(VertexHelper vertexHelper, Vec2d top, Vec2d bottom, double radius, int segments, float lineWidth, Color color) {
        Vec2d vertex1 = new Vec2d(bottom.x, top.y);
        Vec2d vertex2 = new Vec2d(top.x, bottom.y);

        drawArcOutline(vertexHelper, top.plus(radius), radius, new Pair(-90f, 0f), segments, lineWidth, color); // Top left
        drawArcOutline(vertexHelper, vertex1.plus(-radius, radius), radius, new Pair(0f, 90f), segments, lineWidth, color);// Top right
        drawArcOutline(vertexHelper, bottom.minus(radius), radius, new Pair(90f, 180f), segments, lineWidth, color); // Bottom right
        drawArcOutline(vertexHelper, vertex2.plus(radius, -radius), radius, new Pair(180f, 270f), segments, lineWidth, color); // Bottom left

        drawLine(vertexHelper, top.plus(radius, 0.0), vertex1.minus(radius, 0.0), lineWidth, color); // Top
        drawLine(vertexHelper, top.plus(0.0, radius), vertex2.minus(0.0, radius), lineWidth, color); // Left
        drawLine(vertexHelper, vertex1.plus(0.0, radius), bottom.minus(0.0, radius), lineWidth, color); // Right
        drawLine(vertexHelper, vertex2.plus(radius, 0.0), bottom.minus(radius, 0.0), lineWidth, color); // Bottom
    }

    private static void drawArcFilled(VertexHelper vertexHelper, Vec2d center, double radius, Pair<Float, Float> angleRange, int segments, Color color) {
        drawTriangleFan(vertexHelper, center, getArcVertices(center, radius, angleRange, segments), color);
    }

    private static void drawArcOutline(VertexHelper vertexHelper, Vec2d center, double radius, Pair<Float, Float> angleRange, int segments, float lineWidth, Color color) {
        drawLineStrip(vertexHelper, getArcVertices(center, radius, angleRange, segments), lineWidth, color);
    }

    private static void drawTriangleFan(VertexHelper vertexHelper, Vec2d centre, ArrayList<Vec2d> vertices, Color color) {
        prepareGl();

        vertexHelper.begin(GL11.GL_TRIANGLE_FAN);
        vertexHelper.put(centre, color);
        for (Vec2d vertex : vertices) {
            vertexHelper.put(vertex, color);
        }
        vertexHelper.end();

        releaseGl();
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

    public static void drawImage(ResourceLocation resourceLocation, int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        mc.getTextureManager().bindTexture(resourceLocation);
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void drawItem(ItemStack item, int x, int y, int amount, boolean drawAmount) {
        RenderItem itemRenderer = mc.getRenderItem();

        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        itemRenderer.zLevel = 200.0f;
        itemRenderer.renderItemAndEffectIntoGUI(item, x, y);
        itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, item, x, y, "");
        itemRenderer.zLevel = 0.0f;
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        if (drawAmount && amount > 0) {
            FontUtil.drawText(amount + "", x + 17 - FontUtil.getStringWidth(amount + ""), y + FontUtil.getStringHeight("]"), Color.white.getRGB());
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    private static ArrayList<Vec2d> getArcVertices(Vec2d center, double radius, Pair<Float, Float> angleRange, int segments) {
        double range = Math.max(angleRange.first, angleRange.last) - Math.min(angleRange.first, angleRange.last);
        double seg = calculateSegments(segments, radius, range);
        double segAngle = range / seg;

        ArrayList<Vec2d> vec2ds = new ArrayList<>();

        for (int i = 0; i < seg + 1; i++) {
            double angle = Math.toRadians(i * segAngle + angleRange.first);
            vec2ds.add(new Vec2d(Math.sin(angle), -Math.cos(angle)).times(radius).plus(center));
        }

        return vec2ds;
    }

    private static double calculateSegments(int segmentsIn, double radius, double range) {
        if (segmentsIn != -0) return segmentsIn;
        double segments = radius * 0.5 * Math.PI * (range / 360);
        return Math.max(segments, 16);
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
