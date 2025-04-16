/**
 * @Author Jace Claassen
 * @Author Joseph Farrier
 * Enum representing different types of balloons in the game,
 * each with distinct health and speed values.
 */
public enum BalloonType {
    /**
     * Red balloon: weakest and slowest basic balloon.
     */
    RED(1, 2.0),

    /**
     * Blue balloon: slightly faster than red.
     */
    BLUE(1, 2.5),

    /**
     * Green balloon: faster than blue.
     */
    GREEN(1, 3.0),

    /**
     * Yellow balloon: faster than green.
     */
    YELLOW(1, 4.0),

    /**
     * Pink balloon: one of the fastest basic balloons.
     */
    PINK(1, 5.0),

    /**
     * Zebra balloon: moderate speed with more health.
     */
    ZEBRA(3, 3.6),

    /**
     * Rainbow balloon: even stronger with higher speed.
     */
    RAINBOW(5, 4),

    /**
     * Ceramic balloon: tough balloon with high health and low speed.
     */
    CERAMIC(20, 2),

    /**
     * MOAB (Massive Ornary Air Balloon): extremely tough, very slow.
     */
    MOAB(200, 1),

    /**
     * Lead balloon: immune to all attacks besides wizard and bomb tower..
     */
    LEAD(1, 2.5);

    private final int health;
    private final double speed;

    /**
     * Constructs a balloon type with specified health and speed.
     *
     * @param health The number of hits the balloon can take.
     * @param speed The movement speed of the balloon.
     */
    BalloonType(int health, double speed) {
        this.health = health;
        this.speed = speed;
    }

    /**
     * Returns the health of the balloon type.
     *
     * @return Balloon health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the speed of the balloon type.
     *
     * @return Balloon speed.
     */
    public double getSpeed() {
        return speed;
    }
}
