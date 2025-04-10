import java.util.ArrayList;
import java.util.List;

public class WaveManager {
    private final List<Wave> waves;
    private int currentWaveIndex;

    public WaveManager() {
        this.waves = new ArrayList<>();
        this.currentWaveIndex = 0;
        initializeWaves();
    }

    private void initializeWaves() {
        waves.add(new Wave(List.of(
                new BloonSpawnInfo(10, 2, 1)
        )));

        waves.add(new Wave(List.of(
                new BloonSpawnInfo(10, 1, 0.2),
                new BloonSpawnInfo(15, 2, 0.3),
                new BloonSpawnInfo(10, 3, 0.35),
                new BloonSpawnInfo(15, 4, 0.41),
                new BloonSpawnInfo(10, 5, 0.5),
                new BloonSpawnInfo(15, 6, 0.4),
                new BloonSpawnInfo(10, 7, 0.23),
                new BloonSpawnInfo(1, 8, 6)
        )));

        // Add 20 waves...
    }

    public Wave getCurrentWave() {
        return waves.get(currentWaveIndex);
    }

    public boolean hasNextWave() {
        return currentWaveIndex < waves.size() - 1;
    }

    public void nextWave() {
        if (hasNextWave()) {
            currentWaveIndex++;
        }
    }

    public boolean isLastWave() {
        return currentWaveIndex == waves.size() - 1;
    }

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }
}
