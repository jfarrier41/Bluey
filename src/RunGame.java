import javax.swing.*;
/*
Filename: RunGame.java
Authors: Jace Claassen and Joe Farrier
Description:
 */
public class RunGame extends JFrame {

    private static final int SCREEN_WIDTH = 1024;
    private static final int SCREEN_HEIGHT = 768;




    private final HomeScreenGUI homeScreenGUI;  // Keep reference to HomeScreen

    public RunGame() {
        setTitle("Bloons Tower Defense");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Initialize and show the home screen
        homeScreenGUI = new HomeScreenGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT);
        homeScreenGUI.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        add(homeScreenGUI);

        setVisible(true);
    }

    // Method to show the GameRunningGUI screen and pass the selected map
    public void startGame(String selectedMap,int width,int height) {
        setSize(width + 2 * (width/3), height + height / 18);
        GameRunningGUI gameRunningGUI = new GameRunningGUI(this, width, height, selectedMap,homeScreenGUI);  // Pass selected map name to the constructor
        setContentPane(gameRunningGUI);  // Switch content pane to GameRunningGUI
        revalidate();  // Revalidate the layout
        repaint();  // Repaint the content
    }

    // Method to start the map selection screen
    public void showMapSelectionScreen() {
        MapSelectionGUI mapSelectionGUI = new MapSelectionGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT, new HomeScreenGUI(this, getWidth(), getHeight()));
        setContentPane(mapSelectionGUI);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new RunGame();
    }
}
