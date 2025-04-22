/**
 * Represents a group of Bloons to be spawned during a wave.
 * Contains the number of Bloons, their strength level, and the time interval between spawns.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class BloonSpawnInfo {

    /**
     * The number of Bloons to spawn in this group.
     */
    private int amount;

    /**
     * The strength level of the Bloons.
     * <p>
     * Strength corresponds to the Bloon type index:
     * 0 = Red, 1 = Blue, 2 = Green, 3 = Yellow, 4 = Pink,
     * 5 = Zebra, 6 = Rainbow, 7 = Ceramic, 8 = MOAB, 9 = Lead.
     * </p>
     */
    private final int strength;

    /**
     * The time interval between each Bloon spawn, in seconds.
     * For example, 0.5 means one Bloon spawns every 500 milliseconds.
     */
    private final double spawnRate;

    /**
     * Constructs a new {@code BloonSpawnInfo} with the given amount, strength, and spawn rate.
     *
     * @param amount     the number of Bloons to spawn
     * @param strength   the strength level of the Bloons
     * @param spawnRate  the interval between spawns in seconds
     */
    public BloonSpawnInfo(int amount, int strength, double spawnRate) {
        this.amount = amount;
        this.strength = strength;
        this.spawnRate = spawnRate;
    }

    /**
     * Returns the number of Bloons to spawn.
     *
     * @return the Bloon amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the number of Bloons to spawn.
     *
     * @param amount the new Bloon amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns the strength level of the Bloons.
     *
     * @return the strength index
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Returns the spawn rate between Bloons in seconds.
     *
     * @return the spawn interval in seconds
     */
    public double getSpawnRate() {
        return spawnRate;
    }
}
