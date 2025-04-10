import javax.swing.*;
import java.awt.event.*;

/**
 * Filename: RunGame.java
 * Authors: Jace Claassen and Joe Farrier
 * Description: This class serves as the main entry point for the Bloons Tower Defense game.
 * It initializes the game window and manages the transition between different game screens.
 */

public class RunGame extends JFrame {

    private static final int SCREEN_WIDTH = 1024;
    private static final int SCREEN_HEIGHT = 768;

    private final HomeScreenGUI homeScreenGUI;  // Keep reference to HomeScreen

    /**
     * Constructor for RunGame.
     * Initializes the game window and displays the home screen.
     */
    public RunGame() {
        setTitle("Bloons Tower Defense");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Initialize background music and start it looping
        SoundEffect backgroundMusic = new SoundEffect("maintheme.wav", true, 1f);  // Set background music to loop with volume 0.5 (can be adjusted)

        // Initialize and show the home screen
        homeScreenGUI = new HomeScreenGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT);
        homeScreenGUI.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        add(homeScreenGUI);

        setVisible(true);
    }

    /**
     * Starts the game by switching to the GameRunningGUI screen.
     *
     * @param selectedMap The name of the selected map.
     * @param width       The width of the game map.
     * @param height      The height of the game map.
     */
    public void startGame(String selectedMap, int width, int height) {
        // Adjust the window size as needed
        setSize(width + 2 * (width / 3), height + height / 18);

        getContentPane().removeAll();
        repaint();
        // Switch to the game screen
        GameRunningGUI gameRunningGUI = new GameRunningGUI(this, width, height, selectedMap, homeScreenGUI);
        setContentPane(gameRunningGUI);  // Switch content pane to GameRunningGUI

        revalidate();  // Revalidate the layout
        repaint();  // Repaint the content

        // Lower the volume of the background music when the game starts (Optional)
    }

    /**
     * Displays the map selection screen, allowing the player to choose a map before starting the game.
     */
    public void showMapSelectionScreen() {
        MapSelectionGUI mapSelectionGUI = new MapSelectionGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT,
                new HomeScreenGUI(this, getWidth(), getHeight()));
        setContentPane(mapSelectionGUI);
        revalidate();
        repaint();
    }

    /**
     * Main method that initializes and starts the game.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new RunGame();
    }
}

