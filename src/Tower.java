import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;

abstract public class Tower {
    // Information the Monkey Needs to know

    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected BufferedImage currentMap;


    protected int fireSpeed;
    protected int diameter;
    protected int projectileSpeed;
    protected int projectileDamage;

    protected boolean placeable;
    protected boolean isSelected = false;

    protected int xPosition;
    protected int yPosition;

    public Tower(JFrame TowerJframe, int fire_Speed, int diameter
            , int projectile_Speed, int projectile_Damage) {


        parentWindow = TowerJframe.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15, 15, 15, 15);
        this.fireSpeed = fire_Speed;
        this.diameter = diameter;
        this.projectileSpeed = projectile_Speed;
        this.projectileDamage = projectile_Damage;

        placeable = false;


    }



    // Helper method to determine valid pixel color
    // ChatGPT
    public boolean isGreen(Color color) {
        return color.getGreen() > 150 && color.getRed() < 100 && color.getBlue() < 100;
    }

    //Method to determine if monkey can be set down
    public boolean isPlaceable(int x, int y) {
        for(int i = -2; i<= 2; i++){
           for(int j = -2; j<=2; j++){
               int pixelColor = currentMap.getRGB(x+i,y+j);
               Color col = new Color(pixelColor);
               if(!isGreen(col)){
                   placeable = false;
                   return false;
               }
            }
        }
        placeable = true;
        return true;
    }


    // Method to determine attack
    abstract int attack();

    public Balloon Tracking(List<Balloon> balloons) {
        return ballon;
    }



    public int getFireSpeed() {
        return fireSpeed;
    }

    public int getDiameter(){
        return diameter;
    }

    public int getProjectileSpeed() {
        return projectileSpeed;
    }

    public int getProjectileDamage() {
        return projectileDamage;
    }

    public Point getPosition() {
        return new Point(xPosition, yPosition);
    }

    public void setFireSpeed(int fireSpeed) {
        this.fireSpeed = fireSpeed;
    }

    public void setRange(int diameter){
        this.diameter = diameter;
    }

    public void setProjectileSpeed(int projectileSpeed) {
        this.projectileSpeed = projectileSpeed;
    }

    public void setProjectileDamage(int projectileDamage) {
        this.projectileDamage = projectileDamage;
    }


    public void setPostion(int xPostion, int yPosition) {
        this.xPosition = xPostion;
        this.yPosition = yPosition;
    }

    public boolean getValid(){
        return placeable;
    }

}
