import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile {
    public double currentX, currentY;  // Position of the projectile
    public final double startX, startY;
    public double lastX, lastY;
    public final double damageArea;
    public double angle;  // Angle the projectile is traveling in
    public int range;
    public boolean piercing;
    public boolean tracking;
    protected ProjectileType type;



    public double speed;
    private BufferedImage image;
    private Balloon currentTarget;

    public Projectile(double x, double y, double damageArea, double speed, double angle,
                      int range, Balloon currentTarget, boolean peircing,
                      boolean tracking, BufferedImage projectileImage, ProjectileType type) {
        this.currentX = x;
        this.currentY = y;
        this.startX = x;
        this.startY = y;
        this.lastX = x - 10;
        this.lastY = y - 10;
        this.damageArea = damageArea;
        this.speed = speed;
        this.angle = angle;
        this.range = range;
        this.currentTarget = currentTarget;
        this.piercing = peircing;
        this.tracking = tracking;
        this.image = projectileImage;
        this.type = type;
    }

    public void update() {
        this.lastX = currentX;
        this.lastY = currentY;
        if (tracking == false) {
            this.currentX += Math.cos(angle) * speed;
            this.currentY += Math.sin(angle) * speed;
        } else {
            double angle = projectileAngle(currentTarget);
            this.currentX += Math.cos(angle) * speed;
            this.currentY += Math.sin(angle) * speed;
        }

    }

    public double projectileAngle(Balloon balloon) {
        int balloonX = balloon.getX();
        int balloonY = balloon.getY();
        double projAngle = Math.atan2(balloonY - currentY, balloonX - currentX);
        return projAngle;
    }


    public double getAngle() {
        return angle;
    }

    public boolean missed() {
        double dx = Math.abs(currentX - startX);
        double dy = Math.abs(currentY - startY);
        double dist = (dx * dx + dy * dy);
        double maxRange = (range * .5) * (range * .5);
        return dist > maxRange;
    }

    public boolean didHit(Balloon balloon) {

        int balloonX = balloon.getX();
        int balloonY = balloon.getY();

        double distance = Math.sqrt(Math.pow(currentX - balloonX, 2) + Math.pow(currentY - balloonY, 2));

        return distance <= (damageArea + 10); // Use balloon's radius if it's a circle


    }

    public Image getImage() {
        return image;
    }

    public void setType(ProjectileType type) {
        this.type = type;
    }

    private void setWidthHeight() {
    }

    public int getWidth() {
        return type.getWidth();
    }

    public int getHeight() {
        return type.getHeight();
    }





}