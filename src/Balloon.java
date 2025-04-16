import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * The Balloon class represents a balloon object in the Bloons Tower Defense game.
 * Balloons follow a path defined by waypoints and can move along straight or curved segments.
 * Balloons have a level and health, and they can take damage, pop, and animate a popping effect.
 * The class handles the balloon's movement, rendering, and state changes.
 */
public class Balloon {
    /**
     * Represents the current position of the balloon in the game.
     */
    private Point currentPosition;  // Current position of the balloon

    /**
     * Index of the current segment in the waypoints path that the balloon is following.
     */
    private int currentSegmentIndex;  // Index of the current segment in the waypoints path

    /**
     * X and Y coordinates for the balloon's movement.
     */
    private double x, y;  // Coordinates for the balloon's movement

    /**
     * Movement speed of the balloon.
     */
    private double speed;  // Movement speed of the balloon

    /**
     * Indicates whether the balloon is moving along its path.
     */
    private boolean isMoving;  // Indicates whether the balloon is moving

    /**
     * Current level of the balloon, which may affect its strength or damage.
     */
    private int level;  // Current level of the balloon (e.g., strength, damage)

    /**
     * Health of the balloon, which decreases as it takes damage.
     */
    private int health;  // Health of the balloon

    /**
     * Whether the balloon is currently animating its pop effect.
     */
    private boolean animatePop;  // Whether the balloon is animating its pop

    /**
     * Indicates whether the balloon is affected by goo (e.g., slowing down the balloon).
     */
    private boolean gooed;  // Whether the balloon is affected by goo (e.g., slowed)

    /**
     * Whether the balloon is hidden, such as being camouflaged or invisible to certain towers.
     */
    private boolean hidden;  // Whether the balloon is hidden (e.g., camouflaged or invisible)

    /**
     * Whether the balloon has been hit by a tower or a projectile.
     */
    private boolean hit;  // Whether the balloon has been hit by a tower

    /**
     * Array of images used to represent different states or appearances of the balloon.
     */
    private final BufferedImage[] balloonImages;  // Array of images for different balloon states or types

    /**
     * The type of the balloon (e.g., Red, Blue, Lead, etc.), which determines its characteristics.
     */
    private BalloonType type;  // Type of the balloon (e.g., Red, Blue, Lead, etc.)

    /**
     * Represented the progress along the curved bloon movement
     * */
    private double t = 0.0;

    /**
     * Array that defines the downgrade order of balloon types, used for progression or upgrades.
     * The array follows a specific order:
     * Lead -> MOAB -> Ceramic -> Rainbow -> Zebra -> Pink -> Yellow -> Green -> Blue -> Red.
     */
    protected static final BalloonType[] downgradeOrder = {
            BalloonType.LEAD, BalloonType.MOAB, BalloonType.CERAMIC,
            BalloonType.RAINBOW, BalloonType.ZEBRA, BalloonType.PINK,
            BalloonType.YELLOW, BalloonType.GREEN, BalloonType.BLUE, BalloonType.RED
    };

    /**
     * Reference to the `Waypoints` object that manages the path the balloon follows.
     */
    private final Waypoints waypoints;  // Reference to the waypoints object for the balloon'

    /**
     * Constructor for the Balloon class.
     * Initializes the balloon with the given waypoints, images, and level.
     *
     * @param waypoints      The set of waypoints that the balloon will follow.
     * @param balloonImages  An array of images representing the different stages of the balloon.
     * @param level          The initial level of the balloon, which determines its speed and health.
     */
    public Balloon(Waypoints waypoints, BufferedImage[] balloonImages, int level) {
        this.waypoints = waypoints;
        this.balloonImages = balloonImages;
        this.level = level;
        this.currentSegmentIndex = 0;
        this.currentPosition = new Point(waypoints.getSegments().getFirst().getStartPoint()); // Start from first waypoint
        this.isMoving = true;
        this.x = currentPosition.x;
        this.y = currentPosition.y;
        this.type = getBalloonTypeFromLevel(level);
        this.health = type.getHealth();
        this.speed = type.getSpeed();

    }

    /**
     * Retrieves the appropriate {@link BalloonType} based on the specified level.
     * This method maps a given level to a corresponding {@link BalloonType},
     * using the order of {@link BalloonType} values. The level is clamped between
     * 0 and the total number of BalloonTypes to ensure it stays within bounds.
     *
     * @param level The level for which the corresponding {@link BalloonType} is to be retrieved.
     *              The level should be a positive integer.
     * @return The {@link BalloonType} corresponding to the specified level.
     *         If the level exceeds the number of available {@link BalloonType}s, the highest type is returned.
     */
    private BalloonType getBalloonTypeFromLevel(int level) {
        // You can map levels to types however you want
        // Here's a simple example mapping 0-9 to each BalloonType in order
        BalloonType[] types = BalloonType.values();
        int index = Math.max(0, Math.min(level, types.length - 1));
        return types[index];
    }

    /**
     * Updates the position of the balloon as it moves along its path.
     * The balloon will move along the waypoints, either in a straight line or along a curve.
     * If the balloon reaches the end of the path, it stops moving.
     */
    public void updatePosition() {
        if (!isMoving) return;

        if (hasReachedEnd()) {
            // If we've reached the end of the waypoints, stop moving
            isMoving = false;
            return;
        }

        WaypointSegment currentSegment = waypoints.getSegment(currentSegmentIndex);
        if(waypoints.getMapName().equals("MonkeyLane")){
            if(currentSegmentIndex == 6){
                hidden = true;
            }
            if(currentSegmentIndex == 7){
                hidden = false;
            }
            if(currentSegmentIndex == 18){
                hidden = true;
            }
            if(currentSegmentIndex == 19){
                hidden = false;
            }
        }
        if (currentSegment.isCurved()) {
            moveAlongCurve(currentSegment);
        } else {
            moveAlongLine(currentSegment);
        }

        // Update x and y for rendering
        this.x = currentPosition.x;
        this.y = currentPosition.y;
    }

    /**
     * Moves the balloon along a straight line segment.
     *
     * @param segment The current line segment of the path.
     */
    private void moveAlongLine(WaypointSegment segment) {
        Point end = segment.getEndPoint();

        // Calculate the distance between the current position and the endpoint
        double dx = end.x - x;
        double dy = end.y - y;
        double distance = Math.sqrt(dx * dx + dy * dy); // Correct distance formula (Pythagorean theorem)

        if (distance < speed) {
            // If the distance to the endpoint is less than the speed, move directly to the endpoint
            currentPosition = new Point(end.x, end.y);
            currentSegmentIndex++;
        } else {
            // Otherwise, move along the segment by the speed amount
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
            currentPosition = new Point((int) x, (int) y);
        }
    }

    /**
     * Moves the balloon along a curved segment using a quadratic Bezier curve.
     *
     * @param segment The current curved segment of the path.
     */
    private void moveAlongCurve(WaypointSegment segment) {
        Point start = segment.getStartPoint();
        Point end = segment.getEndPoint();
        // Check for zero-length segment (start and end points are the same)
        if (start.equals(end)) {
            // Skip processing or handle the segment properly (e.g., skip to the next segment)
            currentSegmentIndex++;
            return;
        }
        // Calculate the direction vector from start to end
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        // Compute the midpoint of the segment
        int midX = (start.x + end.x) / 2;
        int midY = (start.y + end.y) / 2;
        // Calculate the perpendicular vector (rotate 90 degrees)
        int perpX = -dy;  // Perpendicular to (dx, dy)
        int perpY = dx;
        // Calculate the distance between start and end points
        double distance = Math.sqrt(dx * dx + dy * dy);
        // Add a check to prevent division by zero if distance is 0 (though this case should be handled above)
        if (distance == 0) {
            return;  // Avoid division by zero
        }
        // Adjust sharpness of curve by controlling the offset (strength of curve)
        int offsetAmount = (int) (distance * .38);  // Increase this factor for sharper curves
        // Determine direction for curve (left or right) based on cross product
        double direction = (start.x * end.y + end.x * y + x * start.y) - (start.y * end.x + end.y * x + y * start.x);
        // Determine control point based on direction (left or right) and the curve's radius
        Point controlPoint;
        if (direction > 0) {
            // Counterclockwise (left curve)
            controlPoint = new Point(
                    (int) (midX + (perpX * offsetAmount) / distance),
                    (int) (midY + (perpY * offsetAmount) / distance)
            );
        } else {
            // Clockwise (right curve)
            controlPoint = new Point(
                    (int) (midX - (perpX * offsetAmount) / distance),
                    (int) (midY - (perpY * offsetAmount) / distance)
            );
        }
        // Quadratic Bezier curve formula to simulate a quarter circle
        if (t >= 1.0) {
            // If we reached the end of the curve, move to the next segment
            currentPosition = new Point(end.x, end.y);
            t = 0.0;  // Reset for next segment
            currentSegmentIndex++;  // Move to next segment
            return;
        }
        // Quadratic Bezier curve formula
        double xPos = Math.pow(1 - t, 2) * start.x + 2 * (1 - t) * t * controlPoint.x + Math.pow(t, 2) * end.x;
        double yPos = Math.pow(1 - t, 2) * start.y + 2 * (1 - t) * t * controlPoint.y + Math.pow(t, 2) * end.y;
        // Update position
        x = xPos;
        y = yPos;
        currentPosition = new Point((int) x, (int) y);
        // Increment t to animate the curve
        int val = currentSegmentIndex / 7;

        t += speed * (0.005 - .001 * val);
    }

    /**
     * Renders the balloon at its current position.
     * Animates the popping effect when the balloon is popped.
     *
     * @param g The graphics context used to draw the balloon.
     */
    public void draw(Graphics g) {
        if(balloonImages != null){
            if(animatePop) {
                Random rand = new Random();
                int randomNum = rand.nextInt(4) + 1;
                String soundFile = "Pop" + randomNum + ".wav";
                new SoundEffect(soundFile, false, .8f);
                g.drawImage(balloonImages[balloonImages.length - 1], (int) x-20, (int) y-25, 50, 50, null);
                animatePop = false;
            } else if (getLevel() != 8) {
                g.drawImage(balloonImages[getLevel()], (int) x - 10, (int) y - 10, 27, 33, null);
            } else if (level == 8) {
                g.drawImage(balloonImages[getLevel()], (int) x - 50, (int) y - 25, 100, 50, null);
            } else {
                g.setColor(Color.RED);
                g.fillOval((int) x - 10, (int) y - 10, 30, 30);
            }
        }
    }

    /**
     * Reduces the health of the balloon when it is hit by a projectile.
     * If the balloon's health reaches zero, it decreases in level and animates a popping effect.
     *
     * @param damage The amount of damage to deal to the balloon.
     */
    public void takeDamage(int damage) {
        while (damage > 0 && level > -1) {
            int currentHealth = this.health;

            if (damage >= currentHealth) {
                // If damage is more than or equal to current health, pop this level
                damage -= currentHealth;
                this.health = 0;  // Balloon level is popped

                // Decrement level
                level--;

                // Set the new type based on the downgraded level
                if (level > -1) {
                    int currentIndex = getCurrentTypeIndex();
                    if (currentIndex < downgradeOrder.length - 1) {
                        BalloonType newType = downgradeOrder[currentIndex + 1];
                        setType(newType);
                        this.health = newType.getHealth();
                        animatePop = true;
                    }
                }
            } else {
                // If damage is less than the current health, just apply it
                this.health -= damage;
                damage = 0;  // No leftover damage
            }
        }

        // If health reaches 0, completely pop the balloon (level is 0)
        if (level <= -1) {
            this.health = 0; // Balloon is fully popped
        }
    }

    /**
     * Checks if the balloon has been popped.
     *
     * @return true if the balloon has no remaining health and level, false otherwise.
     */
    public boolean isPopped() {
        return level <= 0 && health <= 0;
    }

    public int getLevel() {
        return level;
    }

    /**
     * Checks if the balloon has reached the end of the path.
     *
     * @return true if the balloon has reached the end of its path, false otherwise.
     */
    public boolean hasReachedEnd() {
        return currentSegmentIndex >= waypoints.getSegmentCount();
    }

    /**
     * Retrieves the X coordinate of the balloon's current position, adjusted by a constant offset.
     * The position is rounded to the nearest integer and an offset of 236 is subtracted.
     *
     * @return The adjusted X coordinate of the balloon's current position.
     */
    public int getX() {
        return Math.round(currentPosition.x) - 236;
    }

    /**
     * Retrieves the Y coordinate of the balloon's current position, adjusted by a constant offset.
     * The position is rounded to the nearest integer and an offset of 6 is subtracted.
     *
     * @return The adjusted Y coordinate of the balloon's current position.
     */
    public int getY() {
        return Math.round(currentPosition.y) - 6;
    }

    /**
     * Freezes the balloon, stopping its movement.
     */
    public void freeze() {
        this.speed = 0;
    }

    /**
     * Unfreezes the balloon, restoring its movement speed.
     */
    public void unfreeze() {
        this.speed = 2 + level * .5;
    }

    /**
     * Retrieves the current speed of the balloon.
     *
     * @return The current speed of the balloon.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Halves the balloon's speed when it is "gooed".
     * This method ensures that the speed is only reduced once.
     */
    public void goo() {
        if (!gooed) {
            gooed = true;
            this.speed = speed / 2;
        }
    }

    /**
     * Restores the balloon's speed when the goo effect is removed.
     * This method ensures that the speed is only restored once.
     */
    public void unGoo() {
        if (gooed) {
            gooed = false;
            this.speed = speed * 2;
        }
    }

    /**
     * Retrieves the current segment index the balloon is traveling on.
     *
     * @return The index of the current waypoint segment.
     */
    public int getCurrentSegmentIndex() {
        return currentSegmentIndex;
    }

    /**
     * Checks if the balloon is hidden.
     *
     * @return {@code true} if the balloon is hidden, {@code false} otherwise.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Marks the balloon as hit, setting the hit status to the provided value.
     *
     * @param hit {@code true} if the balloon is hit, {@code false} otherwise.
     */
    public void gotHit(boolean hit) {
        this.hit = hit;
    }

    /**
     * Checks if the balloon is hit.
     *
     * @return {@code true} if the balloon is hit, {@code false} otherwise.
     */
    public boolean isHit() {
        return hit;
    }

    /**
     * Retrieves the current type of the balloon.
     *
     * @return The {@link BalloonType} of the balloon.
     */
    public BalloonType getType() {
        return type;
    }

    /**
     * Sets the type of the balloon and updates its health and speed accordingly.
     *
     * @param type The {@link BalloonType} to set for the balloon.
     */
    public void setType(BalloonType type) {
        this.type = type;
        this.health = type.getHealth();
        this.speed = type.getSpeed(); // If speed matters
    }

    /**
     * Retrieves the index of the current balloon type in the downgrade order.
     * This index determines the current type's position in the upgrade/downgrade chain.
     *
     * @return The index of the current balloon type in the downgrade order array.
     */
    private int getCurrentTypeIndex() {
        for (int i = 0; i < downgradeOrder.length; i++) {
            if (downgradeOrder[i] == this.type) {
                return i;
            }
        }
        return downgradeOrder.length - 1; // Default to last if not found
    }
}
