import java.util.List;

/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * Represents a wave of balloons (bloons) to be spawned in the game.
 * A wave contains a list of {@link BloonSpawnInfo} objects, each describing a bloon to spawn.
 */
public class Wave {
    /** The list of bloons to be spawned in this wave. */
    private final List<BloonSpawnInfo> bloons;

    /**
     * Constructs a Wave with the specified list of bloon spawn information.
     *
     * @param bloons the list of bloons to be included in this wave
     */
    public Wave(List<BloonSpawnInfo> bloons) {
        this.bloons = bloons;
    }

    /**
     * Returns the list of bloons in this wave.
     *
     * @return a list of {@link BloonSpawnInfo}
     */
    public List<BloonSpawnInfo> getBloons() {
        return bloons;
    }
}

