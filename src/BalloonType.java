/**
 * Represents different types of Bloons in the game, each with defined health and speed values.
 * Bloons are ordered in increasing strength, with special behavior or resistance starting at certain types.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public enum BalloonType {
    RED(1, 2.0),       // health, speed
    BLUE(1, 2.5),
    GREEN(1, 3.0),
    YELLOW(1, 4.0),
    PINK(1, 5.0),
    ZEBRA(3, 3.6),
    RAINBOW(5, 4),
    CERAMIC(20, 2),
    MOAB(200, 2),
    LEAD(1, 2.5);

    private final int health;
    private final double speed;

    /**
     * Constructs a new BalloonType with the specified health and speed.
     *
     * @param health the health of the Bloon
     * @param speed  the speed of the Bloon
     */
    BalloonType(int health, double speed) {
        this.health = health;
        this.speed = speed;
    }

    /**
     * Returns the health value of the Bloon.
     *
     * @return the Bloon's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the movement speed of the Bloon.
     *
     * @return the Bloon's speed
     */
    public double getSpeed() {
        return speed;
    }
}
