import javax.swing.*;
/*
Filename: RunGame.java
Authors: Jace Claassen and Joe Farrier
Description:
 */
public class RunGame extends JFrame {

    private static final int SCREEN_WIDTH = 1400;
    private static final int SCREEN_HEIGHT = 1080;
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

    public void startGame(int selectedMap) {
        System.out.println("Game starting with map index: " + selectedMap);
        // TODO: Transition to actual game screen
        JOptionPane.showMessageDialog(this, "Game starts with Map " + selectedMap);
    }

    public void showMapSelectionScreen() {
        // Clear the current panel and show the map selection screen
        getContentPane().removeAll();  // Remove current content
        // Keep reference to MapSelectionGUI
        MapSelectionGUI mapSelectionScreen = new MapSelectionGUI(this, SCREEN_WIDTH, SCREEN_HEIGHT, homeScreenGUI);  // Pass homeScreen reference
        mapSelectionScreen.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        add(mapSelectionScreen);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        new RunGame();
    }
}
