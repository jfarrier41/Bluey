public enum ProjectileType {
    DART(30, 10),
    NINJASTAR(17, 17),
    BOMB(40, 40),
    TAC(50, 50),
    WIZARD(70, 70),
    PURPLELAZER(80, 80),
    ORANGELAZER(90, 90),
    GOO(30, 30),
    EXPLOSION(30, 30),
    SPIKEYBALL(30, 30),
    BLADE(30, 30);

    private final int width;
    private final int height;

    ProjectileType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
