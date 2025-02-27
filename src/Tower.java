import javax.swing.*;
import java.awt.*;

abstract public class Tower {
    // Information the Monkey Needs to know

    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected final JLabel projectileJLabel;

    protected int fire_Speed;
    protected int range;
    protected int projectile_Speed;
    protected int projectile_Damage;
    protected boolean placeable;
    protected boolean set;

    protected int xPosition;
    protected int yPosition;

    public Tower(JFrame TowerJframe, int fire_Speed, int range
            ,int projectile_Speed, int projectile_Damage) {


        parentWindow = TowerJframe.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15,15,15,15);
        projectileJLabel = new JLabel();
        projectileJLabel.setBounds(5,5,10,10);
        this.fire_Speed = fire_Speed;
        this.range = range;
        this.projectile_Speed = projectile_Speed;
        this.projectile_Damage = projectile_Damage;

        placeable = false;
        set = false;


    }
    // Method to determine if monkey can be set down
    public boolean isPlaceable(){return placeable;}
    // Method to determine attack

    abstract int attack();



    public int getFire_Speed() {return fire_Speed;}
    public int getRange() {return range;}
    public int getProjectile_Speed() {return projectile_Speed;}
    public int getProjectile_Damage() {return projectile_Damage;}
    public int getXPostion() {return xPosition;}
    public int getYPostion() {return yPosition;}

    public void setFire_Speed(int fire_Speed) {this.fire_Speed = fire_Speed;}
    public void setRange(int range) {this.range = range;}
    public void setProjectile_Speed(int projectile_Speed) {this.projectile_Speed = projectile_Speed;}
    public void setProjectile_Damage(int projectile_Damage) {this.projectile_Damage = projectile_Damage;}
    

    public void setXPostion(int xPostion) {this.xPosition = xPostion;}
    public void setYPostion(int yPostion) {this.yPosition = yPostion;}




}
