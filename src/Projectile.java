import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Projectile {
    // Position of the projectile
    public double currentX, currentY;
    // Initial position of the projectile (used to check if it has exceeded its range)
    public final double startX, startY;
    // The damage area radius of the projectile
    public final double damageArea;
    // Angle the projectile is traveling in (in radians)
    public double angle;
    // Maximum range the projectile can travel
    public int range;
    // Number of allowed hits for the projectile
    public int allowedHits;
    // Whether the projectile is tracking a target (balloon)
    public boolean tracking;
    // The type of projectile (e.g., Goo, Dart)
    protected ProjectileImageSize type;
    // Set of balloons that the projectile has already hit
    private Set<Balloon> hitBalloons = new HashSet<>();

    // Speed of the projectile
    public double speed;
    // Image representation of the projectile
    protected BufferedImage image;
    // The balloon that the projectile is currently targeting
    private Balloon currentTarget;
    private ArrayList<Balloon> targets;

    private int damage;

    /**
     * Constructs a new Projectile object.
     *
     * @param x               The starting X position of the projectile.
     * @param y               The starting Y position of the projectile.
     * @param damageArea      The damage area radius of the projectile.
     * @param speed           The speed at which the projectile moves.
     * @param angle           The angle at which the projectile travels.
     * @param range           The maximum range the projectile can travel.
     * @param currentTarget   The target balloon for the projectile.
     * @param allowedHits     The number of allowed hits before the projectile stops.
     * @param tracking        Whether the projectile tracks a target.
     * @param projectileImage The image representing the projectile.
     * @param type            The type of projectile (e.g., Goo, Dart).
     */
    public Projectile(double x, double y, double damageArea, double speed, double angle,
                      int range, Balloon currentTarget, int allowedHits,
                      boolean tracking, BufferedImage projectileImage, ProjectileImageSize type, int damage,
                      ArrayList<Balloon> targets) {
        this.currentX = x;
        this.currentY = y;
        this.startX = x;
        this.startY = y;
        this.damageArea = damageArea;
        this.speed = speed;
        this.angle = angle;
        this.range = range;
        this.currentTarget = currentTarget;
        this.allowedHits = allowedHits;
        this.tracking = tracking;
        this.image = projectileImage;
        this.type = type;
        this.damage = damage;
        this.targets = targets;

    }

    /**
     * Updates the position of the projectile.
     * If the projectile is tracking a target, it moves toward that target; otherwise, it moves in a straight line.
     */
    public void update() {
        if (tracking == false) {
            this.currentX += Math.cos(angle) * speed;
            this.currentY += Math.sin(angle) * speed;
        } else {
            double angle = projectileAngle(currentTarget);
            this.currentX += Math.cos(angle) * speed;
            this.currentY += Math.sin(angle) * speed;
        }
    }

    /**
     * Calculates the angle (in radians) from the projectile's current position to the target balloon.
     *
     * @param balloon The balloon to calculate the angle to.
     * @return The angle in radians between the projectile and the balloon.
     */
    public double projectileAngle(Balloon balloon) {
        int balloonX = balloon.getX();
        int balloonY = balloon.getY();
        double projAngle = Math.atan2(balloonY - currentY, balloonX - currentX);
        return projAngle;
    }

    /**
     * Gets the angle the projectile is traveling in.
     *
     * @return The angle in radians.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Checks if the projectile has exceeded its maximum range.
     *
     * @return true if the projectile has missed (i.e., exceeded its range), otherwise false.
     */
    public boolean missed() {
        double dx = Math.abs(currentX - startX);
        double dy = Math.abs(currentY - startY);
        double dist = (dx * dx + dy * dy);
        double maxRange = (range * .6) * (range * .6);
        return dist > maxRange;
    }

    /**
     * Checks if the projectile has hit the specified balloon.
     * A projectile can only hit a balloon once.
     *
     * @param balloon The balloon to check for collision.
     * @return true if the projectile hits the balloon, otherwise false.
     */
    public boolean didHit(Balloon balloon) {
        // If the balloon has already been hit, do not check again.
        if (hitBalloons.contains(balloon)) {
            return false;
        }

        // For non-Bomb projectiles, only check the original target.
        // Get the position of the balloon to check if the projectile is within range.
        int balloonX = balloon.getX();
        int balloonY = balloon.getY();

        // Calculate the distance between the current position of the projectile and the target balloon.
        double distance = Math.sqrt(Math.pow(currentX - balloonX, 2) + Math.pow(currentY - balloonY, 2));

        // If the distance is within the damage area, hit the balloon.
        if (distance <= (damageArea + 10)) {
            if (type == ProjectileImageSize.valueOf("GOO")) {
                balloon.goo(); // Apply the goo effect if this is a "GOO" projectile.
            }
            balloon.gotHit(true);
            hitBalloons.add(balloon); // Mark the balloon as hit.
            return true; // The projectile hit the balloon
        }

    return false; // No collision with the balloon
    }


    /**
     * Gets the number of remaining hits that the projectile can make.
     *
     * @return The number of remaining hits.
     */
    public int getRemainingHits() {
        return allowedHits;
    }

    /**
     * Decreases the allowed hits by one.
     */
    public void removeOneFromHitCount() {
        allowedHits--;
    }

    /**
     * Gets the image representation of the projectile.
     *
     * @return The image representing the projectile.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the type of the projectile.
     *
     * @param type The type of projectile (e.g., Goo, Dart).
     */
    public void setType(ProjectileImageSize type) {
        this.type = type;
    }

    /**
     * Sets the width and height of the projectile (currently not implemented).
     */
    private void setWidthHeight() {
        // Placeholder for width and height logic (currently unused)
    }

    /**
     * Gets the width of the projectile image.
     *
     * @return The width of the projectile image.
     */
    public int getWidth() {
        return type.getWidth();
    }

    /**
     * Gets the height of the projectile image.
     *
     * @return The height of the projectile image.
     */
    public int getHeight() {
        return type.getHeight();
    }

    public int getDamage() {
        return damage;
    }
    public ProjectileImageSize getType(){
        return type;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }
    public boolean isTracking(){
        return tracking;
    }

    public boolean isStillValid() {
        if(currentTarget.getLevel() <= 0){
            return false;
        }
        return true;
    }


}
