package me.ninethousand.fate.api.util.math;

import java.util.ArrayList;

public class Quad {
    public Vec2d p1;
    public Vec2d p2;
    public Vec2d[] vertices;

    public Quad(Vec2d p1, Vec2d p2) {
        this.p1 = p1;
        this.p2 = p2;
        vertices = new Vec2d[]{p1, p2};
    }
}
