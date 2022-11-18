package ui.tabs;

import model.Colour;
import ui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreateColourPaletteTab extends JPanel {
    JLabel labelPaletteName;
    JTextField paletteName;
    JLabel labelColourName;
    JLabel labelColourHex;
    JTextField colourName;
    JTextField colourHex;
    JButton addColorBtn;
    JButton addPaletteBtn;

    private String name;
    private List<Colour> colours;

    public CreateColourPaletteTab() {
        setLayout(null);
        labelPaletteName = new JLabel("Palette Name: ");
        JLabel label = new JLabel("----------ADD COLOURS----------");
        labelColourName = new JLabel("Colour Name: ");
        labelColourHex = new JLabel("Colour Hex (format #XXXXXX): ");

        labelPaletteName.setBounds(200, 0, 100, 30);
        add(labelPaletteName);
        label.setBounds(500, 50, 100, 30);
        add(label);
        labelColourName.setBounds(200, 150, 100, 30);
        add(labelColourName);
        labelColourHex.setBounds(200, 250, 100, 30);
        add(labelColourHex);

        paletteName = new JTextField();
        paletteName.setBounds(200, 200, 300, 30);
        add(paletteName);

    }


}
