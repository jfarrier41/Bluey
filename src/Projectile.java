import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile {
    public double projX, projY;  // Position of the projectile
    public final double startX, startY;
    public final double damageArea;
    public double angle;  // Angle the projectile is traveling in
    public int range;
    boolean piercing;
    boolean tracking;


    public double speed;
    private Image image;
    private Balloon currentTarget;

    public Projectile(double x, double y, double damageArea, double speed, double angle, int range, Balloon currentTarget, boolean peircing, boolean tracking, int imageIndex) {
        this.projX = x;
        this.projY = y;
        this.startX = x;
        this.startY = y;
        this.damageArea = damageArea;
        this.speed = speed;
        this.angle = angle;
        this.range = range;
        this.currentTarget = currentTarget;
        this.piercing = peircing;
        this.tracking = tracking;
        this.image = GameRunningGUI.getProjectileImage(imageIndex);
    }

    public void update() {
        if(!tracking){
            this.projX += Math.cos(angle) * speed;
            this.projY += Math.sin(angle) * speed;
        }else{
            double angle = projectileAngle(currentTarget);
            this.projX += Math.cos(angle) * speed;
            this.projY += Math.sin(angle) * speed;
        }

    }

    public double projectileAngle(Balloon balloon){
        int balloonX = balloon.getX();
        int balloonY = balloon.getY();
        double projAngle = Math.atan2(balloonY - projY, balloonX - projX);
        return projAngle;
    }


    public double getAngle() {
        return angle;
    }

    public boolean missed() {
        double dx = Math.abs(projX - startX);
        double dy = Math.abs(projY - startY);
        double dist = (dx * dx + dy * dy);
        System.out.println(range);
        double maxRange = (range*.5) * (range*.5);
        return dist > maxRange;
    }

    public boolean didHit(Balloon balloon) {
        double dx = balloon.getX() - projX;
        double dy = balloon.getY() - projY;
        int balloonX = balloon.getX();
        int balloonY = balloon.getY();
        double dist = (dx * dx + dy * dy);
        System.out.println("dist" +dist+ ",  " +damageArea);
        if(dist <= damageArea){
            System.out.println("Hitting" + "Balloon x: " + balloonX + " y: " + balloonY + " x: " + projX + " y: " + projY);
        }
        else{
            System.out.println("Missing" + "Balloon x: " + balloonX + " y: " + balloonY + " x: " + projX + " y: " + projY);
        }
        return dist <= damageArea;

    }

    public Image getImage() {
        return image;
    }



}