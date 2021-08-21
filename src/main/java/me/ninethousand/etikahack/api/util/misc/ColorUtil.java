package me.ninethousand.etikahack.api.util.misc;

import me.ninethousand.etikahack.api.util.math.MathUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorUtil {
    public static final Color friendColor = new Color(-11157267);

    public static Map<Integer, Integer> getInterpolatedValues(int steps, Color start, Color end) {
        Map<Integer, Integer> gradMap = new HashMap<Integer, Integer>();
        Color temp;
        float percentChange = 1.0f / steps;
        float percent =  0;
        for (int i = 0; i < steps; i++) {
            int resultRed = MathUtil.roundNumberDown(start.getRed() + percent * (end.getRed() - start.getRed()));
            int resultGreen = MathUtil.roundNumberDown(start.getGreen() + percent * (end.getGreen() - start.getGreen()));
            int resultBlue = MathUtil.roundNumberDown(start.getBlue() + percent * (end.getBlue() - start.getBlue()));
            temp = new Color(resultRed, resultGreen, resultBlue);
            gradMap.put(i, temp.getRGB());
            percent += percentChange;
        }
        return gradMap;
    }

    public static Map<Integer, Integer> fillMapWithColor(int steps, Color color) {
        Map<Integer, Integer> gradMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < steps; i++) {
            gradMap.put(i, color.getRGB());
        }
        return gradMap;
    }
}
