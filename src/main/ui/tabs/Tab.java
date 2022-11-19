package ui.tabs;

import model.ColourPalette;
import model.ReferenceFolder;
import ui.MainFrame;
import ui.PaletteDetailFrame;
import ui.ReferenceFolderDetailFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a tab panel
public abstract class Tab extends JPanel {
    protected MainFrame ui;
    protected static final ImageIcon SAVE_ICON = new ImageIcon("src/main/resources/artref_save.png");
    protected static final ImageIcon LOAD_ICON = new ImageIcon("src/main/resources/artref_load.png");
    protected static final ImageIcon ADD_REF_ICON = new ImageIcon("src/main/resources/artref-addfolder.png");
    protected static final ImageIcon ADD_CP_ICON = new ImageIcon("src/main/resources/artref-addcp.png");
    protected JButton saveButton;
    protected JButton loadButton;
    protected JButton addRefBtn;
    protected JButton addCPBtn;

    // EFFECTS: Constructs a tab panel with given MainFrame as its frame
    Tab(MainFrame ui) {
        this.ui = ui;
        saveButton = new JButton();
        loadButton = new JButton();
        addRefBtn = new JButton();
        addCPBtn = new JButton();
        setUpButtons();
        this.ui.add(saveButton);
        this.ui.add(loadButton);
        this.ui.add(addCPBtn);
        this.ui.add(addRefBtn);
        setLayout(null);
    }

    public void setUpButtons() {
        addCPBtn.setBounds(700, 570, 100, 100);
        ImageIcon scaledAddCPIcon = scaleIcon(ADD_CP_ICON, addCPBtn);
        addCPBtn.setIcon(scaledAddCPIcon);
        addCPBtn.setContentAreaFilled(false);
        addCPBtn.setBorder(BorderFactory.createEmptyBorder());

        addRefBtn.setBounds(770, 570, 100, 100);
        ImageIcon scaledAddRefIcon = scaleIcon(ADD_REF_ICON, addRefBtn);
        addRefBtn.setIcon(scaledAddRefIcon);
        addRefBtn.setContentAreaFilled(false);
        addRefBtn.setBorder(BorderFactory.createEmptyBorder());

        loadButton.setBounds(840, 570, 100, 100);
        ImageIcon scaledLoadIcon = scaleIcon(LOAD_ICON, loadButton);
        loadButton.setIcon(scaledLoadIcon);
        loadButton.setContentAreaFilled(false);
        loadButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setBounds(900, 570, 100, 100);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        ImageIcon scaledIcon = scaleIcon(SAVE_ICON, saveButton);
        saveButton.setIcon(scaledIcon);
        saveButton.setContentAreaFilled(false);
    }

    // EFFECTS: Scales an image icon to fit given button's size
    protected ImageIcon scaleIcon(ImageIcon icon, JButton button) {
        Image img = icon.getImage();
        img = img.getScaledInstance(button.getWidth() / 2, button.getHeight() / 2, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        return scaledIcon;
    }

    public abstract void addFunctionalityToButtons();

}