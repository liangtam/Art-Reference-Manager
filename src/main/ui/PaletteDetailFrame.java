package ui;

import model.Colour;
import model.ColourPalette;

import javax.swing.*;
import java.awt.*;

public class PaletteDetailFrame extends JFrame {
    public PaletteDetailFrame(ColourPalette cp) {
        setLayout(new FlowLayout());
        JLabel name = new JLabel(cp.getName());
        add(name);
        for (Colour colour: cp.getColours()) {
            JLabel color = new JLabel();
            color.setBackground(colour.getColourVisual());
            color.setSize(100, 100);
            String text = colour.getName() + "\n" + colour.getHex();
            color.setText(text);
            add(color);
        }
        setVisible(true);
        setLocation(900, 500);
        setSize(500, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
