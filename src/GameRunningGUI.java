import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents the graphical user interface (GUI) for the running game.
 * It handles the display of game elements, such as the map, towers, balloons, and projectiles,
 * and updates their states during the game loop.
 */
public class GameRunningGUI extends JPanel {
    private BufferedImage mapImage, woodTexture, heartsImage, moneySignImage;
    private final int MAP_WIDTH, HEIGHT;
    private final String selectedMap;
    private final RunGame runGame;
    private final JLayeredPane layeredPane;
    private final TowerPanel towerPanel;
    private final HomeScreenGUI homeScreenGUI;
    private final Timer gameLoopTimer;
    private final Waypoints waypoints;
    private BufferedImage[] balloonImages;
    private final ArrayList<Balloon> balloons;
    private final ArrayList<Tower> placedTowers;
    private final ArrayList<Projectile> curProjectiles;
    private int currentCash, currentHealth;
    private WaveManager waveManager;
    private boolean waveInProgress;
    private int bloonsRemainingInWave;
    private JButton playButton, sellButton;
    private boolean addEndRoundCash;
    private final TowerSelectionButtons towerSelectionButtons;
    private boolean gameInProgress;
    private boolean promptedForRestart = false;
    private boolean paused;
    private Timer spawnTimer;
    private boolean finalWaveSpawned;
    private Tower clickedTower = null;

    /**
     * Constructs a new GameRunningGUI object.
     * Initializes the game state, loads images, and sets up the layout.
     *
     * @param runGame       The main game object responsible for running the game.
     * @param width         The width of the game window.
     * @param height        The height of the game window.
     * @param selectedMap   The name of the map being played.
     * @param homeScreenGUI The home screen GUI to navigate back to.
     */
    public GameRunningGUI(RunGame runGame, int width, int height, String selectedMap, HomeScreenGUI homeScreenGUI) {
        currentCash = 10000;
        currentHealth = 100;

        this.MAP_WIDTH = width;
        this.HEIGHT = height;
        this.selectedMap = selectedMap;
        this.homeScreenGUI = homeScreenGUI;
        this.runGame = runGame;
        this.waypoints = new Waypoints(selectedMap);
        this.balloons = new ArrayList<>();
        this.placedTowers = new ArrayList<>();
        this.curProjectiles = new ArrayList<>();
        this.gameInProgress = true;

        loadImages();
        loadBalloonImages();

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(MAP_WIDTH / 3, 0, MAP_WIDTH, HEIGHT);
        add(layeredPane);

        towerPanel = new TowerPanel(layeredPane, placedTowers, this);

        towerPanel.setBounds(0, 0, 700, 520);
        towerPanel.setOpaque(false);
        layeredPane.add(towerPanel, JLayeredPane.PALETTE_LAYER);


        towerPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new SoundEffect("Click.wav", false, 1f);

                // Get the X and Y coordinates of the mouse click
                int x = e.getX() - 14;
                int y = e.getY() - 14;

                // Initialize closestTower and closestDistance for this click
                clickedTower = null;
                double closestDistance = Double.MAX_VALUE;

                // Loop through the list of placed towers
                for (Tower tower : placedTowers) {
                    // Assuming Tower has a getPosition() method that returns a Point
                    Point towerPosition = tower.getPosition();
                    int towerX = towerPosition.x;
                    int towerY = towerPosition.y;

                    // Calculate the distance between the mouse click and the tower
                    double distance = Math.sqrt(Math.pow(x - towerX, 2) + Math.pow(y - towerY, 2));

                    // If the distance is within 30 pixels and smaller than the closest found so far
                    if (distance <= 40 && distance < closestDistance) {
                        closestDistance = distance;
                        clickedTower = tower;  // Set the current tower as the closest one
                        sellButton.setVisible(true);
                    }
                }

                // If a closest tower is found within the range, do something with it
                if (clickedTower != null) {
                    sellButton.setText("Sell: $" + (int) (clickedTower.getCost() * 0.8));
                    sellButton.setVisible(true);
                } else {
                    clickedTower = null;
                    sellButton.setVisible(false);
                }
            }
        });

        addMouseListener(new MouseAdapter() {     // Add a MouseListener to detect clicks anywhere on the screen
            public void mouseClicked(MouseEvent e) {
                new SoundEffect("Click.wav", false, 1f);
            }
        });

        waveManager = new WaveManager();
        waveInProgress = false;
        bloonsRemainingInWave = 0;

        towerSelectionButtons = new TowerSelectionButtons(runGame, mapImage, towerPanel, layeredPane, this);
        towerSelectionButtons.setBounds(MAP_WIDTH + MAP_WIDTH / 3 + MAP_WIDTH / 12, 80, MAP_WIDTH / 6, HEIGHT / 2);
        add(towerSelectionButtons);
        setLayout(null);

        addPlayButton();
        addResetButton();
        addPauseButton();
        addSellButton();
        addReturnHomeButton();

        // Game loop timer (60 FPS)
        gameLoopTimer = new Timer(16, e -> gameLoop());
        gameLoopTimer.start();
    }

    /**
     * Loads the map and design images from the specified file paths.
     * If any image fails to load, an error message is printed.
     */
    private void loadImages() {
        String imagePath = "src/MapImg/" + selectedMap + ".png";
        try {
            mapImage = ImageIO.read(new File(imagePath));
            woodTexture = ImageIO.read(new File("src/DesignImg/WoodTextureOne.jpg"));
            heartsImage = ImageIO.read(new File("src/DesignImg/hearts.png"));
            moneySignImage = ImageIO.read(new File("src/DesignImg/moneySign.png"));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath);
            mapImage = null;
        }
    }

    /**
     * Loads the balloon images from the specified file paths.
     * If any image fails to load, an error message is printed.
     */
    private void loadBalloonImages() {
        String[] paths = {
                "src/BalloonImages/redbloon.png",
                "src/BalloonImages/bluebloon.png",
                "src/BalloonImages/greenbloon.png",
                "src/BalloonImages/yellowbloon.png",
                "src/BalloonImages/pinkbloon.png",
                "src/BalloonImages/zebrabloon.png",
                "src/BalloonImages/rainbowbloon.png",
                "src/BalloonImages/ceramicbloon.png",
                "src/BalloonImages/moab.png",
                "src/BalloonImages/metalbloon.png",
                "src/BalloonImages/pop.png",
        };

        balloonImages = new BufferedImage[paths.length];

        for (int i = 0; i < paths.length; i++) {
            try {
                balloonImages[i] = ImageIO.read(new File(paths[i]));
            } catch (IOException e) {
                System.err.println("Failed to load balloon image: " + paths[i]);
                balloonImages[i] = null;
            }
        }
    }

    /**
     * Starts the next wave of balloons if the current wave is finished and there are no balloons remaining.
     */
    private void startNextWave() {
        // Only start the wave if the balloons list is empty
        if (balloons.isEmpty()) {
            addEndRoundCash = true;
            waveInProgress = true;

            Wave currentWave = waveManager.getCurrentWave();
            bloonsRemainingInWave = 0;

            for (BloonSpawnInfo bloonInfo : currentWave.getBloons()) {
                bloonsRemainingInWave += bloonInfo.getAmount();
                spawnBloonsForWave(bloonInfo);
            }
        }
    }

    /**
     * Spawns balloons for the current wave at a specified rate.
     *
     * @param bloonInfo The information about the balloons to spawn.
     */
    private void spawnBloonsForWave(BloonSpawnInfo bloonInfo) {
        spawnTimer = new Timer((int) (bloonInfo.getSpawnRate() * 1000), e -> {
            // Check if there are still balloons left to spawn in this group
            if (bloonInfo.getAmount() > 0) {
                // Spawn a balloon and decrement the amount
                balloons.add(new Balloon(waypoints, balloonImages, bloonInfo.getStrength()));
                bloonInfo.setAmount(bloonInfo.getAmount() - 1);  // Decrement the amount of balloons
                bloonsRemainingInWave--;  // Decrement remaining balloons in wave
            }

            // Check if all balloons have been spawned
            if (bloonsRemainingInWave == 0 && bloonInfo.getAmount() == 0) {
                ((Timer) e.getSource()).stop(); // Stop the timer once all balloons are spawned
                waveManager.nextWave(); // Move to the next wave
                if (waveManager.hasNextWave()) {
                    waveInProgress = false;
                    startNextWave(); // Start the next wave
                } else {
                    finalWaveSpawned = true;
                }
            }
        });
        spawnTimer.start();  // Start the timer
    }

    /**
     * The main game loop. It updates the game state and repaints the screen at 60 frames per second (FPS).
     */
    private void gameLoop() {
        if (paused) {
            JOptionPane.showConfirmDialog(
                    this,
                    "Game is paused. Click OK to continue.",
                    "Paused",
                    JOptionPane.DEFAULT_OPTION
            );

            paused = false; // Resume the game after dialog is closed
            return; // Skip rest of this loop, continue next frame
        }

        if (finalWaveSpawned && balloons.isEmpty() && !promptedForRestart) {
            promptedForRestart = true;
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Congratulations! You defeated all of the waves! Play Again?",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                returnHome(); // Or go back to a menu if you have one
            }
        }

        if (!gameInProgress && !promptedForRestart) {
            promptedForRestart = true;
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Game over! Would you like to play again?",
                    "Play Again?",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                restartGame();
            } else {
                returnHome(); // Or go back to a menu if you have one
            }
        }

        updateGameState();
        repaint();
    }


    /**
     * Updates the game state by processing the positions of balloons and the targeting and firing of towers.
     */
    private void updateGameState() {
        for (int i = 0; i < balloons.size(); i++) {
            Balloon balloon = balloons.get(i);
            balloon.updatePosition();
            if (balloon.hasReachedEnd()) {
                balloons.remove(i);
                i--;
                currentHealth -= (balloon.getLevel() + 1);
            }
        }
        if (currentHealth <= 0) {
            gameInProgress = false;
        }

        // All towers will check which balloons are in range and target the best one.
        for (Tower tower : placedTowers) {
            if (!tower.targets.isEmpty()) {
                tower.targets.removeIf(b -> !tower.inRange(b));
            }
            // Step 1: Invalidate current target if it’s no longer in range
            Balloon currentTarget = tower.getTarget();
            if (currentTarget != null && !tower.inRange(currentTarget)) {
                currentTarget.unGoo();
                tower.setTarget(null);
            }

            // Step 2: Try to find a better target
            Balloon bestTarget = null;
            for (Balloon balloon : balloons) {
                if (tower.inRange(balloon) && !balloon.isHidden()) {
                    if (tower.towerType.equals("Ice")) {
                        tower.addTarget(balloon);
                    }
                    if (bestTarget == null || compareBalloons(balloon, bestTarget) > 0) {
                        bestTarget = balloon;
                    }
                }
            }

            tower.setTarget(bestTarget);

            // Step 3: Fire if there is a valid target
            if (tower.getTarget() != null && tower.isReadyToFire()) {
                tower.fire(tower.getTarget(), curProjectiles);
            }
        }

        // Process projectiles
        Iterator<Projectile> projectiles = curProjectiles.iterator();
        while (projectiles.hasNext()) {
            Projectile p = projectiles.next();
            p.update();
            if (p.missed()) {
                projectiles.remove();
                continue;
            }

            // Check if any balloon is hit
            Iterator<Balloon> balloonIterator = balloons.iterator();
            while (balloonIterator.hasNext()) {
                Balloon b = balloonIterator.next();
                p.didHit(b);
                if (b.isHit()) {
                    ArrayList<Balloon> balloonsToTakeDamage = new ArrayList<>();
                    if(p.getType() == ProjectileImageSize.BOMB){
                        double explosionRadius = 27.0;

                        // Get the center of the impact (you can also get this from the projectile if it stores it)
                        double explosionX = b.getX() + 13;
                        double explosionY = b.getY() + 16;

                        for (Balloon other : balloons) {
                            double otherX = other.getX() + 13;
                            double otherY = other.getY() + 16;

                            double distance = Math.hypot(explosionX - otherX, explosionY - otherY);

                            if (distance <= explosionRadius && !other.isHidden()) {
                                balloonsToTakeDamage.add(other);
                            }
                        }
                    }
                    p.removeOneFromHitCount();
                    p.setTracking(false);
                    if (b.getType() == BalloonType.LEAD) {
                        // Only allow damage to lead if the projectile is from the wizard
                        if (p.getType() == ProjectileImageSize.ORB) {
                            b.takeDamage(229);
                        }
                    } else {
                        // All other balloon types can be damaged by any projectile
                        b.takeDamage(p.getDamage());
                    }

                      // Apply damage to the balloon
                    for(Balloon balloon : balloonsToTakeDamage){
                        if(balloon.getType() != BalloonType.LEAD){
                            balloon.takeDamage(p.getDamage());
                        } else {
                            balloon.takeDamage(229);
                        }
                        currentCash++;
                    }
                    currentCash++;
                    // If the balloon is popped, remove it from the list
                    if (b.isPopped()) {
                        new SoundEffect("Pop1.wav", false, .8f);
                        balloonIterator.remove();
                    } else {
                        b.gotHit(false);
                    }

                    if (!p.isStillValid()) {
                        p.setTracking(false);
                    }
                    // If the projectile has no remaining hits, remove it
                    if (p.getRemainingHits() <= 0) {
                        projectiles.remove();
                        break;  // Exit loop after projectile hits its target(s)
                    }
                }
            }
        }
    }

    /**
     * Compares two balloons to determine which one is the better target.
     * Balloons are compared based on their segment index and strength.
     *
     * @param b1 The first balloon.
     * @param b2 The second balloon.
     * @return 1 if b1 is a better target than b2, 0 otherwise.
     */
    public int compareBalloons(Balloon b1, Balloon b2) {
        if (b1.getCurrentSegmentIndex() > b2.getCurrentSegmentIndex()) {
            return 1;
        } else if (b1.getCurrentSegmentIndex() == b2.getCurrentSegmentIndex() && b1.getLevel() > b2.getLevel()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Paints the game components on the screen.
     * This includes drawing the game map, towers, balloons, projectiles, and game info (health, cash).
     * It also handles the rendering of tower rotations, hit ranges, and projectile movements.
     *
     * @param g The graphics context in which to paint.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        final int WOOD_WIDTH = MAP_WIDTH / 3;

        // Draw the map image.
        g.drawImage(mapImage, WOOD_WIDTH, 0, MAP_WIDTH, HEIGHT, this);

        // Draw each placed tower and its respective hit range (if applicable).
        for (Tower tower : placedTowers) {
            int drawX = tower.xPosition + WOOD_WIDTH;
            int drawY = tower.yPosition;
            int imgWidth = tower.getImgWidth();
            int imgHeight = tower.getImgHeight();
            // Draw the tower's image, with rotation if it has a target.
            if (tower.getTarget() != null && tower.isRotatable) {
                double angle = tower.getAngle(tower.getTarget().getX(), tower.getTarget().getY());
                AffineTransform originalTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle), drawX + imgWidth / 2.0, drawY + imgHeight / 2.0);
                g2d.drawImage(tower.towerImage, drawX, drawY, imgWidth, imgHeight, this);
                g2d.setTransform(originalTransform);
            } else {
                g2d.drawImage(tower.towerImage, drawX, drawY, imgWidth, imgHeight, this);
                if (tower.towerType.equals("Ice") && tower.animateAttack) {
                    Color color = new Color(173, 216, 230, 150);
                    g2d.setColor(color);
                    int xOffset = (tower.getDiameter() / 2) - (tower.getImgWidth() / 2) - 233;
                    int yOffset = (tower.getDiameter() / 2) - (tower.getImgHeight() / 2);
                    g2d.fillOval(tower.xPosition - xOffset, tower.yPosition - yOffset, tower.getDiameter(), tower.getDiameter());
                    tower.setAnimateAttackFalse();
                }
            }
        }
        if (clickedTower != null) {
            Color color = new Color(128, 128, 128, 128);
            g2d.setColor(color);
            int xOffset = (clickedTower.getDiameter() / 2) - (clickedTower.getImgWidth() / 2) - 233;
            int yOffset = (clickedTower.getDiameter() / 2) - (clickedTower.getImgHeight() / 2);
            g2d.fillOval(clickedTower.xPosition - xOffset, clickedTower.yPosition - yOffset, clickedTower.getDiameter(), clickedTower.getDiameter());
        }
        // Draw balloons.
        for (Balloon balloon : balloons) {
            if (!balloon.isHidden()) {
                balloon.draw(g);
            }
        }

        // Draw projectiles.
        for (Projectile p : curProjectiles) {
            double drawX = p.currentX + WOOD_WIDTH;
            AffineTransform projectileTransform = g2d.getTransform();
            g2d.rotate(p.getAngle(), (int) drawX, p.currentY);
            g2d.drawImage(p.getImage(), (int) drawX, (int) p.currentY, p.getWidth(), p.getHeight(), this);
            g2d.setTransform(projectileTransform);
        }

        // Draw the wood texture on both sides of the map.
        g.drawImage(woodTexture, 0, 0, WOOD_WIDTH, HEIGHT, this);
        g.drawImage(woodTexture, MAP_WIDTH + WOOD_WIDTH, 0, WOOD_WIDTH, HEIGHT, this);

        // Draw the game info (health, cash, wave status).
        drawGameInfo(g);
    }

    /**
     * Draws the game information (health, cash, and wave status) on the screen.
     *
     * @param g The graphics context in which to draw the game info.
     */
    private void drawGameInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.setColor(Color.WHITE);
        int xOffset = 10;

        // Format the cash value with commas for better readability.
        String formattedCash = String.format("%,d", currentCash);

        // Draw hearts image and health text.
        g.drawImage(heartsImage, xOffset, 180, 30, 30, this); // Image width and height can be adjusted.
        g.drawString("Health: " + currentHealth, xOffset + 35, 205);

        // Draw money sign image and cash text.
        g.drawImage(moneySignImage, xOffset, 120, 30, 30, this); // Image width and height can be adjusted.
        g.drawString("Cash: $" + formattedCash, xOffset + 35, 145);

        if (towerSelectionButtons.getDisplayTowerName().equals("Towers")) {
            g.drawString(towerSelectionButtons.getDisplayTowerName(), 1000, 30);
        } else {
            g.drawString(towerSelectionButtons.getDisplayTowerName(), 975, 30);
        }

        g.drawImage(moneySignImage, 1000, 40, 30, 30, this);
        g.drawString(String.valueOf(towerSelectionButtons.getDisplayTowerCost()), 1035, 65);

        g.setFont(new Font("Arial", Font.PLAIN, 14));
        String description = loadTowerDescription(towerSelectionButtons.getDisplayTowerName());
        drawWrappedString(g, description, 960, 360, 205);  // Draw below header, wrap at 180 pixels

        // Check if the wave has ended and update the play button text accordingly.
        if (balloons.isEmpty() && !waveInProgress) {
            if (addEndRoundCash) {
                currentCash += 200;
                addEndRoundCash = false;
            }
            playButton.setText("Start Wave " + (waveManager.getCurrentWaveIndex() + 1));
            playButton.setEnabled(true);
            playButton.setVisible(true);
        } else {
            playButton.setEnabled(false);
            playButton.setVisible(false);
        }
    }

    /**
     * Adds the play button to the GUI, which starts the next wave when clicked.
     */
    private void addPlayButton() {
        playButton = new JButton("Start Wave " + (waveManager.getCurrentWaveIndex() + 1));
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.setBounds(80, 10, 140, 40);
        playButton.setFocusPainted(false);

        // Add an action listener to start the next wave when clicked.
        playButton.addActionListener(e -> {
            if (!waveInProgress) {
                new SoundEffect("Click.wav", false, 1f);
                startNextWave();
            }
        });

        add(playButton);
    }

    private void addResetButton() {
        JButton resetButton = new JButton("Reset Game");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBounds(47, 430, 140, 40);
        resetButton.setFocusPainted(false);

        // Add an action listener to start the next wave when clicked.
        resetButton.addActionListener(e -> {
            new SoundEffect("Click.wav", false, 1f);
            restartGame();
        });

        add(resetButton);
    }

    private void addPauseButton() {
        JButton pauseButton = new JButton("Pause Game");
        pauseButton.setFont(new Font("Arial", Font.BOLD, 14));
        pauseButton.setBounds(47, 370, 140, 40);
        pauseButton.setFocusPainted(false);

        // Add an action listener to start the next wave when clicked.
        pauseButton.addActionListener(e -> {
            new SoundEffect("Click.wav", false, 1f);
            paused = true;
        });

        add(pauseButton);
    }

    private void addSellButton() {
        sellButton = new JButton("Sell Tower");
        sellButton.setFont(new Font("Arial", Font.BOLD, 14));
        sellButton.setBounds(47, 300, 140, 40); // Position on screen
        sellButton.setFocusPainted(false);
        sellButton.setContentAreaFilled(true);
        sellButton.setOpaque(true);
        sellButton.setBackground(new Color(255, 102, 102)); // Light red
        sellButton.setVisible(false); // Initially hidden

        sellButton.addActionListener(e -> {
            new SoundEffect("Sell.wav", false, 1f);
            if (clickedTower != null) {
                currentCash += (int) (clickedTower.getCost() * 0.8);
                placedTowers.remove(clickedTower);
                clickedTower = null;
                sellButton.setVisible(false);
                repaint(); // Refresh display
            }
        });

        add(sellButton);
    }


    /**
     * Adds a return home button to the GUI.
     * Clicking this button returns the user to the home screen.
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

        // Change the button's color on mouse hover.
        returnHomeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                returnHomeButton.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnHomeButton.setForeground(Color.WHITE);
            }
        });

        add(returnHomeButton);
    }

    /**
     * Handles returning to the home screen. Stops the game loop and switches to the home screen GUI.
     */
    private void returnHome() {
        gameLoopTimer.stop();
        runGame.setSize(1024, 768);
        runGame.setContentPane(homeScreenGUI);
        runGame.revalidate();
        runGame.repaint();
    }

    public int getCurrentCash() {
        return currentCash;
    }

    public void setCurrentCash(int cash) {
        currentCash -= cash;
    }

    private void restartGame() {
        this.balloons.clear();
        this.placedTowers.clear();
        this.curProjectiles.clear();
        this.currentHealth = 100;
        this.clickedTower = null;
        waveManager = new WaveManager();
        waveInProgress = false;
        gameInProgress = true;
        promptedForRestart = false;
        finalWaveSpawned = false;
        if (spawnTimer != null) {
            this.currentCash = 800;
            spawnTimer.stop();
        } else {
            this.currentCash = 1000;
        }
    }

    public String loadTowerDescription(String towerName) {
        StringBuilder description = new StringBuilder();
        try {
            File file = new File("src/Info/" + towerName + ".txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                description.append(line).append(" ");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return "Description not available.";
        }
        return description.toString().trim();
    }

    private void drawWrappedString(Graphics g, String text, int x, int y, int maxWidth) {
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        int curY = y;

        for (String word : words) {
            String testLine = line + word + " ";
            int lineWidth = fm.stringWidth(testLine);
            if (lineWidth > maxWidth) {
                g.drawString(line.toString(), x, curY);
                line = new StringBuilder(word + " ");
                curY += lineHeight;
            } else {
                line.append(word).append(" ");
            }
        }
        // Draw the last line
        if (!line.toString().isEmpty()) {
            g.drawString(line.toString(), x, curY);
        }
    }
}
