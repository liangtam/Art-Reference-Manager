package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUpFrame extends JFrame {
    private JFrame frame;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 696;

    @SuppressWarnings("methodlength")
    StartUpFrame() {
        frame = new JFrame();
        Container contentPane = frame.getContentPane();

        ImageIcon icon = new ImageIcon("src/main/resources/artref-icon.png");
        ImageIcon backgroundImage = new ImageIcon("src/main/resources/artref-bg.png");
        JLabel bg = new JLabel(backgroundImage);

        JButton loadButton = new JButton();
        loadButton.setBounds(360, 500, 250, 50);
        loadButton.setText("Start");
        loadButton.setFont(new Font("Roboto", Font.BOLD, 20));
        loadButton.setBackground(new Color(28, 145, 235));
        loadButton.setForeground(Color.white);
        loadButton.setFocusable(false);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadButton) {
                    frame.dispose();
                    new MainFrame();
                }
            }
        });

        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Art Reference Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        bg.add(loadButton);
        contentPane.add(bg);
    }
}
