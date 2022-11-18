package ui;

import model.ColourPalette;
import model.ReferenceFolder;
import persistence.JsonReader;
import persistence.JsonReaderRef;
import persistence.JsonWriter;
import ui.tabs.ColourPalettesTab;
import ui.tabs.CreateColourPaletteTab;
import ui.tabs.CreateRefFolderTab;
import ui.tabs.ReferenceFoldersTab;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 696;

    private static final ImageIcon ICON = new ImageIcon("src/main/resources/artref-icon.png");

    private JTabbedPane tabbedPane;

    public MainFrame() {
        super("Art Reference Manager");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ICON.getImage()); // changes the icon of the frame

        tabbedPane = new JTabbedPane();
        loadTabs();
        getContentPane().add(tabbedPane);
        setVisible(true);
    }

    public void loadTabs() {
        JPanel colourPaletteTab = new ColourPalettesTab();
        JPanel referenceFoldersTab = new ReferenceFoldersTab();
        JPanel createColourPaletteTab = new CreateColourPaletteTab();
        JPanel createRefFolderTab = new CreateRefFolderTab();

        tabbedPane.add(colourPaletteTab, 0);
        tabbedPane.setTitleAt(0, "Palettes");
        tabbedPane.add(referenceFoldersTab, 1);
        tabbedPane.setTitleAt(1, "References");
        tabbedPane.add(createColourPaletteTab, 2);
        tabbedPane.setTitleAt(2, "Create Palette");
        tabbedPane.add(createRefFolderTab, 3);
        tabbedPane.setTitleAt(3, "Create Reference Folder");


    }

    public JTabbedPane getTabbedPane() {
        return this.tabbedPane;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }




}
