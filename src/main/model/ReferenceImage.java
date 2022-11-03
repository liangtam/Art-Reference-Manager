package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// Represents a reference image
public class ReferenceImage implements Writable {
    private String name;
    private Image image;

    // EFFECTS: creates a reference image with given name and image file
    public ReferenceImage(String name, String imgFileURL) {
        this.name = name;
        createImage(imgFileURL);
    }

    // MODIFIES: this
    // EFFECTS: sets this reference image as image from the given imgFileURL
    public void createImage(String imgFileURL) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(imgFileURL));
        } catch (IOException e) {
            System.out.println("This image file does not exist.");
        }

        this.image = img;
    }

    // Getters and setters
    // EFFECTS: returns the name of the reference image
    public String getName() {
        return this.name;
    }

    // EFFECTS: returns the image of the reference image
    public Image getImage() {
        return this.image;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this reference image to given name
    public void setName(String newName) {
        this.name = newName;
    }

    // **based on JsonSerializationDemo project**
    // EFFECTS: turns this ReferenceImage object into a JSON object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("imageName", this.name);
        json.put("image", this.image);
        return json;
    }
}

