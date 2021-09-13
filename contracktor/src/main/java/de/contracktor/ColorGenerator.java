package de.contracktor;

import java.util.Arrays;
import java.util.Random;

public class ColorGenerator {
    Random random = new Random();

    public String[] getRandomColors(int numberOfColors) {
        String[] colors = new String[numberOfColors];
        for (int i = 0; i < numberOfColors; i++) {
           String red =  Integer.toHexString(random.nextInt(255));
           String green =  Integer.toHexString(random.nextInt(255));
           String blue =  Integer.toHexString(random.nextInt(255));
           colors[i] = red + green + blue;
        }
        return colors;
    }

    public String[] getColorSteps(int numberOfColors) {
        String[] colors = new String[numberOfColors];
        for (int i = 0; i < numberOfColors; i++) {
            String red =  Integer.toHexString(random.nextInt(255));
            String green =  Integer.toHexString(random.nextInt(255));
            String blue =  Integer.toHexString(random.nextInt(255));
            colors[i] = red + green + blue;
        }
        return colors;
    }

    private int colorDistance(int[] color1, int[] color2) {
        int sum = 0;
        for (int i = 0; i < color1.length; i++) {
            sum += Math.pow(color1[i] - color2[i], 2);
        }
        return (int) Math.round(Math.sqrt(sum));
    }

    public static void main(String[] args) {
        ColorGenerator colorGenerator = new ColorGenerator();
        System.out.println(Arrays.toString(colorGenerator.getRandomColors(5)));
    }

}
