package me.ninethousand.fate.api.util.render;

import me.ninethousand.fate.Fate;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import org.lwjgl.opengl.Display;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class IconUtil {
    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/fate/logos/newlogo-16x.png");
                 InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/fate/logos/newlogo-32x.png")) {
                ByteBuffer[] icons = new ByteBuffer[]{readImageToBuffer(inputStream16x), readImageToBuffer(inputStream32x)};
                Display.setIcon(icons);
            } catch (Exception e) {
                Fate.log("FAILED TO SET ICON");
            }
        }
    }

    public static ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException {
        BufferedImage bufferedimage = ImageIO.read(inputStream);
        int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
        Arrays.stream(aint).map(i -> i << 8 | (i >> 24 & 255)).forEach(bytebuffer::putInt);
        bytebuffer.flip();
        return bytebuffer;
    }
}
