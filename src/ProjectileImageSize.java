public enum ProjectileImageSize {
    DART(30, 10),
    NINJASTAR(17, 17),
    BOMB(30, 30),
    TAC(20, 7),
    GOO(30, 10),
    ORB(30, 30);

    private final int width;
    private final int height;

    ProjectileImageSize(int width, int height) {
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
