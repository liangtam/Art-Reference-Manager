package ui.tabs;

import model.Colour;
import model.ColourPalette;
import persistence.JsonReader;
import ui.PaletteDetailFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ColourPalettesTab extends JPanel {
    private static final String JSON_CP = "./data/colourPalettes.json";
    private JsonReader jsonReader;
    private JPanel paletteDetailPanel;
    private List<ColourPalette> colourPalettes;
    private List<JFrame> cpDetailFrames;

    public ColourPalettesTab() {
        jsonReader = new JsonReader(JSON_CP);
        colourPalettes = new ArrayList<>();
        cpDetailFrames = new ArrayList<>();

        loadColourPalettes();
    }


    // MODIFIES: this
    // EFFECTS: loads list of colour palettes from file
    private void loadColourPalettes() {
        try {
            colourPalettes = jsonReader.read();
            System.out.println("Loaded all the colour palettes from " + JSON_CP);
            renderColourPalettes();
        } catch (IOException e) {
            System.out.println("Could not load from file: " + JSON_CP);
        }
    }

    // EFFECTS: Shows a colour palette image for every colour palette
    public void renderColourPalettes() {
        for (ColourPalette cp: colourPalettes) {
            JButton palette = new JButton(cp.getName());
            palette.setSize(300, 300);
            ImageIcon paletteIcon = new ImageIcon("src/main/resources/artref-cp.png");
            ImageIcon scaledIcon = scaleIcon(paletteIcon, palette);
            System.out.println(scaledIcon.getIconHeight() + scaledIcon.getIconWidth());
            palette.setHorizontalTextPosition(JButton.CENTER);
            palette.setVerticalTextPosition(JButton.BOTTOM);
            palette.setFocusable(false);
            palette.setBorder(BorderFactory.createEmptyBorder());
            palette.setContentAreaFilled(false);
            palette.setIcon(scaledIcon);
            palette.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == palette) {
                        new PaletteDetailFrame(cp);
                    }
                }
            });
            add(palette);
        }
    }

    // EFFECTS: shows the details of one colour palette
//    public void showPaletteDetails(ColourPalette cp) {
//        JFrame frame = new PaletteDetailFrame(cp);
//        cpDetailFrames.add(frame);
//        for (JFrame f: cpDetailFrames) {
//            f.setVisible(true);
//        }
//        cpDetailFrames.clear();
//    }

    public ImageIcon scaleIcon(ImageIcon icon, JButton button) {
        Image img = icon.getImage();
        img = img.getScaledInstance(button.getWidth() / 2, button.getHeight() / 2, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        return scaledIcon;
    }
}
