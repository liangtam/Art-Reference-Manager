package ui;

import model.ColourPalette;
import model.ReferenceFolder;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class MainFrame extends JFrame {
    private java.util.List<ColourPalette> colourPalettes;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/colourPalettes.json";
    private List<ReferenceFolder> referenceFolders;

    public MainFrame() {

        ImageIcon icon = new ImageIcon("src/main/resources/artref-icon.png");
        ImageIcon cpImage = new ImageIcon("src/main/resources/artref-cp.png");
        ImageIcon folderImage = new ImageIcon("src/main/resources/artref-folder.png");
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/artref-bg.png");


        JPanel panel = new JPanel();

        this.setSize(1000, 696);
        this.setTitle("Art Reference Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(true);

        this.setIconImage(icon.getImage()); // changes the icon of the frame
        loadColourPalettes();
    }


    // MODIFIES: this
    // EFFECTS: loads list of colour palettes from file
    private void loadColourPalettes() {
        try {
            colourPalettes = jsonReader.read();
            System.out.println("Loaded all the colour palettes from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Could not save to file: " + JSON_STORE);
        }
    }


}
