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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    private JButton startButton;
    private JButton quitButton;
    private JButton botButton;

    public Menu(ActionListener startGameListener,ActionListener botButtonListener) {
        setLayout(new BorderLayout());

        // Create the start button
        startButton = new JButton("Start Game");
        startButton.addActionListener(startGameListener); // Delegate to listener passed by Main
        add(startButton, BorderLayout.NORTH);

        //create the bot buttom
        botButton = new JButton("Bot 1");
        botButton.addActionListener(botButtonListener); // Delegate to botButtonListener
        add(botButton, BorderLayout.CENTER);

        // Create the quit button
        quitButton = new JButton("Quit Game");
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
        add(quitButton, BorderLayout.SOUTH);
    }
}
