package ui;

import model.Colour;
import model.ColourPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Represents all the colour info of a colour palette
public class PaletteDetailFrame extends JFrame {
    private ColourPalette cp;
    private static final ImageIcon PALETTE_ICON = new ImageIcon("src/main/resources/artref-cp.png");
    JButton deleteBtn;
    JButton clearBtn;
    MainFrame mainFrame;

    // MODIFIES: this
    // EFFECTS: constructs a frame containing all colour info of given colour palette
    public PaletteDetailFrame(ColourPalette cp, MainFrame mainFrame) {
        this.cp = cp;
        this.mainFrame = mainFrame;
        setLayout(null);
        setTitle(cp.getName());
        JPanel coloursPanel = new JPanel();
        coloursPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        coloursPanel.setBounds(0, 0, 500,400);
        displayPaletteInfo(coloursPanel);
        add(coloursPanel);
        deleteBtn = new JButton("Delete Palette");
        clearBtn = new JButton("Clear Colours");
        addButtonFunctionality();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBounds(0, 400, 500, 100);
        buttonsPanel.add(deleteBtn);
        buttonsPanel.add(clearBtn);
        add(buttonsPanel);
        setVisible(true);
        setLocation(900, 300);
        setSize(500, 500);
        setIconImage(PALETTE_ICON.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // MODIFIES: JPanel
    // Effects: displays all the colours in the colour palette
    @SuppressWarnings("methodlength")
    public void displayPaletteInfo(JPanel panel) {
        for (Colour colour: cp.getColours()) {
            JPanel miniColorPanel = new JPanel();
            miniColorPanel.setLayout(new BoxLayout(miniColorPanel, BoxLayout.PAGE_AXIS));
            miniColorPanel.setPreferredSize(new Dimension(80, 80));

            JPanel color = new JPanel();
            color.setOpaque(true);
            color.setBackground(colour.getColourVisual());
            color.setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
            color.setPreferredSize(new Dimension(50, 50));
            color.setLayout(null);

            JLabel colourName = new JLabel(colour.getName());
            JLabel colourHex = new JLabel(colour.getHex());
            miniColorPanel.add(color);
            miniColorPanel.add(colourName);
            miniColorPanel.add(colourHex);

            miniColorPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Would you like to delete "
                                    + colour.getName() + " from this palette?",
                            "Delete?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        JOptionPane.showMessageDialog(null, "Success! Click the save button"
                                + " to save your changes.");
                        cp.deleteColour(colour.getName());
                    }
                }
            });

            panel.add(miniColorPanel);
        }
    }

    // MODIFIES: this, MainFrame
    // EFFECTS: adds button functionality to buttons
    @SuppressWarnings("methodlength")
    public void addButtonFunctionality() {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteBtn) {
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
                            + " this palette?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        mainFrame.getColourPalettes().remove(cp);
                        dispose();
                    }
                }
            }
        });
        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == clearBtn) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete "
                            + " all colours from this palette?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        cp.clearColours();
                        dispose();
                    }
                }
            }
        });
    }
}