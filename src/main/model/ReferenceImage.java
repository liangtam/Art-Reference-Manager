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
    private String imageURL;
    private BufferedImage image;

    // MODIFIES: this
    // EFFECTS: creates a reference image with given name and image file
    public ReferenceImage(String name, String imgFileURL) {
        this.name = name;
        this.imageURL = imgFileURL;
        createImage();
    }

    // MODIFIES: this
    // EFFECTS: sets this reference image as image from the given imgFileURL
    public void createImage() {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(this.imageURL));
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
    public BufferedImage getImage() {
        return this.image;
    }

    // EFFECTS: returns the imageURL of the reference image
    public String getImageURL() {
        return this.imageURL;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this reference image to given name
    public void setName(String newName) {
        this.name = newName;
    }

    // MODIFIES: this
    // EFFECTS: sets the imageURL of this reference image to given imageURL
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // **based on JsonSerializationDemo project**
    // EFFECTS: turns this ReferenceImage object into a JSON object and returns it
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("imageName", this.name);
        json.put("imageURL", this.imageURL);
        return json;
    }
}

