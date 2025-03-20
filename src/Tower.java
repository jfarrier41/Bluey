import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;

/*
 * Joseph Farrier
 * 3/16/2025
 * Description: This is an abstract class that defines what each tower will need to
 * know and have access to. Mostly contaning getters and setters, but also providing
 * implementations to check placeability, and to handle if enemies are in range, while also
 * allowing each implented tower the ability to attack in their own implemented way
 * */
abstract public class Tower {
    // Information the Monkey Needs to know

    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected BufferedImage currentMap;

    // Queue that allows tower to decide what enemy to shoot
    private PriorityQueue<Balloon> targets = new PriorityQueue<>(
            (b1, b2) -> Double.compare(b2.getLevel(), b1.getLevel())
    );

    protected int fireSpeed;
    protected int diameter;
    protected int projectileSpeed;
    protected int projectileDamage;

    // flags to help determine placing functionality
    protected boolean placeable;
    protected boolean isSelected = false;
    protected boolean placed = false;

    protected int xPosition;
    protected int yPosition;

    protected Image towerImage;

    /*
     * Constructor for towers
     * Assigns all needs values and it an image
     */
    public Tower(JFrame runGame, int fire_Speed, int diameter
            , int projectile_Speed, int projectile_Damage, String image, BufferedImage currentMap) {


        parentWindow = runGame.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15, 15, 15, 15);
        this.fireSpeed = fire_Speed;
        this.diameter = diameter;
        this.projectileSpeed = projectile_Speed;
        this.projectileDamage = projectile_Damage;
        this.currentMap = currentMap;

        placeable = false;
        // give monkey an image icon
        this.towerImage = new ImageIcon(image).getImage();
        towerJLabel.setIcon(new ImageIcon(towerImage));
        parentWindow.add(towerJLabel);
        // make sure a image is found and that is valid by checking its width
        if (towerImage != null && towerImage.getWidth(null) > 0) {
            System.out.println("towerImage working");
        } else {
            System.out.println("Failed to load image.");
        }
    }


    // Helper method to determine valid pixel color
    public boolean isGreen(int red, int green, int blue) {
        /*if(currentMap.equals("MonkeyLane")){
            return color.getGreen() >= 39 && color.getRed() < 110 && color.getBlue() < 100;
        }else if(currentMap.equals("Maze")){
            return color.getGreen() >= 39 && color.getRed() < 110 && color.getBlue() < 100;
        }else if(currentMap.equals("ParkPath")){*/
        return (green > 180 && (red < 150) && (blue < 160));
        //}

    }

    //Method to determine if monkey can be placed
    public boolean isPlaceable(int x, int y) {
        // Grab the the 36 surounding pixels at x,y( Where mouse is at)
        int missed = 0;
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                // Use try catch to stop user from trying to place Tower out of bounds.
                if (x + i < 0 || x + i >= currentMap.getWidth() || y + j < 0 || y + j >= currentMap.getHeight()) {
                    placeable = false;
                    return false;
                }
                int pixelColor = currentMap.getRGB(x + i, y + j);
                int xpos = (x+i);
                int ypos = (y+j);
                int red = (pixelColor >> 16) & 0xFF;
                int green = (pixelColor >> 8) & 0xFF;
                int blue = (pixelColor) & 0xFF;
                System.out.println("Red" + red +"Cord "+ xpos+" , " + ypos);
                System.out.println("Blue" + blue);
                System.out.println("Green" + green);
                // use helper function to determine placeability
                if (!isGreen(red, green, blue)) {
                    missed++;
                    if (missed > 25) {
                        placeable = false;
                        return false;
                    }
                }
            }
        }
        // Only return true if every pixel meets green requirments
        missed = 0;
        placeable = true;
        return true;
    }


    // Method to determine attack stragey, each tower implements
    abstract int attack();


    // Will check to see if balloon is in range.
    public boolean inRange(Balloon ballon) {
        int xPos = ballon.getX();
        int yPos = ballon.getY();
        // Circular formula, provided by ChatGPT
        int distanceSquared = (xPos - this.xPosition) * (xPos - this.xPosition) +
                (yPos - this.yPosition) * (yPos - this.yPosition);

        int rangeSquared = (this.diameter / 2) * (this.diameter / 2);
        return distanceSquared <= rangeSquared;
    }

    // Helper function to add ballons to Queue
    public void addTarget(Balloon balloon) {
        // make sure balloon is not already in list
        if (!targets.contains(balloon)) {
            targets.add(balloon);
        }
    }

    // Helper function to remove balloons from Queue
    public void removeTarget(Balloon balloon) {
        // If the balloon level is less than one or no longer in range, remove it
        if (balloon.getLevel() < 1 || !inRange(balloon)) {
            targets.remove(balloon);
        }
    }

    // Give Tower first ballon in Queue
    public Balloon target() {
        return targets.peek();
    }

    // Return balloons fire speed
    public int getFireSpeed() {
        return fireSpeed;
    }

    // Get Balloons range
    public int getDiameter() {
        return diameter;
    }

    // Return Projectile speed
    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    // Return projectile damage
    public int getProjectileDamage() {
        return projectileDamage;
    }

    // Get postion of Tower
    public Point getPosition() {
        return new Point(xPosition, yPosition);
    }

    // Change FireSpeed of Tower
    public void setFireSpeed(int fireSpeed) {
        this.fireSpeed = fireSpeed;
    }

    // Set Range of Tower
    public void setRange(int diameter) {
        this.diameter = diameter;
    }

    // Set Projectile Speed
    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    // Set projectile Damge
    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }

    // Set postion of tower
    public void setPosition(int x, int y) {
        this.xPosition = x - 24;
        this.yPosition = y - 18;
        this.placed = true;
        this.isSelected = false;
    }


    // Return if Placeable
    public boolean getValid() {
        return placeable;
    }

}
