package ui.tabs;

import model.Colour;
import model.ColourPalette;
import persistence.JsonReader;
import ui.MainFrame;
import ui.PaletteDetailFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ColourPalettesTab extends CPTabs {
    private MainFrame ui;
    private static final ImageIcon SAVE_ICON = new ImageIcon("src/main/resources/artref_save.png");
    private static final ImageIcon LOAD_ICON = new ImageIcon("src/main/resources/artref_load.png");
    JButton saveButton;
    JButton loadButton;

    public ColourPalettesTab(MainFrame ui) {
        super();
        this.ui = ui;
        saveButton = new JButton();
        loadButton = new JButton();
        loadButton.setBounds(850, 570, 100, 100);
        ImageIcon scaledLoadIcon = scaleIcon(LOAD_ICON, loadButton);
        loadButton.setIcon(scaledLoadIcon);
        loadButton.setContentAreaFilled(false);
        loadButton.setBorder(BorderFactory.createEmptyBorder());
        saveButton.setBounds(900, 570, 100, 100);
        saveButton.setBorder(BorderFactory.createEmptyBorder());
        ImageIcon scaledIcon = scaleIcon(SAVE_ICON, saveButton);
        saveButton.setIcon(scaledIcon);
        saveButton.setContentAreaFilled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveAllColourPalettes();
                renderColourPalettes();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadColourPalettes();
                renderColourPalettes();
            }
        });
        this.ui.add(saveButton);
        this.ui.add(loadButton);
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
        renderColourPalettes();
    }

    // EFFECTS: Shows a colour palette image for every colour palette
    public void renderColourPalettes() {
        this.removeAll();
        for (ColourPalette cp: colourPalettes) {
            JButton palette = new JButton(cp.getName());
            palette.setSize(300, 300);
            ImageIcon paletteIcon = new ImageIcon("src/main/resources/artref-cp.png");
            ImageIcon scaledIcon = scaleIcon(paletteIcon, palette);
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

    // EFFECTS: returns list of colour palettes
    public List<ColourPalette> getColourPalettes() {
        return colourPalettes;
    }
}
