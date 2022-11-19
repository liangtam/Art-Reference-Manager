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

// Represents a page holding all the colour palettes created
public class MainTab extends Tab {

    public MainTab(MainFrame frame) {
        super(frame);

        addFunctionalityToButtons();

        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
        renderItems();
    }

    // EFFECTS: adds functionality to any buttons on the tab
    public void addFunctionalityToButtons() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.saveAllColourPalettes();
                ui.saveAllReferenceFolders();
                JOptionPane.showMessageDialog(null,
                        "Saved all colour palettes and reference folders!", "Save success",
                        JOptionPane.PLAIN_MESSAGE);
                renderItems();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.loadColourPalettes();
                ui.loadReferenceFolders();
                JOptionPane.showMessageDialog(null,
                        "Loaded all colour palettes and reference folders!", "Load success",
                        JOptionPane.PLAIN_MESSAGE);
                renderItems();
            }
        });
        setUpSwitchTabButtons();
    }

    public void setUpSwitchTabButtons() {
        addCPBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addCPBtn) {
                    ui.getTabbedPane().setSelectedIndex(1);
                }
            }
        });
        addRefBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addRefBtn) {
                    ui.getTabbedPane().setSelectedIndex(2);
                }
            }
        });
    }

    public void renderItems() {
        renderReferenceFolders();
        renderColourPalettes();
    }

    // EFFECTS: Shows a colour palette image for every colour palette. Each image can be clicked to show more info
    //          about the clicked palette
    public void renderColourPalettes() {
        for (ColourPalette cp: this.ui.getColourPalettes()) {
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
                        new PaletteDetailFrame(cp, ui);
                    }
                }
            });
            add(palette);
        }
    }

    public void renderReferenceFolders() {
        this.removeAll();
        for (ReferenceFolder rf: this.ui.getReferenceFolders()) {
            JButton refFolder = new JButton(rf.getFolderName());
            refFolder.setSize(300, 300);
            ImageIcon rfIcon = new ImageIcon("src/main/resources/artref-folder.png");
            ImageIcon scaledIcon = scaleIcon(rfIcon, refFolder);
            refFolder.setHorizontalTextPosition(JButton.CENTER);
            refFolder.setVerticalTextPosition(JButton.BOTTOM);
            refFolder.setFocusable(false);
            refFolder.setBorder(BorderFactory.createEmptyBorder());
            refFolder.setContentAreaFilled(false);
            refFolder.setIcon(scaledIcon);
            refFolder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == refFolder) {
                        new ReferenceFolderDetailFrame(rf);
                    }
                }
            });
            add(refFolder);
        }
    }
}