import java.awt.Point;

/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * Represents a segment of a path between two points in the game.
 * A segment can be either straight or curved.
 */
public class WaypointSegment {
    /** The starting point of the segment. */
    private Point startPoint;

    /** The ending point of the segment. */
    private Point endPoint;

    /** Whether the segment is curved or not. */
    private boolean isCurved;

    /**
     * Constructs a WaypointSegment with the given start and end points,
     * and a flag indicating if it is curved.
     *
     * @param startPoint the starting point of the segment
     * @param endPoint   the ending point of the segment
     * @param isCurved   true if the segment is curved, false if it is straight
     */
    public WaypointSegment(Point startPoint, Point endPoint, boolean isCurved) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isCurved = isCurved;
    }

    /**
     * Gets the starting point of the segment.
     *
     * @return the start point
     */
    public Point getStartPoint() { return startPoint; }

    /**
     * Gets the ending point of the segment.
     *
     * @return the end point
     */
    public Point getEndPoint() { return endPoint; }

    /**
     * Returns whether the segment is curved.
     *
     * @return true if the segment is curved, false otherwise
     */
    public boolean isCurved() { return isCurved; }
}
