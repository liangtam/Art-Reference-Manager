package model;

import model.exceptions.CurrentPaletteException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a collection of colours--i.e. a colour scheme.
public class ColourPalette implements Writable {
    private ArrayList<Colour> colours;
    private ArrayList<ColourPalette> subColourPalettes;
    private String name;
    private int numOfColourPalettes;
    private int numOfColours;
    private EventLog eventLog = EventLog.getInstance();

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
        eventLog.logEvent(new Event("Added colour " + colour.getName() + " to palette: " + getName()));
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
                eventLog.logEvent(new Event("Removed colour " + colour + " from palette: " + getName()));
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: if collection of subColourPalettes does not have a colour palette
    //             with same name as given colour palette, nor is the given colour palette a parent colour palette,
    //          adds given colour palette to collection of subColourPalettes, return true
    //          else, do nothing and return false
    public boolean addSubColourPalette(ColourPalette colourPalette) throws CurrentPaletteException {
        if (this.name.equals(colourPalette.getName())) {
            throw new CurrentPaletteException();
        }
        if (ifSubColourPaletteAlreadyExists(colourPalette)) {
            return false;
        }
        this.subColourPalettes.add(colourPalette);
        numOfColourPalettes++;
        eventLog.logEvent(new Event("Added sub colour palette " + colourPalette.getName()
                + " to " + this.getName()));
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if collection of subColourPalettes has a colour palette
    //             with same name as given colour palette,
    //          remove given colour palette from collection of subColourPalettes, return true
    //          else, do nothing and return false
    public boolean deleteSubColourPalette(ColourPalette colourPalette) throws CurrentPaletteException {
        if (this.name.equals(colourPalette.getName())) {
            throw new CurrentPaletteException();
        }
        if (ifSubColourPaletteAlreadyExists(colourPalette)) {
            this.subColourPalettes.remove(colourPalette);
            numOfColourPalettes--;
            eventLog.logEvent(new Event("Deleted sub colour palette " + colourPalette.getName()
                    + " from " + this.getName()));
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
    public boolean ifSubColourPaletteAlreadyExists(ColourPalette colourPalette) {
        for (ColourPalette cp: subColourPalettes) {
            if (cp.getName().equals(colourPalette.getName())) {
                return true;
            }
        }
        return false;
    }

    // Getters and Setters
    // EFFECTS: returns the collection of colours in this colour palette
    public ArrayList<Colour> getColours() {
        return this.colours;
    }

    // EFFECTS: returns the collection of sub colour palettes in this colour palette
    public ArrayList<ColourPalette> getSubColourPalettes() {
        return this.subColourPalettes;
    }

    // EFFECTS: returns name of this colour palette
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

    // **based on JsonSerializationDemo project**
    // Citation: https://stackoverflow.com/questions/18983185/how-to-create-correct-jsonarray-in-java-using-jsonobject
    //           ^ Used to learn how to use JSONArray using JSONObject
    // EFFECTS: turns this ColourPalette object into a JSON object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray arrayOfColours = new JSONArray();
        JSONArray arrayOfSubColourPalettes = new JSONArray();

        for (Colour c: this.colours) {
            JSONObject colourToJson = c.toJson();
            arrayOfColours.put(colourToJson);
        }

        for (ColourPalette cp: this.subColourPalettes) {
            JSONObject subPaletteToJson = cp.toJson();
            arrayOfSubColourPalettes.put(subPaletteToJson);

        }
        json.put("paletteName", this.name);
        json.put("listOfColours", arrayOfColours);
        json.put("listOfSubColourPalettes", arrayOfSubColourPalettes);

        return json;
    }
}
