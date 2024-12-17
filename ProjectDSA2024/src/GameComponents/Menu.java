//package GameComponents;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
//import static java.awt.SystemColor.menu;
//
//public class Menu extends JPanel {
//    public JButton startButton;
//    private JButton quitButton;
//    private PanelGame panelGame;
//
//    public Menu() {
//        setLayout(new BorderLayout());
//
//        // Create the start button
//        startButton = new JButton("Start Game");
//        startButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                // Implement the logic for starting the game here
//                // For example, you might call a method to switch to the game panel
//                System.out.println("Start Game button clicked");
//                setVisible(false);
//
//                panelGame = new PanelGame();
//                add(panelGame, BorderLayout.CENTER);
//// Add PanelGame to the center
//                addWindowListener(new WindowAdapter() {
//                    @Override
//                    public void windowOpened(WindowEvent e) {
//                        panelGame.start();
//                        revalidate();
//                        repaint();
//                    }
//                });
//            }
//        });
//        add(startButton, BorderLayout.NORTH); // Add Start Button to the north
//
//                // Create the quit button
//                quitButton = new JButton("Quit Game");
//                quitButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        // Implement the logic for quitting the game here
//                        // For example, you might call System.exit(0) to exit the application
//                        System.out.println("Quit Game button clicked");
//                        System.exit(0);
//                    }
//                });
//        add(quitButton, BorderLayout.SOUTH); // Add Quit Button to the south
//    }
//}


package GameComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class Menu extends JPanel {
    private JButton startButton;
    private JButton quitButton;
    private JButton botButton;
    private JButton dumbbotButton;
    private final Image backgroundImage;

    public Menu(ActionListener startGameListener, ActionListener botButtonListener, ActionListener DumbbotButtonListener, String backgroundImagePath) {
        setLayout(new BorderLayout());

        // Load the background image
        backgroundImage = loadImage(backgroundImagePath);
        if (backgroundImage == null) {
            System.err.println("Failed to load background image: " + backgroundImagePath);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Arrange buttons vertically
        buttonPanel.setOpaque(false); // Make the panel transparent so the background shows
        Dimension buttonSize = new Dimension(150, 40);

        // Create the start button
        startButton = new JButton("Start Game");
        startButton.setPreferredSize(buttonSize);
        startButton.setMaximumSize(buttonSize);
        startButton.addActionListener(startGameListener);
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing

        // Create the bot button
        botButton = new JButton("Bot 1");
        botButton.setPreferredSize(buttonSize);
        botButton.setMaximumSize(buttonSize);
        botButton.addActionListener(botButtonListener);
        buttonPanel.add(botButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing

        //Create the dumb bot button
        dumbbotButton = new JButton("Bot dumb");
        dumbbotButton.setPreferredSize(buttonSize);
        dumbbotButton.setMaximumSize(buttonSize);
        dumbbotButton.addActionListener(DumbbotButtonListener);
        buttonPanel.add(dumbbotButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add sp


        // Create the quit button
        quitButton = new JButton("Quit Game");
        quitButton.setPreferredSize(buttonSize);
        quitButton.setMaximumSize(buttonSize);
        quitButton.addActionListener(e -> {
            System.out.println("Quit Game button clicked");
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to quit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        buttonPanel.add(quitButton);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(250, 600, 50, 50)); // Adjust as needed
        add(buttonPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the background image if available
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private Image loadImage(String path) {
        try {
            URL resource = getClass().getResource(path); // Load image as resource
            if (resource != null) {
                return new ImageIcon(resource).getImage();
            } else {
                System.err.println("Image resource not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
}
