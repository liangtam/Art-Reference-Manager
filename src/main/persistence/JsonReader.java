package persistence;

import model.Colour;
import model.ColourPalette;

import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

// **heavily based on JsonSerializationDemo project**
// Represents a reader that reads Json files
public class JsonReader {
    private String sourceFile;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    // EFFECTS: reads sourceFile as a string and returns it
    private String readFile(String sourceFile) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // ----------------- RECOVERING COLOUR PALETTES ------------------
    // EFFECTS: reads list of colour palettes from sourceFile and returns it;
    //          throws IOException if an error occurs reading data from file
    public ArrayList<ColourPalette> read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONArray arrayOfColourPalettes = new JSONArray(jsonData);
        return parseListOfColourPalettes(arrayOfColourPalettes);
    }

    // EFFECTS: parses list of colour palettes from JSON Array and returns it
    private ArrayList<ColourPalette> parseListOfColourPalettes(JSONArray jsonArray) {
        ArrayList<ColourPalette> colourPalettes = new ArrayList<>();
        for (Object json : jsonArray) {
            // Convert the object in the json array into a JSONObject
            JSONObject colourPaletteInJson = (JSONObject) json;

            // Grab the name of the JSONObject
            String name = colourPaletteInJson.getString("paletteName");

            // Make a ColourPalette object with the name you grabbed
            ColourPalette cp = new ColourPalette(name);

            // Add colours from the JSONObject to the ColourPalette object
            addColours(cp, colourPaletteInJson);

            // Add subColourPalettes from the JSONObject to the ColourPalette object
            addSubColourPalettes(cp, colourPaletteInJson);

            // Add the completed ColourPalette to list of ColourPalettes
            colourPalettes.add(cp);
        }

        return colourPalettes;
    }

    // EFFECTS: parses list of colours from JSON colour palette and adds them to given colour palette
    private void addColours(ColourPalette cp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfColours");
        for (Object json : jsonArray) {
            // Convert the object in JSONArray into a JSONObject
            JSONObject colourInJson = (JSONObject) json;

            // add the JSONObject as a colour to the ColourPalette object
            addColour(cp, colourInJson);
        }
    }

    // EFFECTS: parses subColourPalettes from the given JSONObject and adds it to the given colour palette
    private void addSubColourPalettes(ColourPalette cp, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfSubColourPalettes");
        for (Object json: jsonArray) {
            JSONObject subColourPaletteInJson = (JSONObject) json;

            addSubColourPalette(cp, subColourPaletteInJson);
        }
    }

    // EFFECTS: parses colour from JSON colour and adds it to colour palette
    private void addColour(ColourPalette cp, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String hex = jsonObject.getString("hex");
        Colour colour = new Colour(name, hex);
        cp.addColour(colour);
    }

    // EFFECTS: parses subColourPalette from JSON subColourPalette and adds it to colour palette
    private void addSubColourPalette(ColourPalette cp, JSONObject jsonObject) {
        String name = jsonObject.getString("paletteName");
        ColourPalette subCP = new ColourPalette(name);
        addColours(subCP, jsonObject);
        addSubColourPalettes(subCP, jsonObject);
        cp.addSubColourPalette(subCP);
    }

}
