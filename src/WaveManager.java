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
        // Waves 1–5: REDs to GREENs
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 0, 1.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 1, 0.9))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(40, 2, 0.8))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(50, 3, 0.7))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(40, 2, 0.5), new BloonSpawnInfo(10, 4, 0.8))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 4, 0.6), new BloonSpawnInfo(15, 3, 0.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 5, 0.6))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 5, 0.5), new BloonSpawnInfo(15, 2, 0.3))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(35, 6, 0.6))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 4, 0.4), new BloonSpawnInfo(10, 6, 0.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 5, 0.5), new BloonSpawnInfo(5, 9, 2.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 6, 0.5), new BloonSpawnInfo(10, 9, 1.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 6, 0.5), new BloonSpawnInfo(15, 4, 0.3))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 7, 0.6))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 7, 0.4), new BloonSpawnInfo(20, 5, 0.3))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 6, 0.5), new BloonSpawnInfo(10, 9, 1.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 7, 0.5), new BloonSpawnInfo(10, 9, 1.3))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 8, 1.8))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(15, 8, 1.2), new BloonSpawnInfo(10, 6, 0.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 7, 0.5), new BloonSpawnInfo(10, 8, 2.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 6, 0.3), new BloonSpawnInfo(15, 9, 1.2))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 8, 1.5), new BloonSpawnInfo(10, 7, 0.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(10, 8, 1.4), new BloonSpawnInfo(10, 9, 1.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 9, 1.8))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 7, 0.6))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 6, 0.5), new BloonSpawnInfo(10, 9, 1.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 8, 2.0), new BloonSpawnInfo(10, 9, 1.2))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 9, 1.6), new BloonSpawnInfo(5, 8, 2.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(25, 9, 1.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 9, 1.3))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(35, 7, 0.5), new BloonSpawnInfo(10, 8, 1.7))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 9, 1.1), new BloonSpawnInfo(10, 8, 2.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 9, 1.0))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(40, 9, 0.9))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(45, 9, 0.8))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(50, 9, 0.7))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(40, 8, 1.0), new BloonSpawnInfo(10, 9, 0.6))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(20, 9, 1.0), new BloonSpawnInfo(10, 8, 1.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(30, 9, 1.2), new BloonSpawnInfo(10, 7, 0.5))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(35, 9, 0.9), new BloonSpawnInfo(15, 6, 0.4))));
        waves.add(new Wave(List.of(new BloonSpawnInfo(40, 9, 0.7), new BloonSpawnInfo(10, 5, 0.3))));

        //Empty wave which is used to help end the game
        waves.add(new Wave(List.of(
        )));
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

    public int getCurrentWaveIndex() {
        return currentWaveIndex;
    }

}
