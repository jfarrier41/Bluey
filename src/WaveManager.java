import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 * Manages the sequence of waves in the game, handling the initialization and progression of waves.
 * Each wave contains a list of bloons to spawn.
 */
public class WaveManager {
    /** The list of all waves in the game. */
    private final List<Wave> waves;

    /** The index of the currently active wave. */
    private int currentWaveIndex;

    /**
     * Constructs a WaveManager and initializes all waves.
     */
    public WaveManager() {
        this.waves = new ArrayList<>();
        this.currentWaveIndex = 0;
        initializeWaves();
    }

    /**
     * Initializes the wave list with predefined wave configurations.
     * Includes basic waves and an empty wave to signal end-of-game.
     */
    private void initializeWaves() {
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 7, 1.0),
                new BloonSpawnInfo(10,1,.5))));
        // Empty wave used to help end the game
        waves.add(new Wave(List.of()));
    }

    /**
     * Retrieves the current wave.
     *
     * @return the current {@link Wave}
     */
    public Wave getCurrentWave() {
        return waves.get(currentWaveIndex);
    }

    /**
     * Checks whether there is a subsequent wave available.
     *
     * @return true if another wave exists, false otherwise
     */
    public boolean hasNextWave() {
        return currentWaveIndex < waves.size() - 1;
    }

    /**
     * Advances to the next wave, if one exists.
     */
    public void nextWave() {
        if (hasNextWave()) {
            currentWaveIndex++;
        }
    }

    /**
     * Returns the index of the current wave.
     *
     * @return the current wave index
     */
    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }
}
