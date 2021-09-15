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

}
