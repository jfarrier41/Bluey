import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Joseph Farrier
 * @author Jace Claassen
 * The Projectile class represents a projectile that can be fired by a tower.
 * It tracks its movement, target, and the damage it causes to balloons.
 * The projectile can move in a straight line or track its target,
 * and it can hit balloons within a certain damage area.
 */
public class Projectile {
    public final double startX, startY;
    public final double damageArea;
    public double currentX, currentY;
    public double angle;
    public int range;
    public int allowedHits;
    public boolean tracking;
    public double speed;
    protected ProjectileImageSize type;
    protected BufferedImage image;
    private Set<Balloon> hitBalloons = new HashSet<>();
    private Balloon currentTarget;
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
                      boolean tracking, BufferedImage projectileImage, ProjectileImageSize type, int damage) {
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
    }

    /**
     * Updates the position of the projectile.
     * If the projectile is tracking a target, it moves toward that target; otherwise, it moves in a straight line.
     */
    public void update() {
        /** Math for updating angle provided by CHATGPT*/
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
        if (hitBalloons.contains(balloon)) {
            return false;
        }

        int balloonX = balloon.getX();
        int balloonY = balloon.getY();

        /** Math to Calculate the distance between the current position and Target Provided by ChatGPT */
        double distance = Math.sqrt(Math.pow(currentX - balloonX, 2) + Math.pow(currentY - balloonY, 2));

        if (distance <= (damageArea + 10)) {
            if (type == ProjectileImageSize.valueOf("GOO")) {
                balloon.goo();
            }
            balloon.gotHit(true);
            hitBalloons.add(balloon);
            return true;
        }

        return false;
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

    /**
     * Gets the damage value of the projectile.
     *
     * @return The damage value of the projectile.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Gets the type of projectile.
     *
     * @return The type of projectile (e.g., Goo, Dart).
     */
    public ProjectileImageSize getType() {
        return type;
    }

    /**
     * Sets whether the projectile tracks a target balloon.
     *
     * @param tracking true if the projectile should track a target, false otherwise.
     */
    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    /**
     * Checks if the projectile is still valid (target's level is greater than zero).
     *
     * @return true if the projectile is still valid, false otherwise.
     */
    public boolean isStillValid() {
        if (currentTarget.getLevel() <= 0) {
            return false;
        }
        return true;
    }
}
