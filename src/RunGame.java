import javax.swing.*;


/**
 * RunGame serves as the main entry point for the Bloons Tower Defense game.
 * It initializes the game window, manages transitions between different game screens,
 * and starts the game.
 * This class includes the main method to launch the game and methods to transition
 * to different game screens (HomeScreen, Map Selection, and Game Running).
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class RunGame extends JFrame {

    private static final int SCREEN_WIDTH = 1024;   // Width of the game window
    private static final int SCREEN_HEIGHT = 768;   // Height of the game window

    public SoundEffect mainThemeMusic;
    private final HomeScreenGUI homeScreenGUI;  // HomeScreenGUI instance reference

    /**
     * Constructor for RunGame.
     * Initializes the game window with a specific title, size, layout, and default close operation.
     * Also initializes and plays background music and displays the home screen.
     */
    public RunGame() {
        setTitle("Bloons Tower Defense");  // Set the title of the window
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);  // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set default close operation to exit on close
        setLayout(null);  // Disable layout manager to manually set component positions

        // Initialize and play background music
        mainThemeMusic = new SoundEffect("maintheme.wav", true, 1f);  // Looping background music

        // Initialize and show the home screen
        homeScreenGUI = new HomeScreenGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT);
        homeScreenGUI.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);  // Set bounds for the home screen
        add(homeScreenGUI);  // Add the home screen component to the JFrame

        setVisible(true);  // Make the window visible
    }

    /**
     * Starts the game by transitioning to the GameRunningGUI screen with the selected map.
     * Adjusts the window size based on the game map and switches the content pane to the game screen.
     *
     * @param selectedMap The name of the selected map to be played.
     * @param width       The width of the game map.
     * @param height      The height of the game map.
     */
    public void startGame(String selectedMap, int width, int height) {
        // Adjust window size based on map dimensions
        setSize(width + 2 * (width / 3), height + height / 18);

        getContentPane().removeAll();  // Remove previous content
        repaint();  // Repaint the window

        // Initialize and show the game screen
        GameRunningGUI gameRunningGUI = new GameRunningGUI(this, width, height, selectedMap, homeScreenGUI);
        setContentPane(gameRunningGUI);  // Set GameRunningGUI as the new content pane

        revalidate();  // Revalidate the layout
        repaint();  // Repaint the content to reflect changes
    }

    /**
     * Displays the map selection screen, allowing the player to choose a map before starting the game.
     */
    public void showMapSelectionScreen() {
        // Initialize and display the map selection screen
        MapSelectionGUI mapSelectionGUI = new MapSelectionGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT,
                new HomeScreenGUI(this, getWidth(), getHeight()));
        setContentPane(mapSelectionGUI);  // Set MapSelectionGUI as the new content pane
        revalidate();  // Revalidate the layout
        repaint();  // Repaint the content to reflect changes
    }

    /**
     * Main method that initializes and starts the game.
     * It creates an instance of RunGame to begin the application.
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {
        new RunGame();  // Create and start the RunGame application
    }
}
