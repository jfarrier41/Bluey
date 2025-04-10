public enum TowerImageSize {
    DARTMONKEY(44,44),
    NINJA(49,49),
    BOMBTOWER(54,54),
    TACSHOOTER(52,52),
    WIZARD(47,47),
    SUPERMONKEY(46,46),
    ICETOWER(51,51),
    SNIPERMONKEY(65,65),
    GLUEGUNNER(55,55);

    private final int width;
    private final int height;

    TowerImageSize(int width, int height) {
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
