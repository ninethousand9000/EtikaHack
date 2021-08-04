package me.ninethousand.fate.impl.modules.misc;

import me.ninethousand.fate.api.command.Command;
import me.ninethousand.fate.api.module.Module;
import me.ninethousand.fate.api.module.ModuleAnnotation;
import me.ninethousand.fate.api.module.ModuleCategory;
import me.ninethousand.fate.api.settings.Setting;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@ModuleAnnotation(category = ModuleCategory.MISC)
public class VisualRange extends Module {
    public static final Setting<Boolean> players = new Setting<>("Players", true);
    public static final Setting<Boolean> horses = new Setting<>("Horses", true);
    public static final Setting<Boolean> donkeys = new Setting<>("Donkeys", true);
    public static final Setting<Boolean> mules = new Setting<>("Mules", true);
    public static final Setting<Boolean> ghasts = new Setting<>("Ghasts", true);
    public static final Setting<Boolean> evokers = new Setting<>("Evokers", true);
    public static final Setting<Boolean> coords = new Setting<>("Coords", false);
    public static final Setting<Boolean> sound = new Setting<>("Sound", true);

    public VisualRange() {
        addSettings(
                players,
                horses,
                donkeys,
                mules,
                ghasts,
                evokers,
                coords,
                sound
        );
    }

    private static final List<Entity> currentLoaded = new ArrayList<>();

    @Override
    public void onUpdate() {
        if (nullCheck()) return;

        try {
            mc.world.loadedEntityList.forEach(entity -> {
                if (entity instanceof EntityPlayer && !currentLoaded.contains(entity) && players.getValue()) {
                    if (coords.getValue()) {
                        Command.sendClientMessageDefault("Player: " + entity.getName() +
                                " has been spotted at X=" + (int) entity.posX +
                                " Y=" + (int) entity.posY +
                                " Z=" + (int) entity.posZ);
                    }

                    else {
                        Command.sendClientMessageDefault("Player: " + entity.getName() +
                                " has been spotted");
                    }

                    if (sound.getValue()) mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));

                }

                if (entity instanceof EntityHorse && !currentLoaded.contains(entity) && horses.getValue()) {
                    if (coords.getValue()) {
                        Command.sendClientMessageDefault("Horse has been spotted at X=" + (int) entity.posX +
                                " Y=" + (int) entity.posY +
                                " Z=" + (int) entity.posZ);
                    }

                    else {
                        Command.sendClientMessageDefault("Horse has been spotted");
                    }

                    if (sound.getValue()) mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));

                }

                if (entity instanceof EntityDonkey && !currentLoaded.contains(entity) && donkeys.getValue()) {
                    if (coords.getValue()) {
                        Command.sendClientMessageDefault("Donkey has been spotted at X=" + (int) entity.posX +
                                " Y=" + (int) entity.posY +
                                " Z=" + (int) entity.posZ);
                    }

                    else {
                        Command.sendClientMessageDefault("Donkey has been spotted");
                    }

                    if (sound.getValue()) mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));

                }

                if (entity instanceof EntityMule && !currentLoaded.contains(entity) && mules.getValue()) {
                    if (coords.getValue()) {
                        Command.sendClientMessageDefault("Mule has been spotted at X=" + (int) entity.posX +
                                " Y=" + (int) entity.posY +
                                " Z=" + (int) entity.posZ);
                    }

                    else {
                        Command.sendClientMessageDefault("Mule has been spotted");
                    }

                    if (sound.getValue()) mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));

                }

                if (entity instanceof EntityGhast && !currentLoaded.contains(entity) && ghasts.getValue()) {
                    if (coords.getValue()) {
                        Command.sendClientMessageDefault("Ghast has been spotted at X=" + (int) entity.posX +
                                " Y=" + (int) entity.posY +
                                " Z=" + (int) entity.posZ);
                    }

                    else {
                        Command.sendClientMessageDefault("Ghast has been spotted");
                    }

                    if (sound.getValue()) mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));

                }

                if (entity instanceof EntityEvoker && !currentLoaded.contains(entity) && evokers.getValue()) {
                    if (coords.getValue()) {
                        Command.sendClientMessageDefault("Evoker has been spotted at X=" + (int) entity.posX +
                                " Y=" + (int) entity.posY +
                                " Z=" + (int) entity.posZ);
                    }

                    else {
                        Command.sendClientMessageDefault("Evoker has been spotted");
                    }

                    if (sound.getValue()) mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F));

                }

                if (!currentLoaded.contains(entity)) currentLoaded.add(entity);
            });
        }

        catch (ConcurrentModificationException fatfucker) {}
    }
}
