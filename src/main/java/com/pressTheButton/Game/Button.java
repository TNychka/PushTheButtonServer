package com.pressTheButton.Game;

import lombok.NoArgsConstructor;

import java.awt.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Float.max;

/**
 * Created by Tyler on 2016-08-20.
 */

public class Button {
    public enum status {
        ALIVE, DEAD
    }

    //Hex code color of the button
    private Color colour;

    //Scale of 0-100 of happiness rating
    private Float happiness;

    public String getFace() {
        if (happiness > 80) {
            return ":D";
        } else if (happiness == 69){
            return ";)";
        }else if (happiness > 60) {
            return ":)";
        } else if (happiness > 40) {
            return ":|";
        } else if (happiness > 20) {
            return ":(";
        } else if (happiness > 5){
            return ":'(";
        } else if (happiness > 0){
            return ":'C";
        }  else {
            return "DX";
        }
    }

    public Float getHappy() {
        return happiness;
    }

    public void setHappy(Float happiness) {
        this.happiness = max(happiness, 0);
    }

    public String getColor() {
        int r = this.colour.getRed();
        int g = this.colour.getGreen();
        int b = this.colour.getBlue();
        return String.format("#%02x%02x%02x", r, g, b);
    }

    private void setColour(String hex) {
        Pattern p = Pattern.compile("^#[0-9A-F]{6}$");
        Matcher m = p.matcher(hex);
        if (m.matches()) {
            colour = new Color(
                    Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                    Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                    Integer.valueOf( hex.substring( 5, 7 ), 16 ) );
        } else {
            setRandomColour();
        }
    }

    private void setColour(int r, int g, int b) {
        colour = new Color(r, g, b);
    }

    private void setRandomColour() {
        Random randomGenerator = new Random();
        int red = randomGenerator.nextInt(256);
        int green = randomGenerator.nextInt(256);
        int blue = randomGenerator.nextInt(256);
        setColour(red, green, blue);
    }
}
