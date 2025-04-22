/**
 * The {@code TowerImageSize} enum defines the fixed image dimensions (width and height)
 * for various tower types in the tower defense game.
 * Each enum constant represents a specific tower and is associated with its image's
 * width and height in pixels. This is typically used for rendering towers at the correct
 * size in the game's user interface.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public enum TowerImageSize {
    DARTMONKEY(44, 44),
    NINJA(49, 49),
    BOMBTOWER(54, 54),
    TACSHOOTER(52, 52),
    WIZARD(47, 47),
    SUPERMONKEY(46, 46),
    ICETOWER(51, 51),
    SNIPERMONKEY(65, 65),
    GLUEGUNNER(55, 55);

    private final int width;
    private final int height;

    /**
     * Constructs a {@code TowerImageSize} enum constant with the specified
     * width and height.
     *
     * @param width  the width of the image in pixels
     * @param height the height of the image in pixels
     */
    TowerImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the tower image.
     *
     * @return the width in pixels
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the tower image.
     *
     * @return the height in pixels
     */
    public int getHeight() {
        return height;
    }
}
