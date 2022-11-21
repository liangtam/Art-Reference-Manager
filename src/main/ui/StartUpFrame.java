package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents start up screen of the application
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

        JButton startButton = new JButton();
        startButton.setBounds(360, 500, 250, 50);
        startButton.setText("Start");
        startButton.setFont(new Font("Roboto", Font.BOLD, 20));
        startButton.setBackground(new Color(28, 145, 235));
        startButton.setForeground(Color.white);
        startButton.setFocusable(false);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
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
        frame.setLocation(250, 0);
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        bg.add(startButton);
        contentPane.add(bg);
    }
}
