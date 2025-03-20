import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameRunningGUI extends JPanel {
    private BufferedImage mapImage;
    private BufferedImage woodTexture;
    private final int MAP_WIDTH, HEIGHT;
    private final String selectedMap;
    private final RunGame runGame;
    private final JLayeredPane layeredPane;
    private final TowerPanel towerPanel;
    private final HomeScreenGUI homeScreenGUI;
    private final Timer gameLoopTimer;
    private int currentRound;
    private int currentCash;
    private int currentHealth;
    public GameRunningGUI(RunGame runGame, int width, int height, String selectedMap, HomeScreenGUI homeScreenGUI) {

        currentRound = 1;
        currentCash = 1000;
        currentHealth = 200;

        this.MAP_WIDTH = width;
        this.HEIGHT = height;
        this.selectedMap = selectedMap;
        this.homeScreenGUI = homeScreenGUI;
        this.runGame = runGame;

        loadMapImage();

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(MAP_WIDTH / 3, 0, MAP_WIDTH, HEIGHT);
        add(layeredPane);

        towerPanel = new TowerPanel(layeredPane, mapImage);
        towerPanel.setBounds(0, 0, 700, 510);
        towerPanel.setOpaque(false); // Ensure transparency
        layeredPane.add(towerPanel, JLayeredPane.PALETTE_LAYER);

        TowerSelectionButtons towerSelectionButtons = new TowerSelectionButtons(runGame, mapImage,towerPanel,layeredPane);
        towerSelectionButtons.setBounds(MAP_WIDTH + MAP_WIDTH / 3 + MAP_WIDTH / 12, MAP_WIDTH/5 + 15, MAP_WIDTH / 6, HEIGHT/2+ HEIGHT / 6);  // Position the button panel on the right
        add(towerSelectionButtons);
        setLayout(null);  // Set layout to null for absolute positioning


        // Add the return home button
        addReturnHomeButton();

        // Load the map and wood texture images
        loadWoodTexture();

        // Create and start the game loop for 60 FPS
        gameLoopTimer = new Timer(16, e -> gameLoop()); // 16 ms per frame = 60 FPS
        gameLoopTimer.start();
    }

    /**
     * Loads the map image based on the selected map.
     */
    private void loadMapImage() {
        String mapFolderPath = "src/MapImg/";
        String imagePath = mapFolderPath + selectedMap + ".png";
        try {
            mapImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath);
            mapImage = null;
        }
    }

    /**
     * Loads the wood texture image.
     */
    private void loadWoodTexture() {
        String woodTexturePath = "src/DesignImg/WoodTextureOne.jpg";
        try {
            woodTexture = ImageIO.read(new File(woodTexturePath));
        } catch (IOException e) {
            System.err.println("Failed to load wood texture image: " + woodTexturePath);
            woodTexture = null;
        }
    }

    /**
     * The game loop. It updates the game state (e.g., towers, balloons) and repaints the screen.
     */
    private void gameLoop() {
        // Update the game state (e.g., spawn towers, balloons, check collisions, etc.)
        updateGameState();

        // Repaint the screen
        repaint();
    }

    /**
     * Updates the game state, such as spawning towers and balloons, etc.
     */
    private void updateGameState() {
        // Implement your tower and balloon logic here
        // For example: spawn new towers, move balloons, check for collisions, etc.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the wood texture on both sides of the map
        final int WOOD_WIDTH = MAP_WIDTH / 3;
        g.drawImage(woodTexture, 0, 0, WOOD_WIDTH, HEIGHT, this);  // Left side
        g.drawImage(woodTexture, MAP_WIDTH + WOOD_WIDTH, 0, WOOD_WIDTH, HEIGHT, this);  // Right side


        // Draw the map image in the center

        g.drawImage(mapImage, WOOD_WIDTH, 0, MAP_WIDTH, HEIGHT, this);

        drawGameInfo(g);

        // You can also draw towers and balloons here
        // e.g., drawTowers(g);
        //       drawBalloons(g);
    }

    private void drawGameInfo(Graphics g) {
        // Set font for the game information
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);

        int xOffset = 10;
        // Current round
        g.drawString("Round: " + currentRound,xOffset, 100);

        // Cash
        g.drawString("Cash: $" + currentCash, xOffset, 130);

        // Health
        g.drawString("Health: " + currentHealth, xOffset, 160);
    }
    /**
     * Adds the return home button.
     */
    private void addReturnHomeButton() {
        // Create the return home button with "X" as the text
        JButton returnHomeButton = new JButton("X");

        // Set the font for the "X" text and color
        returnHomeButton.setFont(new Font("Arial", Font.BOLD, 24));  // Set font size
        returnHomeButton.setForeground(Color.WHITE);  // Default white color for the "X"

        // Set the button's background color to a darker red
        returnHomeButton.setBackground(new Color(150, 0, 0));  // Darker red color

        // Make the button circular
        returnHomeButton.setContentAreaFilled(false);  // Transparent background
        returnHomeButton.setOpaque(true);  // Button opaque to show the background color
        returnHomeButton.setBorder(BorderFactory.createEmptyBorder());  // No border

        // Set the button size to be circular
        int buttonSize = 40;  // Button size
        returnHomeButton.setBounds(10, 10, buttonSize, buttonSize);  // Set position

        // Add an ActionListener to handle the click event
        returnHomeButton.addActionListener(e -> returnHome());

        // Add hover effect to change the text color to yellow
        returnHomeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                returnHomeButton.setForeground(Color.YELLOW);  // Change text color to yellow on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                returnHomeButton.setForeground(Color.WHITE);  // Reset text color when mouse exits
            }
        });

        // Add the button to the panel
        add(returnHomeButton);


        // Revalidate and repaint to make sure the button appears
        revalidate();
        repaint();
    }

    /**
     * Handles the return to home screen.
     */
    private void returnHome() {
        // Stop the game loop timer
        gameLoopTimer.stop();
        runGame.setSize(1024, 768);
        runGame.setContentPane(homeScreenGUI);
        runGame.revalidate();
        runGame.repaint();
    }
}
