package ui.tabs;

import model.ReferenceFolder;
import model.ReferenceImage;
import ui.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class CreateRefFolderTab extends Tab {
    JLabel labelFolderName;
    JTextField textFolderName;
    JLabel labelImageName;
    JLabel labelImageURL;
    JTextField textImageName;
    JButton uploadImgBtn;
    JButton addImageBtn;
    JButton addFolderBtn;
    JPanel leftPanel;
    JPanel rightPanel;
    JList<String> imageList;
    JFileChooser fc = new JFileChooser();
    private String imageFileURL;
    DefaultListModel imageListModel;

    private List<ReferenceImage> refImages;

    public CreateRefFolderTab(MainFrame ui) {
        super(ui);
        setLayout(null);
        refImages = new ArrayList<>();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 600, 696);
//        rightPanel.setLayout(null);
//        rightPanel.setBounds(400, 0, 600, 696);

        imageList = new JList();
        imageListModel = new DefaultListModel();
        addForm();
        add(addList());
        add(leftPanel);
        add(rightPanel);

    }

    // MODIFIES: this
    // EFFECTS: adds a form for a user to fill in
    public void addForm() {

        labelFolderName = new JLabel("Folder Name: ");
        JLabel label = new JLabel("----------ADD IMAGES----------");
        labelImageName = new JLabel("Image Name: ");
        labelImageURL = new JLabel("Upload image:");

        labelFolderName.setBounds(70, 90, 300, 30);
        leftPanel.add(labelFolderName);
        label.setBounds(100, 180, 900, 30);
        leftPanel.add(label);
        labelImageName.setBounds(70, 280, 300, 30);
        leftPanel.add(labelImageName);
        labelImageURL.setBounds(50, 380, 300, 30);

        leftPanel.add(labelImageURL);
        setTextFields();
        setButtons();
    }

    // MODIFIES: this
    // EFFECTS: displays a list of image names that the user added so far to the folder they're creating
    public JPanel addList() {
        JPanel panel = new JPanel();
        panel.setBounds(600, 0, 400, 696);
        // JLabel title = new JLabel("Colours of this Palette");
        //title.setFont(new Font("Roboto", Font.BOLD, 16));
        imageList.setVisibleRowCount(8);
        imageList.setModel(imageListModel);
        JScrollPane scrollPane = new JScrollPane(imageList);
        scrollPane.setPreferredSize(new Dimension(150, 500));

        // panel.add(title);
        panel.add(scrollPane);
        return panel;
    }

    // EFFECTS: puts all the necessary text fields for the  user to fill in form
    public void setTextFields() {
        textFolderName = new JTextField();
        textFolderName.setBounds(230, 90, 300, 30);
        leftPanel.add(textFolderName);
        textImageName = new JTextField();
        textImageName.setBounds(230, 280, 300, 30);
        leftPanel.add(textImageName);
    }

    public void setButtons() {
        addImageBtn = new JButton("Add Image");
        addImageBtn.setBackground(new Color(28, 145, 235));
        addImageBtn.setForeground(Color.white);
        addImageBtn.setBounds(220, 470, 150, 30);
        addImageBtn.setFont(new Font("Roboto", Font.BOLD, 16));
        addFolderBtn = new JButton("Add Folder");
        addFolderBtn.setBackground(new Color(169, 77, 255));
        addFolderBtn.setForeground(Color.white);
        addFolderBtn.setLocation(380, 550);
        addFolderBtn.setSize(200, 50);
        addFolderBtn.setFont(new Font("Roboto", Font.BOLD, 20));
        uploadImgBtn = new JButton("Upload Image");
        uploadImgBtn.setFocusable(false);
        uploadImgBtn.setBackground(new Color(28, 145, 235));
        uploadImgBtn.setForeground(Color.white);
        uploadImgBtn.setBounds(230, 380, 200, 40);

        addFunctionalityToButtons();

        leftPanel.add(addImageBtn);
        leftPanel.add(addFolderBtn);
        leftPanel.add(uploadImgBtn);
    }

    @SuppressWarnings("methodlength")
    public void addFunctionalityToButtons() {
        addImageBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addImageBtn) {
                    addImage();
                }
            }
        });

        addFolderBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addFolderBtn) {
                    String name = textFolderName.getText();
                    if (refFolderAlreadyExists(name)) {
                        System.out.println("Reference folder " + name + " already exists.");
                        textFolderName.setText("");
                        return;
                    }
                    ReferenceFolder rf = createReferenceFolder(name);
                    addReferenceFolder(rf);
                    imageList.removeAll();
                    imageListModel.removeAllElements();
                    refImages.clear();
                    System.out.println("Added rf! Num of rfs: " + ui.getReferenceFolders().size());
                }
            }
        });

        uploadImgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == uploadImgBtn) {
                    int returnVal = fc.showOpenDialog(CreateRefFolderTab.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        String fileURL = file.getAbsolutePath();
                        imageFileURL = fileURL;

                        try {
                            ImageIcon img = new ImageIcon(ImageIO.read(new File(imageFileURL)));
                            Image scaleImg = img.getImage();
                            scaleImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                            img = new ImageIcon(scaleImg);
                            JLabel label = new JLabel(img);
                            label.setLocation(280, 280);
                            leftPanel.add(label);
                        } catch (Exception err) {
                            JOptionPane.showMessageDialog(null, "Invalid file!",
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            }
        });
    }

    // EFFECTS: Creates a new colour palette using the information provided by user input
    public ReferenceFolder createReferenceFolder(String name) {

        ReferenceFolder referenceFolder = new ReferenceFolder(name);
        textFolderName.setText("");

        addImagesToFolder(referenceFolder);

        return referenceFolder;
    }

    // EFFECTS: creates refImage
    public void addImage() {
        String imageName = textImageName.getText();
        ReferenceImage referenceImage = new ReferenceImage(imageName, imageFileURL);
        refImages.add(referenceImage);
        imageListModel.addElement(referenceImage.getName());
        textImageName.setText("");
        System.out.println("Added colour. Num of colours: " + refImages.size());
    }

    // EFFECTS: Guides the user on how to add colours to the given colour palette, and adds the colours to
    //          the given colour palette
    public void addImagesToFolder(ReferenceFolder referenceFolder) {
        for (ReferenceImage r: refImages) {
            referenceFolder.addRef(r);
        }
    }

    // EFFECTS: Adds given reference folder to the MainFrame's list of reference folders
    public void addReferenceFolder(ReferenceFolder rf) {
        ui.getReferenceFolders().add(rf);
    }

    // EFFECTS: return true if the reference folder we want to add already exists,
    //          else return false
    //          (does not check if it exists as a sub reference folder in another
    //          folder)
    public boolean refFolderAlreadyExists(String name) {
        for (ReferenceFolder rf: ui.getReferenceFolders()) {
            if (rf.getFolderName().equals(name)) {
                return true;
            }
        }

        return false;
    }
}
