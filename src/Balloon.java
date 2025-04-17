import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

/**
 * The Balloon class represents a balloon object in the Bloons Tower Defense game.
 * Balloons follow a path defined by waypoints and can move along straight or curved segments.
 * Balloons have a level and health, and they can take damage, pop, and animate a popping effect.
 * The class handles the balloon's movement, rendering, and state changes.
 */
public class Balloon {
    private Point currentPosition;
    private int currentSegmentIndex;
    private Waypoints waypoints;
    private BufferedImage[] balloonImages;
    private double x, y;
    private double speed;
    private int level;
    private int health;
    private BalloonType type;
    private boolean isMoving;
    private boolean animatePop;
    protected boolean gooed;
    protected boolean frozen;
    private boolean hidden;
    private boolean hit;
    private boolean popped;
    private static final BalloonType[] downgradeOrder = {
            BalloonType.LEAD, BalloonType.MOAB, BalloonType.CERAMIC,
            BalloonType.RAINBOW, BalloonType.ZEBRA, BalloonType.PINK,
            BalloonType.YELLOW, BalloonType.GREEN, BalloonType.BLUE, BalloonType.RED
    };

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
        this.currentPosition = new Point(waypoints.getSegments().get(0).getStartPoint()); // Start from first waypoint
        this.isMoving = true;
        this.x = currentPosition.x;
        this.y = currentPosition.y;
        // Determine BalloonType based on level
        this.type = getBalloonTypeFromLevel(level);
        this.health = type.getHealth();
        this.speed = type.getSpeed();

    }

    private BalloonType getBalloonTypeFromLevel(int level) {
        // You can map levels to types however you want
        // Here's a simple example mapping 1-10 to each BalloonType in order
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
        Point start = segment.getStartPoint();
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

    private double t = 0.0; // Progress along the curve

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
        if (balloonImages != null && level >= 0) {
            if (animatePop) {
                Random rand = new Random();
                int randomNum = rand.nextInt(4) + 1;
                String soundFile = "Pop" + randomNum + ".wav";
                new SoundEffect(soundFile, false, .8f);
                g.drawImage(balloonImages[balloonImages.length - 1], (int) x - 20, (int) y - 25, 50, 50, null);
                animatePop = false;
            } else if (level != 8) {
                g.drawImage(balloonImages[level], (int) x - 10, (int) y - 10, 27, 33, null);
            } else {
                g.drawImage(balloonImages[level], (int) x - 50, (int) y - 25, 100, 50, null);
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
            popped = true;
        }
    }

    /**
     * Checks if the balloon has been popped.
     *
     * @return true if the balloon has no remaining health and level, false otherwise.
     */
    public boolean isPopped() {
        return popped;
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

    public int getX() {
        return Math.round(currentPosition.x) - 236;
    }

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

    public double getSpeed() {
        return speed;
    }

    public void goo (){
        if(!gooed){
            gooed = true;
            this.speed = speed / 2;
        }
    }
    public void unGoo(){
        if(gooed){
            gooed = false;
            this.speed = speed * 2;
        }

    }

    public int getCurrentSegmentIndex() {
        return currentSegmentIndex;
    }
    public boolean isHidden(){
        return hidden;
    }

    public void gotHit(boolean hit) {
        this.hit = hit;
    }
    public boolean isHit() {
        return hit;
    }

    public BalloonType getType(){
        return type;
    }
    public void setType(BalloonType type) {
        this.type = type;
        this.health = type.getHealth();
        this.speed = type.getSpeed(); // If speed matters
    }

    private int getCurrentTypeIndex() {
        for (int i = 0; i < downgradeOrder.length; i++) {
            if (downgradeOrder[i] == this.type) {
                return i;
            }
        }
        return downgradeOrder.length - 1; // Default to last if not found
    }

}
