import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Waypoints {
    private List<Point> points;

    public Waypoints(String mapName) {
        points = new ArrayList<>();
        loadWaypoints(mapName);
    }

    private void loadWaypoints(String mapName) {
        switch (mapName) {
            case "MonkeyLane":
                points.add(new Point(200, 295));
                points.add(new Point(400, 295));
                points.add(new Point(400, 213));
                points.add(new Point(517, 213));
                points.add(new Point(517, 477));
                points.add(new Point(685, 477));
                points.add(new Point(685, 135));
                points.add(new Point(400, 135));
                points.add(new Point(400, 60));
                points.add(new Point(400, 56));
                points.add(new Point(776, 56));
                points.add(new Point(776, 137));
                points.add(new Point(876, 137));
                points.add(new Point(876, 224));
                points.add(new Point(776, 224));
                points.add(new Point(776, 372));
                points.add(new Point(400, 372));
                points.add(new Point(400, 525));
                break;

            case "Maze":
                points.add(new Point(200, 98));
                points.add(new Point(342, 98));
                points.add(new Point(342, 183));
                points.add(new Point(271, 183));
                points.add(new Point(271, 470));
                points.add(new Point(343, 470));
                points.add(new Point(343, 270));
                points.add(new Point(412, 270));
                points.add(new Point(412, 312));
                points.add(new Point(480, 312));
                points.add(new Point(480, 207));
                points.add(new Point(548, 207));
                points.add(new Point(548, 377));
                points.add(new Point(444, 377));
                points.add(new Point(444, 462));
                points.add(new Point(604, 462));
                points.add(new Point(604, 368));
                points.add(new Point(658, 368));
                points.add(new Point(658, 397));
                points.add(new Point(733, 397));
                points.add(new Point(733, 288));
                points.add(new Point(645, 288));
                points.add(new Point(645, 202));
                points.add(new Point(776, 202));
                points.add(new Point(776, 110));
                points.add(new Point(617, 110));
                points.add(new Point(617, 30));

                points.add(new Point(881, 30));
                points.add(new Point(881, 535));

                break;

            case "ParkPath":
                points.add(new Point(500, 600));
                points.add(new Point(150, 550));
                points.add(new Point(250, 500));
                points.add(new Point(350, 450));
                points.add(new Point(450, 400));
                points.add(new Point(550, 350));
                points.add(new Point(650, 300));
                break;

            case "Tracks":
                points.add(new Point(50, 500));
                points.add(new Point(150, 550));
                points.add(new Point(250, 500));
                points.add(new Point(350, 450));
                points.add(new Point(450, 400));
                points.add(new Point(550, 350));
                points.add(new Point(650, 300));
                break;

            default:
                System.out.println("No waypoints defined for this map.");
                break;
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}
