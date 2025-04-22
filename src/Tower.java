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
 * Abstract base class for all towers in the game.
 * Handles shared logic like placement, targeting, and projectile firing.
 * Subclasses define specific attack behavior and visuals.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
abstract public class Tower {
    /** Shared visual/projectile data */
    protected static BufferedImage[] PROJECTILE_IMAGES;

    /**Tower placement and GUI references*/
    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected BufferedImage currentMap;
    protected Image towerImage;

    /**Combat and behavior properties*/
    protected int projectileSpeed;
    protected int projectileDamage;
    protected int fireRate;
    protected boolean animateAttack;
    protected boolean isRotatable;
    protected String towerType;
    private int cost;

    /**Combat state tracking*/
    protected ArrayList<Projectile> projectiles = new ArrayList<>();
    protected ArrayList<Balloon> targets = new ArrayList<>();
    protected Balloon target;

    protected Timer fireTimer;
    protected boolean readyToFire = true;

    /**Placement flags and values*/
    protected boolean placeable;
    protected boolean isSelected = false;
    protected boolean placed = false;
    protected int diameter;
    protected int xPosition;
    protected int yPosition;

    /** tower image values*/
    private int imgWidth;
    private int imgHeight;

    /**
     * Constructs a tower and sets up its initial image and reference to the game window.
     *
     * @param runGame        The JFrame representing the main game.
     * @param currentMap     The current level's background map image.
     * @param towerImagePath Path to the tower image relative to resources.
     */
    public Tower(JFrame runGame, BufferedImage currentMap, String towerImagePath) {
        parentWindow = runGame.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15, 15, 15, 15);
        this.currentMap = currentMap;
        placeable = false;

        towerImage = loadImage(towerImagePath);
        if (towerImage != null) {
            towerJLabel.setIcon(new ImageIcon(towerImage));
        } else {
            System.out.println("Failed to load image: " + towerImagePath);
        }

        parentWindow.add(towerJLabel);
    }

    /**
     * Returns a projectile image from the static image array.
     *
     * @param num Index of the image.
     * @return The projectile image.
     */
    public static BufferedImage getProjectileImage(int num) {
        return PROJECTILE_IMAGES[num];
    }

    /**
     * Loads an image from the TowerImages resource folder.
     *
     * @param imagePath File name of the image.
     * @return The image if loaded successfully, otherwise null.
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
     * Determines whether a pixel color qualifies as "green" (i.e., valid for tower placement).
     *
     * @param red   Red component.
     * @param green Green component.
     * @param blue  Blue component.
     * @return True if the color is considered valid green terrain.
     */
    public boolean isGreen(int red, int green, int blue) {
        return (green > 80 && (red < 130) && (blue < 81));
    }

    /**
     * Checks whether the tower can be placed at the given coordinate.
     * Valid placement requires a majority of surrounding pixels to be green.
     *
     * @param x X-coordinate of the mouse/tower center.
     * @param y Y-coordinate of the mouse/tower center.
     * @return True if the area is valid for placement.
     */
    public boolean isPlaceable(int x, int y) {
        int missed = 0;
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
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
     * Determines whether a balloon is within the tower's attack radius.
     *
     * @param balloon The balloon to check.
     * @return True if the balloon is within range.
     */
    public boolean inRange(Balloon balloon) {
        /** Math to determine if balloon is in range Provided by CHATGPT*/
        int targetX = balloon.getX() + 13;
        int targetY = balloon.getY() + 16;
        double distanceSquared = Math.pow(targetX - this.xPosition - (getImgWidth() / 2), 2) +
                Math.pow(targetY - this.yPosition - (getImgHeight() / 2), 2);

        double rangeSquared = Math.pow(this.diameter / 2, 2);
        return distanceSquared <= rangeSquared;
    }

    /**
     * Calculates the aiming angle from the tower to a given coordinate.
     *
     * @param x Target x-coordinate.
     * @param y Target y-coordinate.
     * @return The angle in degrees, adjusted for sprite rotation.
     */
    public double getAngle(int x, int y) {
        /** Math to determine angle provided by CHATGPT*/
        int centerX = this.xPosition + towerImage.getWidth(null) / 2;
        int centerY = this.yPosition + towerImage.getHeight(null) / 2;

        double angle = Math.atan2(y - centerY, x - centerX);
        double angleDegrees = Math.toDegrees(angle);

        while (angleDegrees < 0) angleDegrees += 360;
        while (angleDegrees > 360) angleDegrees -= 360;
        /** Add ninty to account for default orintation*/
        return angleDegrees + 90;
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
     * Adds a balloon to the list of targets if not already present.
     *
     * @param target The balloon to be added as a target.
     */
    public void addTarget(Balloon target) {
        if (!targets.contains(target)) {
            this.targets.add(target);
        }
    }

    /**
     * Retrieves the fire rate of the tower.
     *
     * @return The fire rate in milliseconds.
     */
    public int getFireRate() {
        return fireRate;
    }

    /**
     * Sets the fire rate of the tower.
     *
     * @param fireRate The fire rate in milliseconds.
     * @return The updated fire rate.
     */
    public int setFireRate(int fireRate) {
        return this.fireRate = fireRate;
    }

    /**
     * Retrieves the range (diameter) of the tower.
     *
     * @return The diameter of the tower's range.
     */
    public int getRange() {
        return diameter;
    }

    /**
     * Sets the range (diameter) of the tower.
     *
     * @param diameter The new range diameter.
     */
    public void setRange(int diameter) {
        this.diameter = diameter;
    }

    /**
     * Gets the speed of the projectile.
     *
     * @return The projectile speed.
     */
    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    /**
     * Sets the speed of the projectile.
     *
     * @param projectileSpeed The projectile speed to set.
     */
    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    /**
     * Gets the damage dealt by the projectile.
     *
     * @return The projectile damage.
     */
    public int getProjectileDamage() {
        return projectileDamage;
    }

    /**
     * Sets the damage dealt by the projectile.
     *
     * @param projectileDamage The damage value to set.
     */
    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    /**
     * Gets the position of the tower as a Point.
     *
     * @return The (x, y) position of the tower.
     */
    public Point getPosition() {
        return new Point(xPosition, yPosition);
    }

    /**
     * Sets the position of the tower and marks it as placed.
     *
     * @param x The x-coordinate of the tower.
     * @param y The y-coordinate of the tower.
     */
    public void setPosition(int x, int y) {
        this.xPosition = x;
        this.yPosition = y;
        this.placed = true;
        this.isSelected = false;
    }


    /**
     * Enables or disables tower rotation behavior.
     *
     * @param rotatable True to make the tower rotatable, false otherwise.
     */
    public void setRotatable(boolean rotatable) {
        this.isRotatable = rotatable;
    }

    /**
     * Checks if the tower is placeable.
     *
     * @return True if the tower can be placed, false otherwise.
     */
    public boolean isPlaceable() {
        return placeable;
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

    /**
     * Sets the size of the tower image using a predefined size type.
     *
     * @param type The size of the tower image (defined by TowerImageSize enum).
     */
    public void setTowerImageSize(TowerImageSize type) {
        this.imgWidth = type.getWidth();
        this.imgHeight = type.getHeight();
    }

    /**
     * Gets the cost of the tower.
     *
     * @return The cost to purchase the tower.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Sets the cost of the tower.
     *
     * @param cost The cost to set.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Disables the attack animation flag for the tower.
     * Can be used to reset visual effects between attacks.
     */
    public void setAnimateAttackFalse() {
        this.animateAttack = false;
    }

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
     * Prevents spamming of projectiles by enforcing cooldown between shots.
     */
    public void setFireTimer() {
        if (!readyToFire) return;

        readyToFire = false;

        if (fireTimer != null) {
            fireTimer.cancel();
            fireTimer.purge();
        }

        /** Create new fire timer after firing*/
        fireTimer = new Timer();
        fireTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                readyToFire = true;
            }
        }, fireRate); /** Delay in milliseconds before the tower can fire again*/
    }

    /**
     * Loads the projectile images from the provided file paths.
     * Ensures projectiles have associated visual representations.
     */
    protected void loadProjectileImages() {
        String[] paths = {
                "src/ProjectileImages/dart.png",
                "src/ProjectileImages/glueDart.png",
                "src/ProjectileImages/ninjaStar.png",
                "src/ProjectileImages/energyBall.png",
                "src/ProjectileImages/bomb.png"
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
     * Abstract method to be implemented by subclasses to fire a projectile at a target balloon.
     *
     * @param currentTarget The balloon to target with the projectile.
     * @param projectile    The list of projectiles to be fired.
     */
    public abstract void fire(Balloon currentTarget, ArrayList<Projectile> projectile);
}