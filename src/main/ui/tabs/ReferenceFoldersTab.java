package ui.tabs;

import model.ReferenceFolder;
import persistence.JsonReader;
import persistence.JsonReaderRef;
import ui.ReferenceFolderDetailFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReferenceFoldersTab extends JPanel {
    private static final String JSON_RF = "./data/referenceFolders.json";
    private JsonReaderRef jsonReader;

    private List<ReferenceFolder> referenceFolders;

    public ReferenceFoldersTab() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 3, 3));
        referenceFolders = new ArrayList<>();
        jsonReader = new JsonReaderRef(JSON_RF);
        placeButtons();

        loadReferenceFolders();
        renderReferenceFolders();
    }

    public void placeButtons() {

    }

    public void renderReferenceFolders() {
        for (ReferenceFolder rf: referenceFolders) {
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

    public void showFolderDetails(ReferenceFolder rf) {

    }

    // MODIFIES: this
    // EFFECTS: loads list of reference folders from file
    private void loadReferenceFolders() {
        try {
            referenceFolders = jsonReader.read();
            System.out.println("Loaded all the reference folders from " + JSON_RF);
        } catch (IOException e) {
            System.out.println("Could not save to file: " + JSON_RF);
        }
    }

    public ImageIcon scaleIcon(ImageIcon icon, JButton button) {
        Image img = icon.getImage();
        img = img.getScaledInstance(button.getWidth() / 2, button.getHeight() / 2, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        return scaledIcon;
    }
}
