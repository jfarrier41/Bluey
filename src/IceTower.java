import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author Joseph Farrier
 * @author Jace Classen
 * Implements the Ice Tower that freezes or unfreezes balloons within range.
 */
public class IceTower extends Tower {

    /**
     * Constructs an IceTower with preset attributes and image.
     *
     * @param runGame    the main game window
     * @param currentMap the current map image
     */
    public IceTower(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap, "IceTower.png");
        this.setFireSpeed(2500);
        this.setRange(160);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.ICETOWER);
        this.setCost(205);
        towerType = "Ice";
    }

    /**
     * Freezes all balloons in range if not already frozen, or unfreezes if frozen.
     *
     * @param currentTarget the balloon being targeted (not directly used)
     * @param projectiles   the list of active projectiles (not used here)
     */
    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        animateAttack = true;
        for (Balloon target : targets) {
            if (target.getSpeed() == 0) {
                target.unfreeze();
            } else {
                target.freeze();
            }
        }
        setFireTimer();
    }
}
