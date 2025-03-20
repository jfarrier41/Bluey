import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Authors: Jace Claassen and Joseph Farrier
 * Description: The GameRunningGUI class represents the main game screen in the Tower Defense game.
 * It handles the rendering of the selected map, tower placement, and game statistics.
 * Additionally, it runs a game loop at 60 FPS to update and repaint the game state.
 */

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

    /**
     * Constructs a GameRunningGUI object, initializing the game environment.
     *
     * @param runGame        The main game frame.
     * @param width          The width of the game map.
     * @param height         The height of the game map.
     * @param selectedMap    The name of the selected map.
     * @param homeScreenGUI  The home screen GUI for navigation.
     */
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

        TowerSelectionButtons towerSelectionButtons = new TowerSelectionButtons(runGame, mapImage, towerPanel, layeredPane);
        towerSelectionButtons.setBounds(MAP_WIDTH + MAP_WIDTH / 3 + MAP_WIDTH / 12, MAP_WIDTH / 5 + 15, MAP_WIDTH / 6, HEIGHT / 2 + HEIGHT / 6);
        add(towerSelectionButtons);
        setLayout(null);  // Set layout to null for absolute positioning

        addReturnHomeButton();
        loadWoodTexture();

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
     * Loads the wood texture image for the side panels.
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
     * The main game loop. Updates the game state and repaints the screen at 60 FPS.
     */
    private void gameLoop() {
        updateGameState();
        repaint();
    }

    /**
     * Updates the game state, such as spawning towers and balloons.
     */
    private void updateGameState() {
        // Implement your tower and balloon logic here.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int WOOD_WIDTH = MAP_WIDTH / 3;
        g.drawImage(woodTexture, 0, 0, WOOD_WIDTH, HEIGHT, this);  // Left side
        g.drawImage(woodTexture, MAP_WIDTH + WOOD_WIDTH, 0, WOOD_WIDTH, HEIGHT, this);  // Right side
        g.drawImage(mapImage, WOOD_WIDTH, 0, MAP_WIDTH, HEIGHT, this);
        drawGameInfo(g);
    }

    /**
     * Draws game information such as round number, cash, and health.
     *
     * @param g The graphics object used for drawing.
     */
    private void drawGameInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        int xOffset = 10;
        g.drawString("Round: " + currentRound, xOffset, 100);
        g.drawString("Cash: $" + currentCash, xOffset, 130);
        g.drawString("Health: " + currentHealth, xOffset, 160);
    }

    /**
     * Adds a return home button to the GUI.
     */
    private void addReturnHomeButton() {
        JButton returnHomeButton = new JButton("X");
        returnHomeButton.setFont(new Font("Arial", Font.BOLD, 24));
        returnHomeButton.setForeground(Color.WHITE);
        returnHomeButton.setBackground(new Color(150, 0, 0));
        returnHomeButton.setContentAreaFilled(false);
        returnHomeButton.setOpaque(true);
        returnHomeButton.setBorder(BorderFactory.createEmptyBorder());
        returnHomeButton.setBounds(10, 10, 40, 40);
        returnHomeButton.addActionListener(e -> returnHome());

        returnHomeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                returnHomeButton.setForeground(Color.YELLOW);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                returnHomeButton.setForeground(Color.WHITE);
            }
        });

        add(returnHomeButton);
        revalidate();
        repaint();
    }

    /**
     * Handles the return to the home screen.
     */
    private void returnHome() {
        gameLoopTimer.stop();
        runGame.setSize(1024, 768);
        runGame.setContentPane(homeScreenGUI);
        runGame.revalidate();
        runGame.repaint();
    }
}
