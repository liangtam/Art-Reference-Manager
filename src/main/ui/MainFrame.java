package ui;

import model.ColourPalette;
import model.Event;
import model.EventLog;
import model.ReferenceFolder;
import persistence.JsonReader;
import persistence.JsonReaderRef;
import persistence.JsonWriter;
import ui.tabs.MainTab;
import ui.tabs.CreateColourPaletteTab;
import ui.tabs.CreateRefFolderTab;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

    // EFFECTS: Constructs a JFrame and sets up components on the JFrame
    public MainFrame() {
        super("Art Reference Manager");
        initializeFields();
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        WindowListener listener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                int result = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Close the application?");
                if (result == JOptionPane.OK_OPTION) {
                    printLogs();
                    MainFrame.this.dispose();
                }
            }
        };
        addWindowListener(listener);
        setIconImage(ICON.getImage()); // changes the icon of the frame
        setLocation(250, 0);
        setUpTabbedPane();
        loadComponents();
        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads all colour palettes, reference folders, and tabs
    private void loadComponents() {
        loadColourPalettes();
        loadReferenceFolders();
        loadTabs();
    }

    // MODIFIES: this
    // EFFECTS: initializes all the fields in the class
    private void initializeFields() {
        jsonReader = new JsonReader(JSON_CP);
        jsonWriterCP = new JsonWriter(JSON_CP);
        jsonWriterRF = new JsonWriter(JSON_RF);
        jsonReaderRef = new JsonReaderRef(JSON_RF);
        this.colourPalettes = new ArrayList<>();
        this.referenceFolders = new ArrayList<>();
    }

    // EFFECTS: prints all event logs to console
    public void printLogs() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up a tabbed pane
    private void setUpTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.TOP);
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
        } catch (FileNotFoundException e) {
            System.out.println("Could not save to file: " + JSON_RF);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads list of reference folders from file
    public void loadReferenceFolders() {
        try {
            referenceFolders = jsonReaderRef.read();
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
