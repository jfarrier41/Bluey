import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

abstract public class Tower {
    // Information the Monkey Needs to know

    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected JLabel rangeJLabel;


    protected int fireSpeed;
    protected int diameter;
    protected int projectileSpeed;
    protected int projectileDamage;

    protected boolean placeable;
    protected boolean isSelected = false;

    protected int xPosition;
    protected int yPosition;

    public Tower(JFrame TowerJframe, int fire_Speed, int diameter
            ,int projectile_Speed, int projectile_Damage) {


        parentWindow = TowerJframe.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15,15,15,15);
        this.fireSpeed = fire_Speed;
        this.diameter = diameter;
        this.projectileSpeed = projectile_Speed;
        this.projectileDamage = projectile_Damage;

        placeable = false;




    }

    public void startPlacement(BufferedImage currentTower){
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                xPosition = e.getX();
                yPosition = e.getY();
                if(isPlaceable(currentMap)){
                    setCursor(new Cursor(Cursor.CUSTOM_CURSOR))
                    updateDisplay(xPosition,yPosition,true);
                }else{
                    setCursor(new Cursor(Cursor.CUSTOM_CURSOR))
                    updateDisplay(xPosition,yPosition,false);
                }
            }
        });
    }

    // Helper method to determine valid pixel color
    // ChatGPT
    public boolean isGreen(Color color){
        return color.getGreen() > 150 && color.getRed() < 100 && color.getBlue() < 100;
    }
    //Method to determine if monkey can be set down
    public boolean isPlaceable(BufferdImage currentMap){


        return placeable;
    }

    public void updateDisplay(int xPosition, int yPosition, boolean placeable){
        rangeJLabel.setBounds(xPosition - diameter / 2, yPosition - diameter / 2, diameter, diameter);
        if(placeable){
            rangeJLabel.setOpaque(true);
            rangeJLabel.setBackground(new Color(128,128,128,128));
        }else{
            rangeJLabel.setOpaque(true);
            rangeJLabel.setBackground(new Color(128,0,0,128));
        }
        rangeJLabel.setVisible(true);
    }
    // Method to determine attack
    abstract int attack();

    public  Balloon Tracking(List<Balloon> balloons){
        return ballon;
    }

    public int getFireSpeed() {return fireSpeed;}
    public int getRange() {return range;}
    public int getProjectileSpeed() {return projectileSpeed;}
    public int getProjectileDamage() {return projectileDamage;}
    public Point getPosition() {return new Point (xPosition, yPosition);}

    public void setFireSpeed(int fireSpeed) {this.fireSpeed = fireSpeed;}
    public void setRange(int range) {this.range = range;}
    public void setProjectileSpeed(int projectileSpeed) {this.projectileSpeed = projectileSpeed;}
    public void setProjectileDamage(int projectileDamage) {this.projectileDamage = projectileDamage;}
    

    public void setPostion(int xPostion, int yPosition) {this.xPosition = xPostion; this.yPosition =  yPosition; }



}
