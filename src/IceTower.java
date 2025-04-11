import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
Filename: DartMonkey.java
Authors: Jace Claassen and Joseph Farrier
Description: Implements tower and defines the Dart Monkey.
 */

public class IceTower extends Tower {
    // Constructor that only takes JFrame and BufferedImage

    public IceTower(JFrame runGame, BufferedImage currentMap) {
        super(runGame, currentMap,"IceTower.png");

        // Set default values for DartMonkey (can be overridden if needed)
        this.setFireSpeed(3000);
        this.setRange(50);
        this.setProjectileSpeed(10);
        this.setProjectileDamage(10);
        this.setTowerImageSize(TowerImageSize.ICETOWER);
    }

    @Override
    public boolean attack() {
        projectileActive = true;
        return true;
    }

    @Override
    public boolean isProjectileActive() {
        return false;
    }

    @Override
    public void setProjectileActive(boolean projectileActive) {

    }

    @Override
    public void fire(Balloon currentTarget, ArrayList<Projectile> projectiles) {
        if (isReadyToFire()) {

        }
    }
}
