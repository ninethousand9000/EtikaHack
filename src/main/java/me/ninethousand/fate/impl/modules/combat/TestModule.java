package me.ninethousand.fate.impl.modules.combat;

import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.util.math.Vec2d;
import me.ninethousand.fate.api.util.render.gl.VertexHelper;
import me.ninethousand.fate.api.util.render.graphics.GraphicsUtil2d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@ModuleAnnotation(category = ModuleCategory.Combat, bind = Keyboard.KEY_O)
public class TestModule extends Module {
    @SubscribeEvent
    public void onHudEvent(RenderGameOverlayEvent.Text event) {
        VertexHelper vertexHelper = new VertexHelper(true);
        GraphicsUtil2d.drawRoundedRectangleOutline(vertexHelper, new Vec2d(10, 10), new Vec2d(100, 100), 4, 10, 2f, Color.white);
        GraphicsUtil2d.drawRectOutline(vertexHelper, new Vec2d(110, 10), new Vec2d(210, 100), 3, Color.white);
        GraphicsUtil2d.drawQuadOutline(vertexHelper, new Vec2d(220, 10), new Vec2d(310, 10), new Vec2d(310, 100), new Vec2d(220, 100), 3, Color.white);
    }
}
