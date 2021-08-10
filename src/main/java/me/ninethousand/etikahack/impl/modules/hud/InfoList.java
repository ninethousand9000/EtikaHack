package me.ninethousand.etikahack.impl.modules.hud;

import me.ninethousand.etikahack.api.event.events.RenderEvent2d;
import me.ninethousand.etikahack.api.module.Module;
import me.ninethousand.etikahack.api.module.ModuleAnnotation;
import me.ninethousand.etikahack.api.module.ModuleCategory;
import me.ninethousand.etikahack.api.settings.Setting;
import me.ninethousand.etikahack.api.util.game.ServerManager;
import me.ninethousand.etikahack.api.util.game.SpeedManager;
import me.ninethousand.etikahack.api.util.render.font.FontUtil;
import me.ninethousand.etikahack.api.util.render.gl.VertexHelper;
import me.ninethousand.etikahack.impl.modules.client.Customise;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ModuleAnnotation(category = ModuleCategory.HUD)
public class InfoList extends Module {
    public static final Setting<Boolean> ping = new Setting<>("Ping", true);
    public static final Setting<Boolean> fps = new Setting<>("FPS", true);
    public static final Setting<Boolean> tps = new Setting<>("TPS", true);
    public static final Setting<Boolean> speed = new Setting<>("Speed", true);
    public static final Setting<Boolean> potions = new Setting<>("Potions", true);
    public static final Setting<Boolean> potionColors = new Setting<>(potions, "UseCustomColor", true);

    private static final Map<Potion, Color> potionColorMap = new HashMap<Potion, Color>();

    public InfoList() {
        addSettings(ping, fps, tps, speed, potions);
        setupPotions();
    }

    private static void setupPotions() {
        // Thanks for the colours phobos :)

        potionColorMap.put(MobEffects.SPEED, new Color(124, 175, 198));
        potionColorMap.put(MobEffects.SLOWNESS, new Color(90, 108, 129));
        potionColorMap.put(MobEffects.HASTE, new Color(217, 192, 67));
        potionColorMap.put(MobEffects.MINING_FATIGUE, new Color(74, 66, 23));
        potionColorMap.put(MobEffects.STRENGTH, new Color(147, 36, 35));
        potionColorMap.put(MobEffects.INSTANT_HEALTH, new Color(67, 10, 9));
        potionColorMap.put(MobEffects.INSTANT_DAMAGE, new Color(67, 10, 9));
        potionColorMap.put(MobEffects.JUMP_BOOST, new Color(34, 255, 76));
        potionColorMap.put(MobEffects.NAUSEA, new Color(85, 29, 74));
        potionColorMap.put(MobEffects.REGENERATION, new Color(205, 92, 171));
        potionColorMap.put(MobEffects.RESISTANCE, new Color(153, 69, 58));
        potionColorMap.put(MobEffects.FIRE_RESISTANCE, new Color(228, 154, 58));
        potionColorMap.put(MobEffects.WATER_BREATHING, new Color(46, 82, 153));
        potionColorMap.put(MobEffects.INVISIBILITY, new Color(127, 131, 146));
        potionColorMap.put(MobEffects.BLINDNESS, new Color(31, 31, 35));
        potionColorMap.put(MobEffects.NIGHT_VISION, new Color(31, 31, 161));
        potionColorMap.put(MobEffects.HUNGER, new Color(88, 118, 83));
        potionColorMap.put(MobEffects.WEAKNESS, new Color(72, 77, 72));
        potionColorMap.put(MobEffects.POISON, new Color(78, 147, 49));
        potionColorMap.put(MobEffects.WITHER, new Color(53, 42, 39));
        potionColorMap.put(MobEffects.HEALTH_BOOST, new Color(248, 125, 35));
        potionColorMap.put(MobEffects.ABSORPTION, new Color(37, 82, 165));
        potionColorMap.put(MobEffects.SATURATION, new Color(248, 36, 35));
        potionColorMap.put(MobEffects.GLOWING, new Color(148, 160, 97));
        potionColorMap.put(MobEffects.LEVITATION, new Color(206, 255, 255));
        potionColorMap.put(MobEffects.LUCK, new Color(51, 153, 0));
        potionColorMap.put(MobEffects.UNLUCK, new Color(192, 164, 77));
    }

    @Override
    public void onHudRender(RenderEvent2d event, VertexHelper vertexHelper) {
        int responseTime, framesPerSecond, ticksPerSecond;
        double kmh;

        ScaledResolution scaledResolution = new ScaledResolution(mc);

        try {
            responseTime = Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime();
        } catch (Exception exception) {
            responseTime = 0;
        }

        framesPerSecond = mc.getDebugFPS();
        ticksPerSecond = (int) ServerManager.getTickRate();
        kmh = SpeedManager.getCurrentSpeedKMH();

        if (Customise.arrayListTop.getValue()) {
            int startX = scaledResolution.getScaledWidth(), startY = scaledResolution.getScaledHeight();

            if (potions.getValue()) {
                for (PotionEffect effect : mc.player.getActivePotionEffects()) {
                    String pText = I18n.format(effect.getPotion().getName(), new Object[0]) + " " + (effect.getAmplifier() + 1) + " " + Potion.getPotionDurationString(effect, 1.0f);

                    if (potionColors.getValue()) {
                        FontUtil.drawText(pText, startX - FontUtil.getStringWidth(pText) - 2, startY - FontUtil.getStringHeight(pText) - 2, potionColorMap.get(effect.getPotion()).getRGB());
                    }

                    else {
                        FontUtil.drawTextHUD(pText, startX - FontUtil.getStringWidth(pText) - 2, startY - FontUtil.getStringHeight(pText) - 2);
                    }

                    startY -= FontUtil.getStringHeight(pText);
                }
            }

            if (speed.getValue()) {
                FontUtil.drawTextHUD("Speed " + kmh + " km/h", startX - FontUtil.getStringWidth("Speed " + kmh + " km/h") - 2, startY - FontUtil.getStringHeight("Speed " + kmh + " km/h") - 2);
                startY -= FontUtil.getStringHeight("Speed " + kmh + " km/h");
            }

            if (tps.getValue()) {
                FontUtil.drawTextHUD("TPS " + ticksPerSecond, startX - FontUtil.getStringWidth("TPS " + ticksPerSecond) - 2, startY - FontUtil.getStringHeight("TPS " + ticksPerSecond) - 2);
                startY -= FontUtil.getStringHeight("TPS " + ticksPerSecond);
            }

            if (fps.getValue()) {
                FontUtil.drawTextHUD("FPS " + framesPerSecond, startX - FontUtil.getStringWidth("FPS " + framesPerSecond) - 2, startY - FontUtil.getStringHeight("FPS " + framesPerSecond) - 2);
                startY -= FontUtil.getStringHeight("FPS " + framesPerSecond);
            }

            if (ping.getValue()) {
                FontUtil.drawTextHUD("Ping " + responseTime, startX - FontUtil.getStringWidth("Ping " + responseTime) - 2, startY - FontUtil.getStringHeight("Ping " + responseTime) - 2);
                startY -= FontUtil.getStringHeight("Ping " + responseTime);
            }
        }

        else {
            int startX = scaledResolution.getScaledWidth(), startY = 0;

            if (potions.getValue()) {
                for (PotionEffect effect : mc.player.getActivePotionEffects()) {
                    String pText = I18n.format(effect.getPotion().getName(), new Object[0]) + " " + (effect.getAmplifier() + 1) + " " + Potion.getPotionDurationString(effect, 1.0f);

                    if (potionColors.getValue()) {
                        FontUtil.drawText(pText, startX - FontUtil.getStringWidth(pText) - 2, startY + 2, potionColorMap.get(effect.getPotion()).getRGB());
                    }

                    else {
                        FontUtil.drawTextHUD(pText, startX - FontUtil.getStringWidth(pText) - 2, startY + 2);
                    }

                    startY += FontUtil.getStringHeight(pText);
                }
            }

            if (speed.getValue()) {
                FontUtil.drawTextHUD("Speed " + kmh + " km/h", startX - FontUtil.getStringWidth("Speed " + kmh + " km/h") - 2, startY + 2);
                startY += FontUtil.getStringHeight("Speed " + kmh + " km/h");
            }

            if (tps.getValue()) {
                FontUtil.drawTextHUD("TPS " + ticksPerSecond, startX - FontUtil.getStringWidth("TPS " + ticksPerSecond) - 2, startY + 2);
                startY += FontUtil.getStringHeight("TPS " + ticksPerSecond);
            }

            if (fps.getValue()) {
                FontUtil.drawTextHUD("FPS " + framesPerSecond, startX - FontUtil.getStringWidth("FPS " + framesPerSecond) - 2, startY + 2);
                startY += FontUtil.getStringHeight("FPS " + framesPerSecond);
            }

            if (ping.getValue()) {
                FontUtil.drawTextHUD("Ping " + responseTime, startX - FontUtil.getStringWidth("Ping " + responseTime) - 2, startY + 2);
                startY += FontUtil.getStringHeight("Ping " + responseTime);
            }
        }
    }
}
