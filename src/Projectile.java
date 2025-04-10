import com.sun.source.tree.ReturnTree;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Projectile {
    public double currentX, currentY;  // Position of the projectile
    public final double startX, startY;
    public double lastX, lastY;
    public final double damageArea;
    public double angle;  // Angle the projectile is traveling in
    public int range;
    public int allowedHits;
    public boolean tracking;
    protected ProjectileImageSize type;
    private Set<Balloon> hitBalloons = new HashSet<>();



    public double speed;
    private BufferedImage image;
    private Balloon currentTarget;

    public Projectile(double x, double y, double damageArea, double speed, double angle,
                      int range, Balloon currentTarget, int allowedHits,
                      boolean tracking, BufferedImage projectileImage, ProjectileImageSize type) {
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
        this.allowedHits = allowedHits;
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
        if (hitBalloons.contains(balloon)) {
            return false;
        }
        int balloonX = balloon.getX();
        int balloonY = balloon.getY();

        double distance = Math.sqrt(Math.pow(currentX - balloonX, 2) + Math.pow(currentY - balloonY, 2));

        if (distance <= (damageArea + 10)) {
            hitBalloons.add(balloon);
            return true;// Use balloon's radius if it's a circle
        }
        return false;
    }
    public int getRemainingHits(){
        return allowedHits;
    }
    public void removeOneFromHitCount(){
        allowedHits--;
    }
    public Image getImage() {
        return image;
    }

    public void setType(ProjectileImageSize type) {
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