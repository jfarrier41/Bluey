public class BloonSpawnInfo {
    private int amount;  // Remove final to make it mutable
    private final int strength;
    private final double spawnRate; // Time between spawns in seconds

    public BloonSpawnInfo(int amount, int strength, double spawnRate) {
        this.amount = amount;
        this.strength = strength;
        this.spawnRate = spawnRate;
    }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }  // Setter to change the amount

    public int getStrength() { return strength; }
    public double getSpawnRate() { return spawnRate; }
}
