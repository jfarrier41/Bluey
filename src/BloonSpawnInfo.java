/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * Represents information about how a specific type of bloon is spawned during a wave.
 * Includes how many bloons to spawn, their strength, and the rate at which they appear.
 */
public class BloonSpawnInfo {
    /** The number of bloons to spawn. */
    private int amount;

    /** The strength level of the bloons (used to determine type or difficulty). */
    private final int strength;

    /** The time interval between spawning each bloon, in seconds. */
    private final double spawnRate;

    /**
     * Constructs a BloonSpawnInfo object with specified amount, strength, and spawn rate.
     *
     * @param amount    the number of bloons to spawn
     * @param strength  the strength level of the bloons
     * @param spawnRate the time between each bloon's spawn in seconds
     */
    public BloonSpawnInfo(int amount, int strength, double spawnRate) {
        this.amount = amount;
        this.strength = strength;
        this.spawnRate = spawnRate;
    }

    /**
     * Gets the number of bloons to spawn.
     *
     * @return the amount of bloons
     */
    public int getAmount() { return amount; }

    /**
     * Sets the number of bloons to spawn.
     *
     * @param amount the new amount of bloons
     */
    public void setAmount(int amount) { this.amount = amount; }

    /**
     * Gets the strength level of the bloons.
     *
     * @return the bloon strength
     */
    public int getStrength() { return strength; }

    /**
     * Gets the time interval between spawning each bloon.
     *
     * @return the spawn rate in seconds
     */
    public double getSpawnRate() { return spawnRate; }
}
