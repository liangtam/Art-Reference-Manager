package ui;

import model.ColourPalette;
import model.ReferenceFolder;
import persistence.JsonReader;
import persistence.JsonReaderRef;
import persistence.JsonWriter;
import ui.tabs.MainTab;
import ui.tabs.CreateColourPaletteTab;
import ui.tabs.CreateRefFolderTab;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Represents the main menu of the application--manages all the data and holds all the other pages
public class MainFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 696;

    private static final ImageIcon ICON = new ImageIcon("src/main/resources/artref-icon.png");

    private JTabbedPane tabbedPane;

    private static final String JSON_CP = "./data/colourPalettes.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriterCP;
    private JsonWriter jsonWriterRF;
    private static final String JSON_RF = "./data/referenceFolders.json";
    private JsonReaderRef jsonReaderRef;
    private List<ColourPalette> colourPalettes;
    private List<ReferenceFolder> referenceFolders;

    // EFFECTS: Constructs a JFrame
    public MainFrame() {
        super("Art Reference Manager");

        jsonReader = new JsonReader(JSON_CP);
        jsonWriterCP = new JsonWriter(JSON_CP);
        jsonWriterRF = new JsonWriter(JSON_RF);
        jsonReaderRef = new JsonReaderRef(JSON_RF);
        this.colourPalettes = new ArrayList<>();
        this.referenceFolders = new ArrayList<>();

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ICON.getImage()); // changes the icon of the frame
        setLocation(250, 0);

        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
        loadColourPalettes();
        loadReferenceFolders();
        loadTabs();
        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds tabs to the tabbed pane
    public void loadTabs() {
        JPanel mainTab = new MainTab(this);
        JPanel createColourPaletteTab = new CreateColourPaletteTab(this);
        JPanel createRefFolderTab = new CreateRefFolderTab(this);

        tabbedPane.add(mainTab, 0);
        tabbedPane.setTitleAt(0, "Menu");;
        tabbedPane.add(createColourPaletteTab, 1);
        tabbedPane.setTitleAt(1, "Create Palette");
        tabbedPane.add(createRefFolderTab, 2);
        tabbedPane.setTitleAt(2, "Create Reference Folder");


    }

    // MODIFIES: this
    // EFFECTS: loads list of colour palettes from file
    public void loadColourPalettes() {
        try {
            colourPalettes = jsonReader.read();
            System.out.println("Loaded all the colour palettes from "
                    + JSON_CP + ". Num of cps: " + this.colourPalettes.size());
        } catch (IOException e) {
            System.out.println("Could not load from file: " + JSON_CP);
        }
    }

    //  EFFECTS: Saves all the current created colour palettes to file
    public void saveAllColourPalettes() {
        try {
            jsonWriterCP.open();
            jsonWriterCP.writeListOfColourPalettes(this.colourPalettes);
            jsonWriterCP.close();
            System.out.println("Saved all colour palettes to " + JSON_CP + ". Num of cps: " + colourPalettes.size());
        } catch (FileNotFoundException e) {
            System.out.println("Could not save to file: " + JSON_CP);
        }
    }

    //  EFFECTS: Saves all the current created reference folders to file
    public void saveAllReferenceFolders() {
        try {
            jsonWriterRF.open();
            jsonWriterRF.writeListOfReferenceFolders(this.referenceFolders);
            jsonWriterRF.close();
            System.out.println("Saved all reference folders to " + JSON_RF
                    + ". Num of rfs: " + referenceFolders.size());
        } catch (FileNotFoundException e) {
            System.out.println("Could not save to file: " + JSON_RF);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads list of reference folders from file
    public void loadReferenceFolders() {
        try {
            referenceFolders = jsonReaderRef.read();
            System.out.println("Loaded all the reference folders from " + JSON_RF);
        } catch (IOException e) {
            System.out.println("Could not save to file: " + JSON_RF);
        }
    }

    // EFFECTS: Returns tabbedPane
    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    // EFFECTS: Returns collection of colour palettes
    public List<ColourPalette> getColourPalettes() {
        return this.colourPalettes;
    }

    // EFFECTS: Returns collection of reference folders
    public List<ReferenceFolder> getReferenceFolders() {
        return this.referenceFolders;
    }

    // EFFECTS: Returns height of this frame
    public int getHeight() {
        return HEIGHT;
    }

    // EFFECTS: Returns width of this frame
    public int getWidth() {
        return WIDTH;
    }




}
