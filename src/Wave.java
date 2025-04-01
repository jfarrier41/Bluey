import java.util.List;

public class Wave {
    private final List<BloonSpawnInfo> bloons;

    public Wave(List<BloonSpawnInfo> bloons) {
        this.bloons = bloons;
    }

    public List<BloonSpawnInfo> getBloons() {
        return bloons;
    }
}

