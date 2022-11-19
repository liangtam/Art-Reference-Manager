package ui.tabs;

import model.Colour;
import model.ColourPalette;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents a page for users to create a colour palette
public class CreateColourPaletteTab extends Tab {
    JLabel labelPaletteName;
    JTextField textPaletteName;
    JLabel labelColourName;
    JLabel labelColourHex;
    JTextField textColourName;
    JTextField textColourHex;
    JButton addColourBtn;
    JButton addPaletteBtn;
    JList<String> colourList;
    DefaultListModel colourListModel;

    private List<Colour> colours;

    // EFFECTS: Creates a tab panel with a form to create a palette and a list of colours the user wants to add to
    //          the palette they're creating
    public CreateColourPaletteTab(MainFrame ui) {
        super(ui);
        setLayout(null);
        colours = new ArrayList<>();

        colourList = new JList();
        colourListModel = new DefaultListModel();
        add(addForm());
        add(addList());
    }

    // EFFECTS: Creates a form that a user can fill in details of a palette
    public JPanel addForm() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 696);

        labelPaletteName = new JLabel("Palette Name: ");
        JLabel label = new JLabel("----------ADD COLOURS----------");
        labelColourName = new JLabel("Colour Name: ");
        labelColourHex = new JLabel("Colour Hex (format #XXXXXX): ");

        labelPaletteName.setBounds(70, 90, 300, 30);
        panel.add(labelPaletteName);
        label.setBounds(100, 180, 900, 30);
        panel.add(label);
        labelColourName.setBounds(70, 280, 300, 30);
        panel.add(labelColourName);
        labelColourHex.setBounds(50, 380, 300, 30);

        panel.add(labelColourHex);
        setTextFields(panel);
        setButtons(panel);

        return panel;
    }

    // EFFECTS: displays a list of colours that the user added so far to the palette they're creating
    public JPanel addList() {
        JPanel panel = new JPanel();
        panel.setBounds(600, 0, 400, 696);
        // JLabel title = new JLabel("Colours of this Palette");
        //title.setFont(new Font("Roboto", Font.BOLD, 16));
        colourList.setVisibleRowCount(8);
        colourList.setModel(colourListModel);
        JScrollPane scrollPane = new JScrollPane(colourList);
        scrollPane.setPreferredSize(new Dimension(150, 500));

        // panel.add(title);
        panel.add(scrollPane);
        return panel;
    }

    // EFFECTS: puts all the necessary text fields for the  user to fill in form
    public void setTextFields(JPanel panel) {
        textPaletteName = new JTextField();
        textPaletteName.setBounds(230, 90, 300, 30);
        panel.add(textPaletteName);
        textColourName = new JTextField();
        textColourName.setBounds(230, 280, 300, 30);
        panel.add(textColourName);
        textColourHex = new JTextField();
        textColourHex.setBounds(230, 380, 300, 30);
        panel.add(textColourHex);

    }

    // EFFECTS: Adds buttons onto the tab
    public void setButtons(JPanel panel) {
        addColourBtn = new JButton("Add Colour");
        addColourBtn.setBackground(new Color(28, 145, 235));
        addColourBtn.setForeground(Color.white);
        addColourBtn.setBounds(220, 470, 150, 30);
        addColourBtn.setFont(new Font("Roboto", Font.BOLD, 16));
        addPaletteBtn = new JButton("Add Palette");
        addPaletteBtn.setBackground(new Color(169, 77, 255));
        addPaletteBtn.setForeground(Color.white);
        addPaletteBtn.setLocation(380, 550);
        addPaletteBtn.setSize(200, 50);
        addPaletteBtn.setFont(new Font("Roboto", Font.BOLD, 20));

        addFunctionalityToButtons();

        panel.add(addColourBtn);
        panel.add(addPaletteBtn);
    }

    // EFFECTS: adds functionality to the buttons in the tab
    @SuppressWarnings("methodlength")
    public void addFunctionalityToButtons() {
        addColourBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addColourBtn) {
                    addColour();
                }
            }
        });

        addPaletteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addPaletteBtn) {
                    String name = textPaletteName.getText();
                    if (colourPaletteAlreadyExists(name)) {
                        System.out.println("Colour palette " + name + " already exists.");
                        textPaletteName.setText("");
                        return;
                    }
                    ColourPalette cp = createColourPalette(name);
                    addColourPalette(cp);
                    colourList.removeAll();
                    colourListModel.removeAllElements();
                    colours.clear();
                    System.out.println("Added cp! Num of cps: " + ui.getColourPalettes().size());
                }
            }
        });
    }

    // EFFECTS: Creates a new colour palette using the information provided by user input
    public ColourPalette createColourPalette(String name) {

        ColourPalette colourPalette = new ColourPalette(name);
        textPaletteName.setText("");

        addColoursToPalette(colourPalette);

        return colourPalette;
    }

    // EFFECTS: creates colour
    public void addColour() {
        String colorName = textColourName.getText();
        String colorHex = textColourHex.getText();
        Colour colour = new Colour(colorName, colorHex);
        colours.add(colour);
        colourListModel.addElement(colour.getName());
        textColourName.setText("");
        textColourHex.setText("");
        System.out.println("Added colour. Num of colours: " + colours.size());
    }

    // EFFECTS: Guides the user on how to add colours to the given colour palette, and adds the colours to
    //          the given colour palette
    public void addColoursToPalette(ColourPalette colourPalette) {
        for (Colour c: colours) {
            colourPalette.addColour(c);
        }
    }

    // EFFECTS: Adds given colour palette to the MainFrame's list of colour palettes
    public void addColourPalette(ColourPalette cp) {
        ui.getColourPalettes().add(cp);
    }

    // EFFECTS: return true if the colour palette we want to add already exists,
    //          else return false
    //          (does not check if it exists as a sub colour palette in another
    //          palette)
    public boolean colourPaletteAlreadyExists(String name) {
        for (ColourPalette cp: ui.getColourPalettes()) {
            if (cp.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

}