package ui;

import model.Colour;
import model.ColourPalette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents all the colour info of a colour palette
public class PaletteDetailFrame extends JFrame {
    private ColourPalette cp;
    private static final ImageIcon PALETTE_ICON = new ImageIcon("src/main/resources/artref-cp.png");
    JButton deleteBtn;
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
        coloursPanel.setBounds(0, 0, 500,300);
        displayPaletteInfo(coloursPanel);
        add(coloursPanel);
        deleteBtn = new JButton("Delete Palette");
        addButtonFunctionality();

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        buttonsPanel.setBounds(0, 300, 500, 200);
        buttonsPanel.add(deleteBtn);
        add(buttonsPanel);
        setVisible(true);
        setLocation(900, 300);
        setSize(500, 500);
        setIconImage(PALETTE_ICON.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // MODIFIES: JPanel
    // Effects: displays all the colours in the colour palette
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

            panel.add(miniColorPanel);
        }
    }

    public void addButtonFunctionality() {
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteBtn) {
                    JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "
                            + " this palette?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
                    mainFrame.getColourPalettes().remove(cp);
                    dispose();
                }
            }
        });
    }
}