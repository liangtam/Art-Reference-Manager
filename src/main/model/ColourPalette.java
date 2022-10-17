package model;

import java.util.ArrayList;

public class ColourPalette {
    private ArrayList<Colour> colours;
    private ArrayList<ColourPalette> subColourPalettes;
    private String name;
    private int numOfColourPalettes;
    private int numOfColours;

    // EFFECTS: creates a colour palette with given name, no sub colour palettes,
    //          and no colours
    public ColourPalette(String name) {
        this.colours = new ArrayList<>();
        this.subColourPalettes = new ArrayList<>();
        this.name = name;
        this.numOfColourPalettes = 0;
        this.numOfColours = 0;
    }

    // MODIFIES: this
    // EFFECTS: if the colour does not exist in colours collection already,
    //          adds given colour to colour collection, return true
    //          else, do nothing and return false
    public boolean addColour(Colour colour) {
        if (ifColourAlreadyExists(colour)) {
            return false;
        }

        this.colours.add(colour);
        this.numOfColours++;
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if the colour exists already in the current palette's colours collection,
    //          deletes given colour from current palette (not in any sub palettes!), return true
    //          else, do nothing and return false
    public boolean deleteColour(String colour) {
        if (colours.isEmpty()) {
            return false;
        }
        for (Colour c: colours) {
            if (c.getName().equals(colour)) {
                this.colours.remove(c);
                this.numOfColours--;
                return true;
            }
        }
        return false;
    }

    // REQUIRES: colourPalette is not current colourPalette
    // MODIFIES: this
    // EFFECTS: if collection of subColourPalettes does not have a colour palette
    //             with same name as given colour palette, nor is the given colour palette a parent colour palette,
    //          adds given colour palette to collection of subColourPalettes, return true
    //          else, do nothing and return false
    public boolean addSubColourPalette(ColourPalette colourPalette) {
        if (ifSubColourPaletteAlreadyExists(colourPalette.getName())) {
            return false;
        }
        this.subColourPalettes.add(colourPalette);
        numOfColourPalettes++;
        return true;
    }

    // REQUIRES: colourPalette is not current colourPalette
    // MODIFIES: this
    // EFFECTS: if collection of subColourPalettes does not have a colour palette
    //             with same name as given colour palette, nor is the given colour palette a parent colour palette,
    //          remove given colour palette from collection of subColourPalettes, return true
    //          else, do nothing and return false
    public boolean deleteSubColourPalette(ColourPalette colourPalette) {
        if (ifSubColourPaletteAlreadyExists(colourPalette.getName())) {
            this.subColourPalettes.remove(colourPalette);
            numOfColourPalettes--;
            return true;
        }
        return false;
    }

    // EFFECTS: check if given colour already exists in current colour palette
    public boolean ifColourAlreadyExists(Colour colour) {
        for (Colour c: colours) {
            if (c == colour) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if current colour palette already has given colourPalette as sub colour palette,
    //          else returns false
    public boolean ifSubColourPaletteAlreadyExists(String colourPalette) {
        for (ColourPalette cp: subColourPalettes) {
            if (cp.getName().equals(colourPalette)) {
                return true;
            }
        }
        return false;
    }

    // Getters and setters
    public ArrayList<Colour> getColours() {
        return this.colours;
    }

    public ArrayList<ColourPalette> getSubColourPalettes() {
        return this.subColourPalettes;
    }

    public String getName() {
        return this.name;
    }

    // EFFECTS: returns number of subColourPalettes in current palette
    public int getNumOfColourPalettes() {
        return numOfColourPalettes;
    }

    // EFFECTS: returns total number of colours in colour palette, not including in sub colour palettes
    public int getNumOfColours() {
        return this.numOfColours;
    }

    // MODIFIES: this
    // EFFECTS: Changes the name of current colour palette to given name
    public void setName(String name) {
        this.name = name;
    }

}
