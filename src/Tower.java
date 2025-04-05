import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Filename: Tower.java
 * Authors: Joseph Farrier and Jace Claassen
 * Description: This is an abstract class that defines what each tower will need to
 * know and have access to. Mostly contaning getters and setters, but also providing
 * implementations to check placeability, and to handle if enemies are in range, while also
 * allowing each implented tower the ability to attack in their own implemented way
 */


abstract public class Tower {
    // Information the Monkey Needs to know
    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected BufferedImage currentMap;
    protected String mapName;

    // Queue that allows tower to decide what enemy to shoot
    protected ArrayList<Balloon> targets = new ArrayList<>();
    protected ArrayList<Projectile> projectiles = new ArrayList<>();


    protected int fireRate;
    protected int diameter;
    protected int projectileSpeed;
    protected int projectileDamage;
    // ALlow for each tower to have projectile Image

    protected Image projectileImage;
    protected boolean projectileActive;
    protected double projX;
    protected double projY;

    protected Timer fireTimer;
    protected boolean readyToFire = true;

    // flags to help determine placing functionality
    protected boolean placeable;
    protected boolean isSelected = false;
    protected boolean placed = false;

    protected int xPosition;
    protected int yPosition;

    protected Image towerImage;

    /*
     * Constructor for towers that takes only JFrame and BufferedImage currentMap.
     * Other parameters will be initialized later using setters or default values.
     */
    public Tower(JFrame runGame, BufferedImage currentMap, String imagePath) {
        parentWindow = runGame.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15, 15, 15, 15);
        this.currentMap = currentMap;
        placeable = false;

        // Load the image using getResource
        towerImage = loadImage(imagePath);
        if (towerImage != null) {
            towerJLabel.setIcon(new ImageIcon(towerImage));
        } else {
            System.out.println("Failed to load image: " + imagePath);
        }

        parentWindow.add(towerJLabel);
    }

    private Image loadImage(String imagePath) {
        try {
            return new ImageIcon(getClass().getClassLoader().getResource("TowerImages/" + imagePath)).getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            return null;
        }
    }

    // Helper method to determine valid pixel color
    public boolean isGreen(int red, int green, int blue) {
        //System.out.println(currentMap);
//        if(currentMap.equals("src/MapImg/MonkeyLane.png")){
            return (green > 180 && (red < 150) && (blue < 160));
//        }
//        return false;

    }

    // Method to determine if the tower can be placed at the specified coordinates
    public boolean isPlaceable(int x, int y) {
        int missed = 0;
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
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

    // Abstract method to be implemented by subclasses to determine attack strategy
    public abstract boolean attack();

    // Will check to see if a balloon is in range
    public boolean inRange(Balloon balloon) {
        int targetX = balloon.getX();
        int targetY = balloon.getY();
        double distanceSquared = Math.pow(targetX - this.xPosition, 2) +
                Math.pow(targetY - this.yPosition,2);

        double rangeSquared = Math.pow(this.diameter / 2, 2);
        return distanceSquared <= rangeSquared;
    }

    // Helper function to add balloons to the queue
    public void addTarget(Balloon balloon) {
        if (!targets.contains(balloon)) {
            System.out.println("Adding target: " + balloon);
            targets.add(balloon);
        }
    }

    // Helper function to remove balloons from the queue
    public void removeTarget(Balloon balloon) {
        /*if (balloon.getLevel() > 0) {
            balloon.setLevel(projectileDamage);
        }*/
        if(balloon.getLevel() == 1){
            targets.remove(balloon);
            System.out.println("Removed " + balloon);
        }
    }

    // Get the first balloon in the queue
    public Balloon target() {
        return targets.getFirst();
    }

    public double getAngle(int x, int y){
        int centerX = this.xPosition+ towerImage.getWidth(null)/2;
        int centerY = this.yPosition+ towerImage.getHeight(null)/2;

        double angle = Math.atan2(y-centerY, x-centerX);
        double angleDegrees = Math.toDegrees(angle);

        while(angleDegrees < 0){
            angleDegrees += 360;
        }
        while (angleDegrees > 360){
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
        this.xPosition = x - 24;
        this.yPosition = y - 18;
        this.placed = true;
        this.isSelected = false;
    }
    public boolean isPlaceable() {
        return placeable;
    }
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
    // Allow each tower to have own projectile image


    public abstract boolean isProjectileActive();

    public abstract void setProjectileActive(boolean projectileActive);

    public double projectileAngle(int balloonX, int balloonY){
        double projAngle = Math.atan2(balloonY - projY, balloonX - projX);
        return projAngle;
    }

    public abstract void fire(Balloon currentTarget);

    public boolean isReadyToFire() {
        return readyToFire;
    }

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
}