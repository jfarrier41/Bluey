public enum BalloonType {
    RED(1, 2.0),       // health, speed
    BLUE(1, 2.5),
    GREEN(1, 3.0),
    YELLOW(1, 4.0),
    PINK(1, 5.0),
    ZEBRA(3, 3.6),
    RAINBOW(5, 4),
    CERAMIC(20, 2),
    MOAB(200, 1),
    LEAD(1, 2.5);

    private final int health;
    private final double speed;

    BalloonType(int health, double speed) {
        this.health = health;
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    }
}
