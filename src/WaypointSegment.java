import java.awt.Point;

/**
 * Represents a segment between two waypoints on a path in the tower defense game.
 * A {@code WaypointSegment} contains a start point, an end point, and a flag indicating
 * whether the segment is curved. This class is typically used to define enemy movement
 * paths across the game map.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class WaypointSegment {

    /** The starting point of the segment. */
    private Point startPoint;

    /** The ending point of the segment. */
    private Point endPoint;

    /** Flag indicating whether the segment is a curved path. */
    private boolean isCurved;

    /**
     * Constructs a new {@code WaypointSegment} with the given start and end points,
     * and a flag indicating if the segment is curved.
     *
     * @param startPoint the starting {@link Point} of the segment
     * @param endPoint the ending {@link Point} of the segment
     * @param isCurved {@code true} if the segment is curved; {@code false} otherwise
     */
    public WaypointSegment(Point startPoint, Point endPoint, boolean isCurved) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isCurved = isCurved;
    }

    /**
     * Returns the starting point of the segment.
     *
     * @return the start {@link Point}
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Returns the ending point of the segment.
     *
     * @return the end {@link Point}
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * Returns whether this segment is curved.
     *
     * @return {@code true} if the segment is curved; {@code false} otherwise
     */
    public boolean isCurved() {
        return isCurved;
    }
}
