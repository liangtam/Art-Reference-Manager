package persistence;

import model.ColourPalette;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

// Represents a writer that writes JSON representation of list of colours to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destinationFile;

    // EFFECTS: constructs writer to write to destinationFile
    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer, throws FileNotFoundException if destinationFile
    //          can't be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destinationFile));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of list of colour palette
    public void writeListOfColourPalettes(List<ColourPalette> listOfCPs) {
        JSONArray listOfColourPalettes = new JSONArray();
        for (ColourPalette cp: listOfCPs) {
            JSONObject json = cp.toJson();
            listOfColourPalettes.put(json);
        }
        saveToFile(listOfColourPalettes.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
