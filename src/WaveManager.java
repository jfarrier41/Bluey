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
                new BloonSpawnInfo(20, 0, 1)
        )));
        waves.add(new Wave(List.of(
                new BloonSpawnInfo(35, 0, .8)
        )));
        waves.add(new Wave(List.of(
                new BloonSpawnInfo(25, 0, 1),
                new BloonSpawnInfo(5, 1, 2.3)
        )));
        waves.add(new Wave(List.of(
                new BloonSpawnInfo(5, 4, .1)
        )));
        waves.add(new Wave(List.of(
                new BloonSpawnInfo(1, 7, 1)
        )));

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
