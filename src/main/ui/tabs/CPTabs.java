package ui.tabs;

import model.ColourPalette;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CPTabs extends JPanel {
    protected static final String JSON_CP = "./data/colourPalettes.json";
    protected JsonReader jsonReader;
    protected JsonWriter jsonWriter;
    protected List<ColourPalette> colourPalettes;

    CPTabs() {
        setLayout(null);
        jsonReader = new JsonReader(JSON_CP);
        jsonWriter = new JsonWriter(JSON_CP);
        this.colourPalettes = new ArrayList<>();

        loadColourPalettes();
    }

    // MODIFIES: this
    // EFFECTS: loads list of colour palettes from file
    protected void loadColourPalettes() {
        try {
            colourPalettes = jsonReader.read();
            System.out.println("Loaded all the colour palettes from " + JSON_CP + ". Num of cps: " + this.colourPalettes.size());
        } catch (IOException e) {
            System.out.println("Could not load from file: " + JSON_CP);
        }
    }

    //  EFFECTS: Saves all the current created colour palettes to file
    protected void saveAllColourPalettes() {
        try {
            jsonWriter.open();
            jsonWriter.writeListOfColourPalettes(this.colourPalettes);
            jsonWriter.close();
            System.out.println("Saved all colour palettes to " + JSON_CP + ". Num of cps: " + colourPalettes.size());
        } catch (FileNotFoundException e) {
            System.out.println("Could not save to file: " + JSON_CP);
        }
    }
}
