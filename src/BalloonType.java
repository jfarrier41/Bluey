public enum BalloonType {
    RED(1, 2.0),       // health, speed
    BLUE(1, 2.5),
    GREEN(1, 5.0),
    YELLOW(1, 3.5),
    PINK(1, 4.0),
    BLACK(2, 3.0),
    ZEBRA(2, 3.2),
    LEAD(3, 2.5),
    CERAMIC(5, 2.2),
    MOAB(3, 1);

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
