import javax.swing.*;
import java.awt.*;

public class DartMonkey extends Tower{

    private static final ImageIcon dartMonkey = new ImageIcon("DartMonkey.png");
    private static final ImageIcon UpgradedDartMonkey = new ImageIcon("UpgradedDartMonkey.png");


    public DartMonkey(JFrame TowerJframe, int fire_Speed, int diameter, int projectile_Speed, int projectile_Damage, String image) {
        super(TowerJframe, fire_Speed, diameter, projectile_Speed, projectile_Damage, image);
    }

    @Override
    int attack() {
        return 0;
    }

    @Override
    public void setPosition(int x, int y) {
        this.xPosition = x-24;
        this.yPosition = y-18;
        this.placed = true;
        this.isSelected = false;
        System.out.println("TowerPlaced at" +x + ", " + y );
    }
}
