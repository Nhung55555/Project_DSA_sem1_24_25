//package GameMain;
//
//import GameComponents.PanelGame;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//
//public class Main extends JFrame {
//    public Main(){
//        init();
//    }
//    private void init(){
//        setTitle("Java Game");
//        setSize(1366,768);
//        setResizable(false);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//        PanelGame panelGame = new PanelGame();
//        add(panelGame);
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowOpened(WindowEvent e){
//                panelGame.start();
//            }
//        });
//    }
//
//    public static void main(String[] args) {
//        Main main = new Main();
//        main.setVisible(true);
//    }
//}



package GameMain;

import GameComponents.Menu;
import GameComponents.PanelGame;
import GameComponents.PanelGameForBot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private Menu menu;
    private PanelGame panelGame;
    private PanelGameForBot panelGameForBot; // New panel for bots

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private void init() {
        setTitle("Java Game");
        setSize(1366, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use CardLayout for panel switching
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize the Menu and Game Panels
//        menu = new Menu(e -> switchToGamePanel());
        ActionListener startGameListener = e -> switchToGamePanel();
        ActionListener botButtonListener = e -> switchToBotGamePanel();
        menu = new Menu(startGameListener, botButtonListener);

        panelGame = new PanelGame();
        panelGameForBot = new PanelGameForBot(); // Create the bot game panel



        // Add Menu and Game panels to the CardLayout
        mainPanel.add(menu, "Menu");
        mainPanel.add(panelGame, "Game");
        mainPanel.add(panelGameForBot, "GameForBot"); // Add bot game panel

        // Add mainPanel to the JFrame
        add(mainPanel);
        cardLayout.show(mainPanel, "Menu"); // Show the menu by default

        setVisible(true);
    }

    private void switchToGamePanel() {
        System.out.println("Switching to Game Panel");
        cardLayout.show(mainPanel, "Game");
        panelGame.start();
    }
    private void switchToBotGamePanel() {
        System.out.println("Switching to Bot Game Panel");
        cardLayout.show(mainPanel, "GameForBot");
        panelGameForBot.start(); // Start the game loop for the bot game panel (if needed)
    }

    public static void main(String[] args) {
        new Main().init();
    }
}
