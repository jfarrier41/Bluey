import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile {
    public double projX, projY;  // Position of the projectile
    public double angleRadians;  // Angle the projectile is traveling in
    public double speed;
    private Image image;
    private Balloon currentTarget;

    public Projectile(double x, double y, double speed, Balloon currentTarget, int num) {
        this.projX = x;
        this.projY = y;
        this.speed = speed;
        this.image = GameRunningGUI.getProjectileImage(num);
        this.currentTarget = currentTarget;
        //System.out.println("Projectile x" +projX + " Projectile y " + projY + " speed " +speed + " target " + currentTarget);
    }

    public void update() {
        double angle = projectileAngle(currentTarget);
        this.projX += Math.cos(angle) * speed;
        this.projY += Math.sin(angle) * speed;
        System.out.println(angle + " angle " + projY + " projy " + projX + " projx ");
    }

    public double projectileAngle(Balloon balloon){
        int balloonX = balloon.getX();
        System.out.println("balloonX pro " + balloonX);
        int balloonY = balloon.getY();
        System.out.println("balloonY pro " + balloonY);
        double projAngle = Math.atan2(balloonY - projY, balloonX - projX);
        return projAngle;
    }




    public Image getImage() {
        System.out.println(image.getHeight(null)+ " Projectile");
        return image;
    }



}

