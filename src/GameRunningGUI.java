import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


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
    private final WaveManager waveManager;
    private boolean waveInProgress;
    private int bloonsRemainingInWave;
    private JButton playButton;
    private boolean addEndRoundCash;

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
        this.placedTowers = new ArrayList<>();
        this.curProjectiles = new ArrayList<>();

        loadImages();
        loadBalloonImages();

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(MAP_WIDTH / 3, 0, MAP_WIDTH, HEIGHT);
        add(layeredPane);

        towerPanel = new TowerPanel(layeredPane,placedTowers);
        towerPanel.setBounds(0, 0, 700, 520);
        towerPanel.setOpaque(false);
        layeredPane.add(towerPanel, JLayeredPane.PALETTE_LAYER);
        towerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new SoundEffect("Click.wav", false, 1f);
            }
        });

        waveManager = new WaveManager();
        waveInProgress = false;
        bloonsRemainingInWave = 0;

        TowerSelectionButtons towerSelectionButtons = new TowerSelectionButtons(runGame, mapImage, towerPanel, layeredPane);
        towerSelectionButtons.setBounds(MAP_WIDTH + MAP_WIDTH / 3 + MAP_WIDTH / 12, MAP_WIDTH / 5 + 15, MAP_WIDTH / 6, HEIGHT / 2);
        add(towerSelectionButtons);
        setLayout(null);

        addPlayButton();
        addReturnHomeButton();

        // Game loop timer (60 FPS)
        gameLoopTimer = new Timer(16, e -> gameLoop());
        gameLoopTimer.start();
    }

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
                "src/BalloonImages/moab.png",
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



    private void startNextWave() {
        if (!waveManager.hasNextWave() && waveInProgress) return;

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
        // Add global mouse listener for click sounds
        updateGameState();
        repaint();
    }

    private void updateGameState() {
        for (int i = 0; i < balloons.size(); i++) {
            Balloon balloon = balloons.get(i);
            balloon.updatePosition();
            if (balloon.hasReachedEnd()) {
                balloons.remove(i);
                i--;
                currentHealth -= balloon.getLevel();
            }
        }


        /*
        All towers will check which balloons are in range. It will keep track out
        of the balloons in range which of them are the furthest by line segments
        on the map. If there are balloons in the same line segment the stronger
        balloon will be targeted. Each tower will have 1 target per update.
         */
        for (Tower tower : placedTowers) {
            // Step 1: Invalidate current target if it’s no longer in range
            Balloon currentTarget = tower.getTarget();
            if (currentTarget != null && !tower.inRange(currentTarget)) {
                tower.setTarget(null);
            }

            // Step 2: Try to find a better target
            Balloon bestTarget = null;
            for (Balloon balloon : balloons) {
                if (tower.inRange(balloon)) {
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

        Iterator<Projectile> projectiles = curProjectiles.iterator();
        while (projectiles.hasNext()) {
            Projectile p = projectiles.next();
            p.update();
            if (p.missed()) {
                projectiles.remove();
                continue;
            }

            // This will track all balloons that have already been hit by the current projectile

            Iterator<Balloon> balloonIterator = balloons.iterator();
            while (balloonIterator.hasNext()) {
                Balloon b = balloonIterator.next();

                // Ensure that we don't hit the same balloon twice by checking if it's already in the 'hitBalloons' set
                if (p.didHit(b)) {
                    p.removeOneFromHitCount();
                    b.takeDamage(1);  // Apply damage to the balloon
                    currentCash++;
                    // If the balloon is popped, remove it from the list
                    if (b.isPopped()) {
                        new SoundEffect("Pop1.wav", false, .8f);
                        balloonIterator.remove();
                    }

                    // If the projectile has no remaining hits, remove it from the list
                    if (p.getRemainingHits() <= 0) {
                        projectiles.remove();
                        break;  // Exit loop after projectile hits its target(s)
                    }
                }
            }
        }
    }

    public int compareBalloons(Balloon b1, Balloon b2) {
        if (b1.getCurrentSegmentIndex() > b2.getCurrentSegmentIndex()){
            return 1;
        }
        else if(b1.getCurrentSegmentIndex() == b2.getCurrentSegmentIndex() && b1.getLevel() > b2.getLevel()){
            return 1;
        } else{
            return 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        final int WOOD_WIDTH = MAP_WIDTH / 3;
        g.drawImage(mapImage, WOOD_WIDTH, 0, MAP_WIDTH, HEIGHT, this);
        for (Tower tower : placedTowers) {
            int drawX = tower.xPosition + WOOD_WIDTH;
            int drawY = tower.yPosition;
            int imgWidth = tower.getImgWidth();
            int imgHeight = tower.getImgHeight();
            //Temp draw the hit range around tower
            Color color;
            color = new Color(128, 128, 128, 128);
            g2d.setColor(color);
            int xOffset = (tower.getDiameter() / 2) - (tower.getImgWidth() / 2) - 233;
            int yOffset = (tower.getDiameter() / 2) - (tower.getImgHeight() / 2);
            g2d.drawOval(tower.xPosition - xOffset, tower.yPosition - yOffset, tower.getDiameter(), tower.getDiameter());
            if (tower.getTarget() != null) {
                double angle = tower.getAngle(tower.getTarget().getX(), tower.getTarget().getY());
                AffineTransform originalTransform = g2d.getTransform();
                g2d.rotate(Math.toRadians(angle), drawX + imgWidth / 2.0, drawY + imgHeight / 2.0);
                g2d.drawImage(tower.towerImage, drawX, drawY, imgWidth, imgHeight, this);
                g2d.setTransform(originalTransform);
            } else {
                g2d.drawImage(tower.towerImage, drawX, drawY, imgWidth, imgHeight, this);
            }
        }
        for (Balloon balloon : balloons) {
            balloon.draw(g);
        }
        for(Projectile p: curProjectiles) {
            double drawX = p.currentX + WOOD_WIDTH;
            AffineTransform projectileTransform = g2d.getTransform();
            g2d.rotate(p.getAngle(), (int) drawX, p.currentY);
            g2d.drawImage(p.getImage(), (int) drawX, (int) p.currentY, p.getWidth(), p.getHeight(), this);
            g2d.setTransform(projectileTransform);
        }
        g.drawImage(woodTexture, 0, 0, WOOD_WIDTH, HEIGHT, this);
        g.drawImage(woodTexture, MAP_WIDTH + WOOD_WIDTH, 0, WOOD_WIDTH, HEIGHT, this);
        drawGameInfo(g);
    }

    private void drawGameInfo(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        int xOffset = 10;

        // Format the cash value with commas
        String formattedCash = String.format("%,d", currentCash);

        // Draw hearts image and health text
        g.drawImage(heartsImage, xOffset, 160, 30, 30, this); // Image width and height can be adjusted
        g.drawString("Health: " + currentHealth, xOffset + 35, 180);

        // Draw money sign image and cash text
        g.drawImage(moneySignImage, xOffset, 120, 30, 30, this); // Image width and height can be adjusted
        g.drawString("Cash: $" + formattedCash, xOffset + 35, 140);

        // Check wave status
        if (balloons.isEmpty() &&!waveInProgress) {
            if(addEndRoundCash){
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

    private void addPlayButton() {
        playButton = new JButton("Start Wave "+ (waveManager.getCurrentWaveIndex() + 1));
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.setBounds(80, 10, 140, 40);
        playButton.setFocusPainted(false);

        playButton.addActionListener(e -> {
            if (!waveInProgress) {
                new SoundEffect("Click.wav", false, 1f);
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
    }

    private void returnHome() {
        gameLoopTimer.stop();
        runGame.setSize(1024, 768);
        runGame.setContentPane(homeScreenGUI);
        runGame.revalidate();
        runGame.repaint();
    }
}
