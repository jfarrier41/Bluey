import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

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
    private final Timer balloonSpawnTimer;
    private final Waypoints waypoints;
    private BufferedImage[] balloonImages;
    private ArrayList<Balloon> balloons;
    private int currentRound, currentCash, currentHealth;
    private final Random random;

    public GameRunningGUI(RunGame runGame, int width, int height, String selectedMap, HomeScreenGUI homeScreenGUI) {
        currentRound = 1;
        currentCash = 1000;
        currentHealth = 200;
        random = new Random();

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

        TowerSelectionButtons towerSelectionButtons = new TowerSelectionButtons(runGame, mapImage, towerPanel, layeredPane);
        towerSelectionButtons.setBounds(MAP_WIDTH + MAP_WIDTH / 3 + MAP_WIDTH / 12, MAP_WIDTH / 5 + 15, MAP_WIDTH / 6, HEIGHT / 2);
        add(towerSelectionButtons);
        setLayout(null);

        addReturnHomeButton();
        loadWoodTexture();

        // Game loop timer (60 FPS)
        gameLoopTimer = new Timer(16, e -> gameLoop());
        gameLoopTimer.start();

        // Balloon spawn timer (every .5 seconds)
        balloonSpawnTimer = new Timer(500, e -> spawnBalloon());
        balloonSpawnTimer.start();
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

    private void spawnBalloon() {
        int randomLevel = random.nextInt(balloonImages.length);
        balloons.add(new Balloon(waypoints, balloonImages, randomLevel));
    }

    private void gameLoop() {
        updateGameState();
        repaint();
    }

    private void updateGameState() {
        for (int i = 0; i < balloons.size(); i++) {
            Balloon balloon = balloons.get(i);
            balloon.updatePosition();
            if (balloon.hasReachedEnd()) {
                balloons.remove(i);
                i--; // Adjust index after removal
                currentHealth -= 1; // Reduce health when a balloon exits
            }
        }
        animationPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        final int WOOD_WIDTH = MAP_WIDTH / 3;
        g.drawImage(mapImage, WOOD_WIDTH, 0, MAP_WIDTH, HEIGHT, this);
        drawGameInfo(g);

        for (Balloon balloon : balloons) {
            balloon.draw(g);
        }
        g.drawImage(woodTexture, 0, 0, WOOD_WIDTH, HEIGHT, this);
        g.drawImage(woodTexture, MAP_WIDTH + WOOD_WIDTH, 0, WOOD_WIDTH, HEIGHT, this);
    }

    private void drawGameInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        int xOffset = 10;
        g.drawString("Round: " + currentRound, xOffset, 100);
        g.drawString("Cash: $" + currentCash, xOffset, 130);
        g.drawString("Health: " + currentHealth, xOffset, 160);
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
        balloonSpawnTimer.stop();
        runGame.setSize(1024, 768);
        runGame.setContentPane(homeScreenGUI);
        runGame.revalidate();
        runGame.repaint();
    }
}
