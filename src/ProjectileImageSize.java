/**
 * The {@code ProjectileImageSize} enum defines the fixed dimensions (width and height)
 * for projectile images used in the tower defense game.
 * Each enum constant corresponds to a specific projectile type and specifies its
 * associated image size in pixels. This is used for accurately rendering projectile
 * graphics during gameplay.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public enum ProjectileImageSize {
    DART(30, 10),
    NINJASTAR(17, 17),
    BOMB(30, 30),
    TAC(20, 7),
    GOO(30, 10),
    ORB(30, 30);

    private final int width;
    private final int height;

    /**
     * Constructs a {@code ProjectileImageSize} enum constant with the specified dimensions.
     *
     * @param width  the width of the image in pixels
     * @param height the height of the image in pixels
     */
    ProjectileImageSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the projectile image in pixels.
     *
     * @return the image width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the projectile image in pixels.
     *
     * @return the image height
     */
    public int getHeight() {
        return height;
    }
}
