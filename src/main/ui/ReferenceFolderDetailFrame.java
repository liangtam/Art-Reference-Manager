package ui;

import model.Colour;
import model.ReferenceFolder;
import model.ReferenceImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Represents the details of a reference folder (all its photos)
public class ReferenceFolderDetailFrame extends JFrame {
    private static final ImageIcon FOLDER_ICON = new ImageIcon("src/main/resources/artref-folder.png");
    private JScrollPane scrollPane;
    private JPanel panel;
    private JPanel btnPanel;
    JButton deleteBtn;
    JButton clearBtn;
    MainFrame mainFrame;
    private ReferenceFolder rf;

    // MODIFIES: this
    // EFFECTS: constructs a frame displaying all the photos in given reference folder
    public ReferenceFolderDetailFrame(ReferenceFolder rf, MainFrame mainFrame) {
        this.rf = rf;
        setLayout(null);
        scrollPane = new JScrollPane();
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 3));
        panel.setBounds(0, 0, 800, 500);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.setVisible(true);
        getContentPane().add(panel);
        add(scrollPane);
        setTitle(rf.getFolderName());
        addRefs();
        addButtons();
        setVisible(true);
        setLocation(500, 200);
        setSize(800, 600);
        setIconImage(FOLDER_ICON.getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    // MODIFIES: this
    // EFFECTS: displays all the reference images of given folder
    @SuppressWarnings("methodlength")
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
            img.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Would you like to delete the image "
                                    + ref.getName() + " from this folder?",
                            "Delete?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        JOptionPane.showMessageDialog(null, "Success! Click the save button"
                                + " to save your changes.");
                        rf.deleteRef(ref);
                    }
                }
            });
            panel.add(img);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates button panel and adds buttons to button panel
    public void addButtons() {
        btnPanel = new JPanel();
        btnPanel.setBounds(0, 500, 800, 100);
        btnPanel.setLayout(new FlowLayout());
        deleteBtn = new JButton("Delete Folder");
        clearBtn = new JButton("Clear Images");
        addButtonFunctionality();
        btnPanel.add(deleteBtn);
        btnPanel.add(clearBtn);
        this.add(btnPanel);
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
                            + " this folder?", "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);

                    if (result == JOptionPane.OK_OPTION) {
                        mainFrame.getReferenceFolders().remove(rf);
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
                                    + " all images from this folder?",
                            "Are you sure?", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        rf.clearImages();
                        dispose();
                    }
                }
            }
        });
    }
}
