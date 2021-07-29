package me.ninethousand.fate.api.util.misc.popghost;

import net.minecraft.util.math.Vec3d;

public class PopGhost {
    public double yaw, pitch, posX, posY, posZ;
    public int alpha;

    public PopGhost(double yaw, double pitch, Vec3d pos) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
        this.alpha = 255;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void updateAlpha() {
        this.alpha = alpha - 1;
    }
}
