package GameMain;

import GameComponents.Menu;
import GameComponents.PanelGame;
import GameComponents.PanelGameDumbBot;
import GameComponents.PanelGameForBot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private Menu menu;
    private PanelGame panelGame;
    private PanelGameForBot panelGameForBot; // New panel for bots
    private PanelGameDumbBot panelGameDumbBot;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JLayeredPane layeredPane; // Layered pane to stack the menu above the game panel

    private void init() {
        setTitle("Java Game");
        setSize(1366, 768);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use CardLayout for panel switching
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);


        // Initialize the Menu and Game Panels
        ActionListener startGameListener = e -> switchToGamePanel();
        ActionListener botButtonListener = e -> switchToBotGamePanel();
        ActionListener DumbbotButtonListener = e -> switchToDumbBotGamePanel();

        menu = new Menu(startGameListener, botButtonListener, DumbbotButtonListener,"/GameImage/background.png");

        panelGame = new PanelGame();
        panelGameForBot = new PanelGameForBot(this); // Create the bot game panel
        panelGameDumbBot = new PanelGameDumbBot();

        // Add Menu and Game panels to the CardLayout
        mainPanel.add(menu, "Menu");
        mainPanel.add(panelGame, "Game");
        mainPanel.add(panelGameForBot, "GameForBot"); // Add bot game panel
        mainPanel.add(panelGameDumbBot, "GameForDumbBot");

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
//        panelGameForBot.resetGame(); // Reset the game state

        cardLayout.show(mainPanel, "GameForBot");
        panelGameForBot.start(); // Start the game loop for the bot game panel (if needed)
    }
    private void switchToDumbBotGamePanel() {
        System.out.println("Switching to Bot Game Panel");
        cardLayout.show(mainPanel, "GameForBot");
        panelGameDumbBot.start(); // Start the game loop for the bot game panel (if needed)
    }
    public void switchToMenu() {
        System.out.println("Switching to Menu Panel");
        cardLayout.show(mainPanel, "Menu"); // Switch to the menu panel
    }

    public static void main(String[] args) {
        new Main().init();
    }
}



