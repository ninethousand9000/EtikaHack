package me.ninethousand.fate.api.util.math;

public class Vec2d {
    public double x, y;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double length() {
        return Math.hypot(x, y);
    }

    public Vec2d plus(Vec2d vec2d) {
        return plus(vec2d.x, vec2d.y);
    }

    public Vec2d plus(double factor) {
        return plus(factor, factor);
    }

    public Vec2d plus(double x, double y) {
        return new Vec2d(this.x + x, this.y + y);
    }

    public Vec2d minus(double factor) {
        return minus(factor, factor);
    }

    public Vec2d minus(double x, double y) {
        return plus(-x, -y);
    }

    public Vec2d times(double multiplier) {
        return times(multiplier, multiplier);
    }

    public Vec2d times(double x, double y) {
        return new Vec2d(this.x * x, this.y * y);
    }
}
