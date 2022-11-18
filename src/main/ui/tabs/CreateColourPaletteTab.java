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
import java.util.Scanner;

public class CreateColourPaletteTab extends CPTabs {
    JLabel labelPaletteName;
    JTextField textPaletteName;
    JLabel labelColourName;
    JLabel labelColourHex;
    JTextField textColourName;
    JTextField textColourHex;
    JButton addColorBtn;
    JButton addPaletteBtn;
    JList<String> colourList;
    DefaultListModel colourListModel;

    private List<Colour> colours;

    @SuppressWarnings("methodlength")
    public CreateColourPaletteTab() {
        super();
        setLayout(null);
        colours = new ArrayList<>();

        colourList = new JList();
        colourListModel = new DefaultListModel();
        add(addForm());
        add(addList());


    }

    public JPanel addForm() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 696);

        labelPaletteName = new JLabel("Palette Name: ");
        JLabel label = new JLabel("----------ADD COLOURS----------");
        labelColourName = new JLabel("Colour Name: ");
        labelColourHex = new JLabel("Colour Hex (format #XXXXXX): ");

        labelPaletteName.setBounds(50, 90, 300, 30);
        panel.add(labelPaletteName);
        label.setBounds(100, 180, 900, 30);
        panel.add(label);
        labelColourName.setBounds(50, 280, 300, 30);
        panel.add(labelColourName);
        labelColourHex.setBounds(50, 380, 300, 30);

        panel.add(labelColourHex);
        setTextFields(panel);
        setButtons(panel);

        return panel;
    }

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

    public void setTextFields(JPanel panel) {
        textPaletteName = new JTextField();
        textPaletteName.setBounds(130, 90, 300, 30);
        panel.add(textPaletteName);
        textColourName = new JTextField();
        textColourName.setBounds(130, 280, 300, 30);
        panel.add(textColourName);
        textColourHex = new JTextField();
        textColourHex.setBounds(230, 380, 300, 30);
        panel.add(textColourHex);

    }

    @SuppressWarnings("methodlength")
    public void setButtons(JPanel panel) {
        addColorBtn = new JButton("Add Colour");
        addColorBtn.setBackground(new Color(28, 145, 235));
        addColorBtn.setForeground(Color.white);
        addColorBtn.setBounds(220, 470, 150, 30);
        addColorBtn.setFont(new Font("Roboto", Font.BOLD, 16));
        addPaletteBtn = new JButton("Add Palette");
        addPaletteBtn.setBackground(new Color(169, 77, 255));
        addPaletteBtn.setForeground(Color.white);
        addPaletteBtn.setLocation(380, 550);
        addPaletteBtn.setSize(200, 50);
        addPaletteBtn.setFont(new Font("Roboto", Font.BOLD, 20));
        panel.add(addColorBtn);

        addColorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addColorBtn) {
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
                    System.out.println("Added cp! Num of cps: " + colourPalettes.size());
                }
            }
        });
        panel.add(addPaletteBtn);
    }

    // EFFECTS: Creates a new colour palette using the information provided by user input
    public ColourPalette createColourPalette(String name) {

        ColourPalette colourPalette = new ColourPalette(name);
        textPaletteName.setText("");

        processAddColours(colourPalette);

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
    public void processAddColours(ColourPalette colourPalette) {
        for (Colour c: colours) {
            colourPalette.addColour(c);
        }
    }

    public void addColourPalette(ColourPalette cp) {
        this.colourPalettes.add(cp);
    }

    // EFFECTS: return true if the colour palette we want to add already exists,
    //          else return false
    //          (does not check if it exists as a sub colour palette in another
    //          palette)
    public boolean colourPaletteAlreadyExists(String name) {
        for (ColourPalette cp: colourPalettes) {
            if (cp.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

}
