import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GameRunningGUI extends JPanel {
    private BufferedImage mapImage, woodTexture;
    private final int MAP_WIDTH, HEIGHT;
    private final String selectedMap;
    private final RunGame runGame;
    private final JLayeredPane layeredPane;
    private final TowerPanel towerPanel;
    private final AnimationPanel animationPanel;
    private final HomeScreenGUI homeScreenGUI;
    private final Timer gameLoopTimer;
    private final Waypoints waypoints;
    private BufferedImage[] balloonImages;
    private ArrayList<Balloon> balloons;
    private int currentRound, currentCash, currentHealth;
    private final WaveManager waveManager;
    private boolean waveInProgress;
    private int bloonsRemainingInWave;
    private JButton playButton;

    public GameRunningGUI(RunGame runGame, int width, int height, String selectedMap, HomeScreenGUI homeScreenGUI) {
        currentCash = 1000;
        currentHealth = 200;

        this.MAP_WIDTH = width;
        this.HEIGHT = height;
        this.selectedMap = selectedMap;
        this.homeScreenGUI = homeScreenGUI;
        this.runGame = runGame;
        this.waypoints = new Waypoints(selectedMap);
        this.balloons = new ArrayList<>();
        loadMapImage();
        loadBalloonImages();

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(MAP_WIDTH / 3, 0, MAP_WIDTH, HEIGHT);
        add(layeredPane);

        animationPanel = new AnimationPanel();
        animationPanel.setBounds(0, 0, 700, 510);
        animationPanel.setOpaque(false);
        layeredPane.add(animationPanel, JLayeredPane.MODAL_LAYER);

        towerPanel = new TowerPanel(layeredPane, mapImage, animationPanel);
        towerPanel.setBounds(0, 0, 700, 510);
        towerPanel.setOpaque(false);
        layeredPane.add(towerPanel, JLayeredPane.PALETTE_LAYER);

        waveManager = new WaveManager();
        waveInProgress = false;
        bloonsRemainingInWave = 0;

        TowerSelectionButtons towerSelectionButtons = new TowerSelectionButtons(runGame, mapImage, towerPanel, layeredPane);
        towerSelectionButtons.setBounds(MAP_WIDTH + MAP_WIDTH / 3 + MAP_WIDTH / 12, MAP_WIDTH / 5 + 15, MAP_WIDTH / 6, HEIGHT / 2);
        add(towerSelectionButtons);
        setLayout(null);

        addPlayButton();
        addReturnHomeButton();
        loadWoodTexture();

        // Game loop timer (60 FPS)
        gameLoopTimer = new Timer(16, e -> gameLoop());
        gameLoopTimer.start();
    }

    private void loadMapImage() {
        String imagePath = "src/MapImg/" + selectedMap + ".png";
        try {
            mapImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + imagePath);
            mapImage = null;
        }
    }

    private void loadWoodTexture() {
        String woodTexturePath = "src/DesignImg/WoodTextureOne.jpg";
        try {
            woodTexture = ImageIO.read(new File(woodTexturePath));
        } catch (IOException e) {
            System.err.println("Failed to load wood texture image: " + woodTexturePath);
            woodTexture = null;
        }
    }

    private void loadBalloonImages() {
        String[] paths = {
                "src/BalloonImages/pop.png",
                "src/BalloonImages/redbloon.png",
                "src/BalloonImages/bluebloon.png",
                "src/BalloonImages/greenbloon.png",
                "src/BalloonImages/pinkbloon.png",
                "src/BalloonImages/yellowbloon.png",
                "src/BalloonImages/zebrabloon.png",
                "src/BalloonImages/rainbowbloon.png",
                "src/BalloonImages/metalbloon.png",
                "src/BalloonImages/moab.png"
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

    private void startNextWave() {
        if (!waveManager.hasNextWave() && waveInProgress) return;

        // Only start the wave if the balloons list is empty
        if (balloons.isEmpty()) {
            waveInProgress = true;
            playButton.setEnabled(false); // Disable button while wave is in progress

            Wave currentWave = waveManager.getCurrentWave();
            bloonsRemainingInWave = 0;

            for (BloonSpawnInfo bloonInfo : currentWave.getBloons()) {
                bloonsRemainingInWave += bloonInfo.getAmount();
                spawnBloonsForWave(bloonInfo);
            }
        }
    }

    private void spawnBloonsForWave(BloonSpawnInfo bloonInfo) {
        Timer spawnTimer = new Timer((int) (bloonInfo.getSpawnRate() * 1000), e -> {
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
                    startNextWave(); // Start the next wave
                } else {
                    waveInProgress = false; // All waves completed
                }
            }
        });
        spawnTimer.start();  // Start the timer
    }

    private void gameLoop() {
        updateGameState();
        repaint();

        // Check if balloons are empty, if so, enable the play button to allow the next wave
        if (balloons.isEmpty() && waveInProgress) {
            waveInProgress = false;
            playButton.setEnabled(true); // Enable button to start the next wave
        }
    }

    private void updateGameState() {
        for (int i = 0; i < balloons.size(); i++) {
            Balloon balloon = balloons.get(i);
            balloon.updatePosition();
            if (balloon.hasReachedEnd()) {
                balloons.remove(i);
                System.out.println("Balloon " + i + " reached the end");
                System.out.println(balloons.size());
                i--;
                currentHealth -= balloon.getLevel();
            }
        }

        animationPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int WOOD_WIDTH = MAP_WIDTH / 3;
        g.drawImage(mapImage, WOOD_WIDTH, 0, MAP_WIDTH, HEIGHT, this);

        for (Balloon balloon : balloons) {
            balloon.draw(g);
        }
        g.drawImage(woodTexture, 0, 0, WOOD_WIDTH, HEIGHT, this);
        g.drawImage(woodTexture, MAP_WIDTH + WOOD_WIDTH, 0, WOOD_WIDTH, HEIGHT, this);
        drawGameInfo(g);
    }

    private void drawGameInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        int xOffset = 10;
        g.drawString("Wave: " + (waveManager.getCurrentWaveIndex() + 1), xOffset, 100);
        g.drawString("Cash: $" + currentCash, xOffset, 130);
        g.drawString("Health: " + currentHealth, xOffset, 160);
    }

    private void addPlayButton() {
        playButton = new JButton("Start Wave");
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.setBounds(80, 10, 140, 40);
        playButton.setFocusPainted(false);

        playButton.addActionListener(e -> {
            if (!waveInProgress) {
                startNextWave();
            }
        });

        add(playButton);
    }

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
        revalidate();
        repaint();
    }

    private void returnHome() {
        gameLoopTimer.stop();
        runGame.setSize(1024, 768);
        runGame.setContentPane(homeScreenGUI);
        runGame.revalidate();
        runGame.repaint();
    }
}
