import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Balloon {
    private List<Point> waypoints;
    private int currentWaypointIndex;
    private double x, y;
    private double speed;
    private BufferedImage[] balloonImages;
    private int level;

    public Balloon(Waypoints waypoints, BufferedImage[] balloonImages, int level) {
        this.waypoints = waypoints.getPoints();
        this.speed = 2;
        this.balloonImages = balloonImages;
        this.level = level;
        this.currentWaypointIndex = 0;
        if (!this.waypoints.isEmpty()) {
            this.x = this.waypoints.get(0).x;
            this.y = this.waypoints.get(0).y;
        }
    }

    public void updatePosition() {
        if (currentWaypointIndex >= waypoints.size() - 1) {
            return; // Reached the end
        }

        Point target = waypoints.get(currentWaypointIndex + 1);
        double dx = target.x - x;
        double dy = target.y - y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < speed) {
            x = target.x;
            y = target.y;
            currentWaypointIndex++;
        } else {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    public void draw(Graphics g) {
        if (balloonImages != null && level >= 0 && level < balloonImages.length) {
            g.drawImage(balloonImages[level], (int) x - 10, (int) y - 10, 27, 33, null);
        } else {
            g.setColor(Color.RED);
            g.fillOval((int) x - 10, (int) y - 10, 30, 30);
        }
    }

    public boolean hasReachedEnd() {
        return currentWaypointIndex >= waypoints.size() - 1;
    }

    public void handleCurvedMovement(Point controlPoint) {
        if (currentWaypointIndex >= waypoints.size() - 1) {
            return; // Reached the end
        }

        Point start = waypoints.get(currentWaypointIndex);
        Point end = waypoints.get(currentWaypointIndex + 1);

        double t = speed / start.distance(end);
        if (t > 1) {
            t = 1;
            currentWaypointIndex++;
        }

        double oneMinusT = 1 - t;
        x = (oneMinusT * oneMinusT * start.x) + (2 * oneMinusT * t * controlPoint.x) + (t * t * end.x);
        y = (oneMinusT * oneMinusT * start.y) + (2 * oneMinusT * t * controlPoint.y) + (t * t * end.y);
    }

    public int getLevel() {
        return level;
    }
}


