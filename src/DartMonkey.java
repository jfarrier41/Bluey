import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DartMonkey extends Tower{

    private static final ImageIcon dartMonkey = new ImageIcon("DartMonkey.png");
    private static final ImageIcon UpgradedDartMonkey = new ImageIcon("UpgradedDartMonkey.png");


    public DartMonkey(JFrame runGame, int fire_Speed, int diameter, int projectile_Speed, int projectile_Damage, String image, BufferedImage currentMap) {
        super(runGame, fire_Speed, diameter, projectile_Speed, projectile_Damage, image, currentMap);
    }

    @Override
    int attack() {
        return 0;
    }

}
