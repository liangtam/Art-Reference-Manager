package model;

import java.awt.*;

public class Colour {
    private String hex;
    private String name;
    private Color colorVisual;

    // EFFECTS: creates a colour with a given name, hex code, and with a colour visual
    //          based on given hex code
    public Colour(String name, String hex) {
        this.name = name;
        this.hex = hex;
        createColourVisual(hex);
    }

    // REQUIRES: given hex must be a valid hex code (in format #XXXXXX,
    //           where X is between 0 and 9 or A to F)
    // MODIFIES: this
    // EFFECTS: creates a visual colour representation of the given hex code

    // citations:1)https://stackoverflow.com/questions/4129666/how-to-convert-hex-to-rgb-using-java
    //           2)https://stackoverflow.com/questions/51672760/integer-valueof-radix-16
    // The first link helped me learn what methods to use to convert hex to r,g,b (without
    //     only doing Color.decode(hex)
    // The second link helped me understand the first link--what radix in Integer.valueOf
    //     means, and why it works
    public void createColourVisual(String hex) {
        // grabs the first two characters of the hex string, and converts
        //       it to an integer value in base 16 (since hexadecimals are base 16)
        int r = Integer.valueOf(hex.substring(1, 3), 16);
        int g = Integer.valueOf(hex.substring(3, 5), 16);
        int b = Integer.valueOf(hex.substring(5, 7), 16);

        Color color = new Color(r, g, b);
        this.colorVisual = color;
    }

    public String getHex() {
        return this.hex;
    }

    public String getName() {
        return this.name;
    }

    public Color getColourVisual() {
        return this.colorVisual;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public void setName(String name) {
        this.name = name;
    }

}
