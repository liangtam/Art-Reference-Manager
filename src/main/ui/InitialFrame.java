package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialFrame extends JFrame {

    @SuppressWarnings("methodlength")
    InitialFrame() {
        JFrame frame = new JFrame();

        ImageIcon icon = new ImageIcon("src/main/resources/artref-icon.png");
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/artref-bg.png");
        JLabel bg = new JLabel(backgroundImage);
        bg.setSize(1000, 696);

        JButton loadButton = new JButton();
        loadButton.setBounds(200, 100, 250, 50);
        loadButton.setText("Start");
        loadButton.setFont(new Font("Roboto", Font.BOLD, 20));
        loadButton.setBackground(new Color(28, 145, 235));
        loadButton.setForeground(Color.white);
        loadButton.setFocusable(false);
        loadButton.setHorizontalAlignment(JButton.CENTER);
        loadButton.setVerticalAlignment(JButton.CENTER);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadButton) {

                    frame.dispose();
                    MainFrame main = new MainFrame();
                }
            }
        });

        frame.setSize(1000, 696);
        frame.setTitle("Art Reference Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setIconImage(icon.getImage());
        bg.add(loadButton);
        frame.add(bg);
    }
}
