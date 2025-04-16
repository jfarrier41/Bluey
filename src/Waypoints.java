import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * Represents the collection of waypoint segments that define the path for balloons to follow on a specific map.
 * Each map has a specific sequence of straight or curved segments.
 */
public class Waypoints {
    /** The list of waypoint segments forming the complete path. */
    private final List<WaypointSegment> segments;

    /** The name of the map for which these waypoints are defined. */
    String mapName;

    /**
     * Constructs a Waypoints object for the given map name.
     * Automatically loads the waypoint segments for that map.
     *
     * @param mapName the name of the map
     */
    public Waypoints(String mapName) {
        segments = new ArrayList<>();
        loadWaypoints(mapName);
        this.mapName = mapName;
    }

    /**
     * Loads the waypoint segments specific to the given map.
     *
     * @param mapName the name of the map
     */
    private void loadWaypoints(String mapName) {
        switch (mapName) {
            case "MonkeyLane":
                segments.add(new WaypointSegment(new Point(200, 295), new Point(400, 295), false));
                segments.add(new WaypointSegment(new Point(400, 295), new Point(400, 213), false));
                segments.add(new WaypointSegment(new Point(400, 213), new Point(517, 213), false));
                segments.add(new WaypointSegment(new Point(517, 213), new Point(517, 477), false));
                segments.add(new WaypointSegment(new Point(517, 477), new Point(685, 477), false));
                segments.add(new WaypointSegment(new Point(685, 477), new Point(685, 457), false));
                segments.add(new WaypointSegment(new Point(685, 457), new Point(685, 295), false));

                segments.add(new WaypointSegment(new Point(685, 295), new Point(685, 135), false));
                segments.add(new WaypointSegment(new Point(685, 135), new Point(400, 135), false));
                segments.add(new WaypointSegment(new Point(400, 135), new Point(400, 60), false));
                segments.add(new WaypointSegment(new Point(400, 60), new Point(400, 56), false));
                segments.add(new WaypointSegment(new Point(400, 56), new Point(776, 56), false));
                segments.add(new WaypointSegment(new Point(776, 56), new Point(776, 137), false));
                segments.add(new WaypointSegment(new Point(776, 137), new Point(876, 137), false));
                segments.add(new WaypointSegment(new Point(876, 137), new Point(876, 224), false));
                segments.add(new WaypointSegment(new Point(876, 224), new Point(776, 224), false));
                segments.add(new WaypointSegment(new Point(776, 224), new Point(776, 372), false));

                segments.add(new WaypointSegment(new Point(776, 372), new Point(610, 372), false));
                segments.add(new WaypointSegment(new Point(610, 372), new Point(430, 372), false));

                segments.add(new WaypointSegment(new Point(776, 372), new Point(400, 372), false));
                segments.add(new WaypointSegment(new Point(400, 372), new Point(400, 525), false));
                break;

            case "Maze":
                segments.add(new WaypointSegment(new Point(200, 98), new Point(342, 98), false));
                segments.add(new WaypointSegment(new Point(342, 98), new Point(342, 183), false));
                segments.add(new WaypointSegment(new Point(342, 183), new Point(271, 183), false));
                segments.add(new WaypointSegment(new Point(271, 183), new Point(271, 470), false));
                segments.add(new WaypointSegment(new Point(271, 470), new Point(343, 470), false));
                segments.add(new WaypointSegment(new Point(343, 470), new Point(343, 270), false));
                segments.add(new WaypointSegment(new Point(343, 270), new Point(412, 270), false));
                segments.add(new WaypointSegment(new Point(412, 270), new Point(412, 312), false));
                segments.add(new WaypointSegment(new Point(412, 312), new Point(480, 312), false));
                segments.add(new WaypointSegment(new Point(480, 312), new Point(480, 207), false));
                segments.add(new WaypointSegment(new Point(480, 207), new Point(548, 207), false));
                segments.add(new WaypointSegment(new Point(548, 207), new Point(548, 377), false));
                segments.add(new WaypointSegment(new Point(548, 377), new Point(444, 377), false));
                segments.add(new WaypointSegment(new Point(444, 377), new Point(444, 462), false));
                segments.add(new WaypointSegment(new Point(444, 462), new Point(604, 462), false));
                segments.add(new WaypointSegment(new Point(604, 462), new Point(604, 368), false));
                segments.add(new WaypointSegment(new Point(604, 368), new Point(658, 368), false));
                segments.add(new WaypointSegment(new Point(658, 368), new Point(658, 397), false));
                segments.add(new WaypointSegment(new Point(658, 397), new Point(733, 397), false));
                segments.add(new WaypointSegment(new Point(733, 397), new Point(733, 288), false));
                segments.add(new WaypointSegment(new Point(733, 288), new Point(645, 288), false));
                segments.add(new WaypointSegment(new Point(645, 288), new Point(645, 202), false));
                segments.add(new WaypointSegment(new Point(645, 202), new Point(776, 202), false));
                segments.add(new WaypointSegment(new Point(776, 202), new Point(776, 110), false));
                segments.add(new WaypointSegment(new Point(776, 110), new Point(617, 110), false));
                segments.add(new WaypointSegment(new Point(617, 110), new Point(617, 30), false));
                segments.add(new WaypointSegment(new Point(617, 30), new Point(881, 30), false));
                segments.add(new WaypointSegment(new Point(881, 30), new Point(881, 535), false));
                break;

            case "SpringTrack":
                //Lap 1
                segments.add(new WaypointSegment(new Point(960, 393), new Point(466, 393), false));
                segments.add(new WaypointSegment(new Point(466, 393), new Point(350, 260), true));
                segments.add(new WaypointSegment(new Point(350, 260), new Point(466, 116), true));
                segments.add(new WaypointSegment(new Point(466, 116), new Point(695, 116), false));
                segments.add(new WaypointSegment(new Point(695, 116), new Point(811, 260), true));
                segments.add(new WaypointSegment(new Point(811, 260), new Point(696, 427), true));
                //Lap2
                segments.add(new WaypointSegment(new Point(695, 427), new Point(466, 427), false));
                segments.add(new WaypointSegment(new Point(466, 427), new Point(313, 260), true));
                segments.add(new WaypointSegment(new Point(313, 260), new Point(466, 82), true));
                segments.add(new WaypointSegment(new Point(466, 82), new Point(695, 82), false));
                segments.add(new WaypointSegment(new Point(695, 83), new Point(848, 260), true));
                segments.add(new WaypointSegment(new Point(848, 260), new Point(680, 462), true));
                //Lap3
                segments.add(new WaypointSegment(new Point(680, 462), new Point(466, 462), false));
                segments.add(new WaypointSegment(new Point(466, 462), new Point(279, 260), true));
                segments.add(new WaypointSegment(new Point(279, 260), new Point(466, 46), true));
                segments.add(new WaypointSegment(new Point(466, 46), new Point(950, 46), false));
                break;

            default:
                System.out.println("No waypoints defined for this map.");
                break;
        }
    }

    /**
     * Returns the list of waypoint segments that make up the path.
     *
     * @return a list of {@link WaypointSegment} objects
     */
    public List<WaypointSegment> getSegments() {
        return segments;
    }

    /**
     * Gets the total number of segments in the path.
     *
     * @return the number of waypoint segments
     */
    public int getSegmentCount() {
        return segments.size();
    }

    /**
     * Retrieves the waypoint segment at the specified index.
     *
     * @param index the index of the segment to retrieve
     * @return the {@link WaypointSegment} at the specified index
     */
    public WaypointSegment getSegment(int index) {
        return segments.get(index);
    }

    /**
     * Gets the name of the map associated with these waypoints.
     *
     * @return the map name
     */
    public String getMapName() {
        return mapName;
    }
}
