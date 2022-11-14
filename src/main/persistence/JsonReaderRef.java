package persistence;

import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.ReferenceFolder;
import model.ReferenceImage;
import org.json.*;

// **heavily based on JsonSerializationDemo project**
// Represents a reader that reads Json files
public class JsonReaderRef {
    private String sourceFile;

    // EFFECTS: constructs reader to read from source file
    public JsonReaderRef(String sourceFile) {
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

    // ----------------- RECOVERING REFERENCE FOLDERS ------------------
    // EFFECTS: reads list of sub folders from sourceFile and returns it;
    //          throws IOException if an error occurs reading data from file
    public ArrayList<ReferenceFolder> read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONArray arrayOfReferenceFolders = new JSONArray(jsonData);
        return parseListOfReferenceFolders(arrayOfReferenceFolders);
    }

    // EFFECTS: parses list of colour palettes from JSON Array and returns it
    private ArrayList<ReferenceFolder> parseListOfReferenceFolders(JSONArray jsonArray) {
        ArrayList<ReferenceFolder> referenceFolders = new ArrayList<>();
        for (Object json : jsonArray) {
            // Convert the object in the json array into a JSONObject
            JSONObject referenceFolderInJson = (JSONObject) json;

            // Grab the name of the JSONObject
            String name = referenceFolderInJson.getString("folderName");

            // Make a ReferenceFolder object with the name you grabbed
            ReferenceFolder rf = new ReferenceFolder(name);

            // Add images from the JSONObject to the ColourPalette object
            addRefImages(rf, referenceFolderInJson);

            // Add subFolders from the JSONObject to the ColourPalette object
            addSubFolders(rf, referenceFolderInJson);

            // Add the completed ReferenceFolder to list of ColourPalettes
            referenceFolders.add(rf);
        }

        return referenceFolders;
    }

    // EFFECTS: parses list of reference images from JSON reference folder and adds them to given reference folder
    private void addRefImages(ReferenceFolder rf, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfImages");
        for (Object json : jsonArray) {
            // Convert the object in JSONArray into a JSONObject
            JSONObject refImageInJson = (JSONObject) json;

            // add the JSONObject as a colour to the ColourPalette object
            addRefImage(rf, refImageInJson);
        }
    }

    // EFFECTS: parses subFolders from the given JSONObject and adds it to the given reference folder
    private void addSubFolders(ReferenceFolder rf, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfSubFolders");
        for (Object json : jsonArray) {
            JSONObject subFolderInJson = (JSONObject) json;

            addSubFolder(rf, subFolderInJson);
        }
    }

    // EFFECTS: parses reference image from JSON reference image and adds it to colour palette
    private void addRefImage(ReferenceFolder rf, JSONObject jsonObject) {
        String name = jsonObject.getString("imageName");
        String imageURL = jsonObject.getString("imageURL");
        ReferenceImage refImage = new ReferenceImage(name, imageURL);

        rf.addRef(refImage);
    }

    // EFFECTS: parses subFolder from JSON subFolder and adds it to given reference folder
    private void addSubFolder(ReferenceFolder rf, JSONObject jsonObject) {
        String name = jsonObject.getString("folderName");
        ReferenceFolder subFolder = new ReferenceFolder(name);
        addRefImages(subFolder, jsonObject);
        addSubFolders(subFolder, jsonObject);
        rf.addSubRefFolder(subFolder);
    }
}
