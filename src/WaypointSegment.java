import java.awt.Point;

public class WaypointSegment {
    private Point startPoint;
    private Point endPoint;
    private boolean isCurved;

    public WaypointSegment(Point startPoint, Point endPoint, boolean isCurved) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isCurved = isCurved;
    }

    public Point getStartPoint() { return startPoint; }
    public Point getEndPoint() { return endPoint; }
    public boolean isCurved() { return isCurved; }
}
