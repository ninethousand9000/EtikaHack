package me.ninethousand.fate.impl.modules.combat;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.misc.Timer;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.sql.Time;

@ModuleAnnotation(category = ModuleCategory.Combat, bind = Keyboard.KEY_O)
public class TestModule extends Module {
    private final Timer timer = new Timer();
    private float scale;
    private float height;

    @Override
    public void onEnable() {
        timer.reset();
        scale = 1.0f;
        height = 90;
    }

    @SubscribeEvent
    public void onHudEvent(RenderGameOverlayEvent.Text event) {
        VertexHelper vertexHelper = new VertexHelper(true);

        GlStateManager.scale(scale, scale, scale);

        if (timer.getPassedTimeMs() >= 10) {
            timer.reset();
            if (height < 200) {
                height+=4;
            }
        }



        GraphicsUtil2d.drawRoundedRectangleFilled(vertexHelper, new Vec2d(10, 10), new Vec2d(100, 100), 10, 10, Color.white);
        GraphicsUtil2d.drawRoundedRectangleOutline(vertexHelper, new Vec2d(10, 10), new Vec2d(100, 100), 10, 10, 2f, Color.white);
        GraphicsUtil2d.drawRectOutline(vertexHelper, new Vec2d(110, 10), new Vec2d(210, 10 + height), 3, Color.white);
        GraphicsUtil2d.drawQuadOutline(vertexHelper, new Vec2d(220, 10), new Vec2d(310, 10), new Vec2d(310, 100), new Vec2d(220, 100), 3, Color.white);

        GlStateManager.popMatrix();
    }
}
