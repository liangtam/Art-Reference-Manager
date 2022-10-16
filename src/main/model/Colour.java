package model;

import java.awt.*;

public class Colour {
    private String hex;
    private String name;
    private Color color;

    public Colour(String name, String hex) {
        this.name = name;
        this.hex = hex;
    }

    public String getHex() {
        return this.hex;
    }

    public String getName() {
        return this.name;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public void setName(String name) {
        this.name = name;
    }



}
