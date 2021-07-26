package me.ninethousand.fate.api.util.misc.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class SoundUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void playGuiClick() {
        mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
    }
}
