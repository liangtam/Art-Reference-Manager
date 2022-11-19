package ui;

import model.Colour;
import model.ReferenceFolder;
import model.ReferenceImage;

import javax.swing.*;
import java.awt.*;

public class ReferenceFolderDetailFrame extends JFrame {
    private static final ImageIcon FOLDER_ICON = new ImageIcon("src/main/resources/artref-folder.png");
    private JScrollPane scrollPane;
    private JPanel panel;
    private ReferenceFolder rf;

    public ReferenceFolderDetailFrame(ReferenceFolder rf) {
        this.rf = rf;
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
        scrollPane = new JScrollPane();
        panel = new JPanel();
        panel.setSize(this.getSize());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.setVisible(true);
        getContentPane().add(panel);
        add(scrollPane);
        setTitle(rf.getFolderName());
        addRefs();
        setVisible(true);
        setLocation(500, 200);
        setSize(800, 600);
        setIconImage(FOLDER_ICON.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void addRefs() {
        for (ReferenceImage ref: rf.getReferenceImages()) {
            JLabel img = new JLabel();
            img.setSize(500, 500);
            Image image = ref.getImage().getScaledInstance(img.getWidth() / 2,img.getHeight() / 2, Image.SCALE_SMOOTH);
            ImageIcon scaledImg = new ImageIcon(image);
            img.setIcon(scaledImg);
            String text = ref.getName();
            img.setText(text);
            img.setVerticalTextPosition(JLabel.BOTTOM);
            img.setHorizontalTextPosition(JLabel.CENTER);
            panel.add(img);
        }
    }
}
