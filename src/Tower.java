import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Filename: Tower.java
 * Authors: Joseph Farrier and Jace Claassen
 * Description: This is an abstract class that defines the structure and behavior of a tower
 * in a tower defense game. It provides functionality for handling tower properties, checking
 * if a tower can be placed, managing projectiles, and firing attacks. Subclasses should implement
 * specific tower behaviors such as attack methods and projectile handling.
 */
abstract public class Tower {
    // Information the Monkey Needs to know
    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected BufferedImage currentMap;
    protected String mapName;
    protected static BufferedImage[] PROJECTILE_IMAGES;

    protected String towerType;

    // Queue that allows tower to decide what enemy to shoot
    protected ArrayList<Projectile> projectiles = new ArrayList<>();
    protected ArrayList<Balloon> targets = new ArrayList<>();

    protected int fireRate;
    protected int diameter;
    protected int projectileSpeed;
    protected int projectileDamage;
    protected int price;

    Balloon target;

    protected Timer fireTimer;
    protected boolean readyToFire = true;

    // Flags to help determine placing functionality
    protected boolean placeable;
    protected boolean isSelected = false;
    protected boolean placed = false;
    protected boolean isRotatable;

    protected int xPosition;
    protected int yPosition;

    protected Image towerImage;

    private int imgWidth;
    private int imgHeight;

    private int cost;

    /**
     * Constructor for towers that takes JFrame and BufferedImage for the current map.
     * Other parameters will be initialized later using setters or default values.
     *
     * @param runGame     The JFrame representing the game window.
     * @param currentMap  The map image representing the current game level.
     * @param towerImagePath   The path to the image representing the tower.
     */
    public Tower(JFrame runGame, BufferedImage currentMap, String towerImagePath) {
        parentWindow = runGame.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15, 15, 15, 15);
        this.currentMap = currentMap;
        placeable = false;

        // Load the image using getResource
        towerImage = loadImage(towerImagePath);
        if (towerImage != null) {
            towerJLabel.setIcon(new ImageIcon(towerImage));
        } else {
            System.out.println("Failed to load image: " + towerImagePath);
        }

        parentWindow.add(towerJLabel);
    }

    /**
     * Helper method to load an image from the provided path.
     *
     * @param imagePath The path to the image file.
     * @return The image loaded from the path, or null if the image could not be loaded.
     */
    private Image loadImage(String imagePath) {
        try {
            return new ImageIcon(getClass().getClassLoader().getResource("TowerImages/" + imagePath)).getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            return null;
        }
    }

    /**
     * Determines if a given pixel color is "green" for valid placement.
     *
     * @param red   The red component of the pixel color.
     * @param green The green component of the pixel color.
     * @param blue  The blue component of the pixel color.
     * @return True if the pixel is green (valid for placement), false otherwise.
     */
    public boolean isGreen(int red, int green, int blue) {return (green > 80 && (red < 130) && (blue < 81));}

    /**
     * Determines if the tower can be placed at the specified coordinates on the map.
     *
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return True if the tower can be placed at the specified coordinates, false otherwise.
     */
    public boolean isPlaceable(int x, int y) {
        int missed = 0;
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                // Use try-catch to stop user from trying to place the tower out of bounds
                if (x + i < 0 || x + i >= currentMap.getWidth() || y + j < 0 || y + j >= currentMap.getHeight()) {
                    placeable = false;
                    return false;
                }
                int pixelColor = currentMap.getRGB(x + i, y + j);
                int red = (pixelColor >> 16) & 0xFF;
                int green = (pixelColor >> 8) & 0xFF;
                int blue = (pixelColor) & 0xFF;

                if (!isGreen(red, green, blue)) {
                    missed++;
                    if (missed > 25) {
                        placeable = false;
                        return false;
                    }
                }
            }
        }
        placeable = true;
        return true;
    }
    /**
     * Determines if the given balloon is within range of this tower.
     *
     * @param balloon The balloon to check.
     * @return True if the balloon is within range, false otherwise.
     */
    public boolean inRange(Balloon balloon) {
        int targetX = balloon.getX() + 13;
        int targetY = balloon.getY() + 16;
        double distanceSquared = Math.pow(targetX - this.xPosition - (getImgWidth() / 2), 2) +
                Math.pow(targetY - this.yPosition - (getImgHeight() / 2), 2);

        double rangeSquared = Math.pow(this.diameter / 2, 2);
        return distanceSquared <= rangeSquared;
    }

    /**
     * Returns the target balloon for the tower.
     *
     * @return The current target balloon.
     */
    public Balloon getTarget() {
        return target;
    }

    /**
     * Sets the target balloon for the tower.
     *
     * @param target The balloon to be set as the target.
     */
    public void setTarget(Balloon target) {
        this.target = target;
    }

    /**
     * Calculates the angle between the tower and a target position.
     *
     * @param x The x-coordinate of the target.
     * @param y The y-coordinate of the target.
     * @return The angle in degrees between the tower and the target.
     */
    public double getAngle(int x, int y) {
        int centerX = this.xPosition + towerImage.getWidth(null) / 2;
        int centerY = this.yPosition + towerImage.getHeight(null) / 2;

        double angle = Math.atan2(y - centerY, x - centerX);
        double angleDegrees = Math.toDegrees(angle);

        while (angleDegrees < 0) {
            angleDegrees += 360;
        }
        while (angleDegrees > 360) {
            angleDegrees -= 360;
        }
        // Angle is off by 90 degrees by default
        angleDegrees += 90;

        return angleDegrees;
    }

    // Getters and setters for tower properties

    public int getFireRate() {
        return fireRate;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public int getProjectileDamage() {
        return projectileDamage;
    }

    public Point getPosition() {
        return new Point(xPosition, yPosition);
    }

    public int setFireRate(int fireRate) {
        return this.fireRate = fireRate;
    }

    // Setters for tower properties

    public void setFireSpeed(int fireSpeed) {
        this.fireRate = fireSpeed;
    }

    public void setRotatable(boolean rotatable) {
        this.isRotatable = rotatable;
    }

    public void setRange(int diameter) {
        this.diameter = diameter;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.placed = true;
        this.isSelected = false;
    }

    public boolean isPlaceable() {
        return placeable;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
    /**
     * Fires the projectile towards the target balloon.
     *
     * @param currentTarget The balloon to target with the projectile.
     * @param projectile    The list of projectiles to be fired.
     */
    public abstract void fire(Balloon currentTarget, ArrayList<Projectile> projectile);
    public abstract void fire(Balloon currentTarget, ArrayList<Projectile> projectile, ArrayList<Balloon> targets);

    /**
     * Checks if the tower is ready to fire.
     *
     * @return True if the tower is ready to fire, false otherwise.
     */
    public boolean isReadyToFire() {
        return readyToFire;
    }

    /**
     * Sets up a fire timer that ensures the tower cannot fire again until after a delay.
     */
    public void setFireTimer() {
        // If there is already a fireTimer running, do nothing
        if (!readyToFire) return;

        // Disable the tower from firing again immediately
        readyToFire = false;

        // Cancel any previous fire timer to ensure only one is running at a time
        if (fireTimer != null) {
            fireTimer.cancel();
            fireTimer.purge();
        }

        // Create a new timer to allow firing after a delay
        fireTimer = new Timer();
        fireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                readyToFire = true; // Re-enable firing after the fireRate delay
            }
        }, fireRate); // Delay in milliseconds before the tower can fire again
    }

    /**
     * Loads the projectile images from the provided paths.
     *
     */
    protected void loadProjectileImages() {
        String[] paths = {
                "src/ProjectileImages/dart.png",
                "src/ProjectileImages/glueDart.png",
                "src/ProjectileImages/ninjaStar.png",
                "src/ProjectileImages/energyBall.png",
                "src/ProjectileImages/bomb.png",
                "src/ProjectileImages/explosion.png"
        };
        PROJECTILE_IMAGES = new BufferedImage[paths.length];

        for (int i = 0; i < paths.length; i++) {
            try {
                PROJECTILE_IMAGES[i] = ImageIO.read(new File(paths[i]));
            } catch (IOException e) {
                System.err.println("Failed to load balloon image: " + paths[i]);
                PROJECTILE_IMAGES[i] = null;
            }
        }
    }

    /**
     * Retrieves a projectile image based on the index number.
     *
     * @param num The index number of the projectile image.
     * @return The projectile image at the given index.
     */
    public static BufferedImage getProjectileImage(int num) {
        return PROJECTILE_IMAGES[num];
    }

    /**
     * Sets the size of the tower image.
     *
     * @param type The size of the tower image (defined by TowerImageSize enum).
     */
    public void setTowerImageSize(TowerImageSize type) {
        this.imgWidth = type.getWidth();
        this.imgHeight = type.getHeight();
    }

    /**
     * Retrieves the width of the tower image.
     *
     * @return The width of the tower image.
     */
    public int getImgWidth() {
        return imgWidth;
    }

    /**
     * Retrieves the height of the tower image.
     *
     * @return The height of the tower image.
     */
    public int getImgHeight() {
        return imgHeight;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

}
