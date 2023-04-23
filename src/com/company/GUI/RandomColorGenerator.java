package com.company.GUI;

import java.awt.*;
import java.util.Random;

public class RandomColorGenerator {
    private Random random;
    private float hue;
    private float saturation;
    private float brightness;

    public RandomColorGenerator() {
        random = new Random();
        hue = random.nextFloat();
        saturation = 0.5f + 0.5f * random.nextFloat();
        brightness = 0.5f + 0.5f * random.nextFloat();
    }

    public Paint nextColor() {
        hue += 0.3f * random.nextFloat();
        hue %= 1.0f;
        saturation = 0.5f + 0.5f * random.nextFloat();
        brightness = 0.5f + 0.5f * random.nextFloat();
        return Color.getHSBColor(hue, saturation, brightness);
    }
}
