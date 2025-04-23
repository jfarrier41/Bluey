import java.util.ArrayList;
import java.util.List;

/**
 * Manages a sequence of waves in the game, including the spawning of Bloons.
 * The {@code WaveManager} class handles the initialization of waves, tracking the current wave,
 * and transitioning to the next wave.
 * @Author: Jace Claassen
 * @Author: Joseph Farrier
 */
public class WaveManager {

    /**
     * A list of all the waves in the game.
     * Each wave contains Bloon spawn information and properties like spawn rate and type.
     */
    private final List<Wave> waves;

    /**
     * The index of the current wave.
     * This is used to track which wave is currently being processed.
     */
    private int currentWaveIndex;

    /**
     * Constructs a new {@code WaveManager} and initializes the list of waves.
     * The constructor also calls {@link #initializeWaves()} to populate the waves with data.
     */
    public WaveManager(boolean nateWave) {
        this.waves = new ArrayList<>();
        this.currentWaveIndex = 0;
        if (nateWave) {
            initializeNateWaves();
        } else {
            initializeWaves();
        }
    }

    /**
     * Initializes the list of waves with predefined spawn information.
     * This method adds a variety of waves with different types of Bloons and spawn rates.
     */
    private void initializeWaves() {
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 0, 1.0)))); // 20 red
        waves.add(new Wave(List.of(new BloonSpawnInfo(15, 1, 0.9)))); // 15 blue
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 2, 0.8), new BloonSpawnInfo(10, 0, 1.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 2, 0.7))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 3, 0.6), new BloonSpawnInfo(10, 1, 0.8))));

        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 4, 0.7), new BloonSpawnInfo(10, 3, 0.6)))); // first pinks
        waves.add(new Wave(List.of(new BloonSpawnInfo(15, 4, 0.6), new BloonSpawnInfo(5, 2, 0.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 3, 0.5), new BloonSpawnInfo(10, 4, 0.6))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 5, 0.9)))); // first zebra
        waves.add(new Wave(List.of(new BloonSpawnInfo(15, 3, 0.4), new BloonSpawnInfo(5, 5, 0.8))));

        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 5, 0.6), new BloonSpawnInfo(10, 4, 0.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 9, 0.7), new BloonSpawnInfo(10, 4, 0.6)))); // first lead
        waves.add(new Wave(List.of(new BloonSpawnInfo(5, 6, 1.0)))); // first rainbow
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 6, 0.9), new BloonSpawnInfo(10, 5, 0.7))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 9, 0.6), new BloonSpawnInfo(10, 5, 0.4))));

        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 9, 1.0)))); // 10 leads
        waves.add(new Wave(List.of(new BloonSpawnInfo(12, 5, 0.8), new BloonSpawnInfo(5, 4, 0.6)))); // zebras + pinks
        waves.add(new Wave(List.of(new BloonSpawnInfo(8, 9, 1.2), new BloonSpawnInfo(6, 5, 0.9)))); // leads + zebras
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 6, 1.0)))); // rainbows only
        waves.add(new Wave(List.of(new BloonSpawnInfo(8, 6, 0.9), new BloonSpawnInfo(5, 5, 0.6), new BloonSpawnInfo(5, 9, 1.0)))); // rainbow, zebra, lead mix

        waves.add(new Wave(List.of(new BloonSpawnInfo(6, 7, 1.2)))); // ceramics intro
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 6, 0.8), new BloonSpawnInfo(3, 7, 1.4)))); // rainbow + ceramic
        waves.add(new Wave(List.of(new BloonSpawnInfo(12, 5, 0.7), new BloonSpawnInfo(6, 6, 1.0)))); // zebra + rainbow mix
        waves.add(new Wave(List.of(new BloonSpawnInfo(5, 7, 1.2), new BloonSpawnInfo(8, 9, 1.0)))); // ceramic + lead blend
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 6, 0.7), new BloonSpawnInfo(10, 5, 0.5), new BloonSpawnInfo(2, 7, 1.5)))); // heavier rainbow/zebra, light ceramic

        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 7, 1.0)))); // pure ceramics
        waves.add(new Wave(List.of(new BloonSpawnInfo(15, 6, 0.6), new BloonSpawnInfo(10, 5, 0.5)))); // rainbow/zebra swarm
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 7, 1.1), new BloonSpawnInfo(6, 6, 0.9)))); // ceramic + rainbow
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 9, 0.7), new BloonSpawnInfo(6, 7, 1.3)))); // lead + ceramic combo
        waves.add(new Wave(List.of(new BloonSpawnInfo(1, 8, 1)))); // 1 MOAB

        // Empty wave which is used to help end the game
        waves.add(new Wave(List.of()));
    }

    private void initializeNateWaves(){
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 0, 1.0),new BloonSpawnInfo(10,1, 1.6),new BloonSpawnInfo(7,2,2.3))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(50,2,.1))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(5, 3, 1.0),new BloonSpawnInfo(10,4, 1.6),new BloonSpawnInfo(7,5,2.3),new BloonSpawnInfo(6,5,1.5),new BloonSpawnInfo(3,6,4),new BloonSpawnInfo(4,7,3.2))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(1, 8, 1.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 9, .2))));

        //Empty wave indicating the end of the game
        waves.add(new Wave(List.of()));

    }

    /**
     * Returns the current wave being processed.
     *
     * @return the current {@link Wave}
     */
    public Wave getCurrentWave() {
        return waves.get(currentWaveIndex);
    }

    /**
     * Checks if there is a next wave to transition to.
     *
     * @return {@code true} if there is a next wave, otherwise {@code false}
     */
    public boolean hasNextWave() {
        return currentWaveIndex < waves.size() - 1;
    }

    /**
     * Moves to the next wave if one exists.
     * Does nothing if the current wave is the last one.
     */
    public void nextWave() {
        if (hasNextWave()) {
            currentWaveIndex++;
        }
    }

    /**
     * Returns the index of the current wave.
     *
     * @return the index of the current wave
     */
    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }
}
