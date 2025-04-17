import java.util.List;

/**
 * Represents a single wave in the game.
 * A wave contains one or more {@link BloonSpawnInfo} groups,
 * each defining how many Bloons to spawn, their type (strength), and spawn interval.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class Wave {

    /**
     * A list of Bloon spawn instructions for this wave.
     */
    private final List<BloonSpawnInfo> bloons;

    /**
     * Constructs a new {@code Wave} with the specified Bloon spawn information.
     *
     * @param bloons a list of {@link BloonSpawnInfo} objects that define how Bloons will spawn in this wave
     */
    public Wave(List<BloonSpawnInfo> bloons) {
        this.bloons = bloons;
    }

    /**
     * Returns the list of Bloon spawn info for this wave.
     *
     * @return a list of {@link BloonSpawnInfo} objects
     */
    public List<BloonSpawnInfo> getBloons() {
        return bloons;
    }
}

