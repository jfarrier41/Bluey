import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.PriorityQueue;

abstract public class Tower {
    // Information the Monkey Needs to know

    protected final Container parentWindow;
    protected final JLabel towerJLabel;
    protected BufferedImage currentMap;

    private PriorityQueue<Balloon> targets = new PriorityQueue<>(
            (b1,b2) -> Double.compare(b2.getLevel(), b1.getLevel())
    );

    protected int fireSpeed;
    protected int diameter;
    protected int projectileSpeed;
    protected int projectileDamage;

    protected boolean placeable;
    protected boolean isSelected = false;
    protected boolean placed = false;

    protected int xPosition;
    protected int yPosition;

    protected Image towerImage;

    public Tower(JFrame TowerJframe, int fire_Speed, int diameter
            , int projectile_Speed, int projectile_Damage, String image) {


        parentWindow = TowerJframe.getContentPane();
        towerJLabel = new JLabel();
        towerJLabel.setBounds(15, 15, 15, 15);
        this.fireSpeed = fire_Speed;
        this.diameter = diameter;
        this.projectileSpeed = projectile_Speed;
        this.projectileDamage = projectile_Damage;

        placeable = false;

        this.towerImage = new ImageIcon(image).getImage();
        towerJLabel.setIcon(new ImageIcon(towerImage));
        parentWindow.add(towerJLabel);

        if (towerImage != null && towerImage.getWidth(null) > 0) {
            System.out.println("towerImage working");
        } else {
            System.out.println("Failed to load image.");
        }
    }



    // Helper method to determine valid pixel color
    // ChatGPT
    public boolean isGreen(Color color) {
        return color.getGreen() >= 39 && color.getRed() < 110 && color.getBlue() < 100;
    }

    //Method to determine if monkey can be set down
    public boolean isPlaceable(int x, int y) {

        for(int i = -3; i<= 3; i++){
           for(int j = -3; j<=3; j++){
               try{
                   int pixelColor = currentMap.getRGB(x+i,y+j);
                   Color col = new Color(pixelColor);
                   System.out.println("Red" + col.getRed());
                   System.out.println("Blue" + col.getBlue());
                   System.out.println("Green" + col.getGreen());

                   if(!isGreen(col)){
                       placeable = false;
                       return false;
                   }
               }catch(ArrayIndexOutOfBoundsException e){
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



    public boolean inRange(Balloon ballon) {
        int xPos = ballon.getX();
        int yPos = ballon.getY();
        int distanceSquared = (xPos - this.xPosition) * (xPos - this.xPosition) +
                (yPos - this.yPosition) * (yPos - this.yPosition);

        int rangeSquared = (this.diameter / 2) * (this.diameter / 2);
        return distanceSquared <= rangeSquared;
    }

    public void addTarget(Balloon balloon){
        if(!targets.contains(balloon)){
            targets.add(balloon);
        }
    }
    public void removeTarget(Balloon balloon){
        if(balloon.getLevel()<1 || !inRange(balloon)){
            targets.remove(balloon);
        }
    }
    public Balloon target(){
        return targets.peek();
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


    public abstract void setPosition(int x, int y);



    public boolean getValid(){
        return placeable;
    }


    // testing for editing main class
    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }
}
