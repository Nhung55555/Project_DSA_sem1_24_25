package GameObj;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class RocketBoss extends HealthBar{
    public RocketBoss(String imageLocation) {
        super(new HealthPoints(100, 100));
        try {
            BufferedImage originalImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageLocation)));
            int newWidth = originalImage.getWidth() * 10;
            int newHeight = originalImage.getHeight() * 10;
            BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            scaledImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);

            this.image = scaledImage;
            Path2D p = new Path2D.Double();
            p.moveTo(1,9); // Adjust starting point to match top left corner
            p.lineTo(ROCKET_BOSS_SIZE -1 , 15); // Account for the tail section
            p.lineTo(ROCKET_BOSS_SIZE +10, ROCKET_BOSS_SIZE / 2); // Adjust endpoint slightly
            p.lineTo(ROCKET_BOSS_SIZE , ROCKET_BOSS_SIZE -10); // Account for the tail section
            p.lineTo(1, ROCKET_BOSS_SIZE-1); // Adjust endpoint to match bottom left corner
            rocketShap = new Area(p);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final double ROCKET_BOSS_SIZE = 50;
    public String imageLocation;
    private double x;
    private double y;
    //chỉnh tốc độ
    private final float speed = 1f;
    private float angle = 0;
    private Image image;
    private Area rocketShap;

    public void changeLocation(double x,double y){
        this.x = x;
        this.y=y;
    }
    public void update(){
        x+= Math.cos(Math.toRadians(angle)) * speed;
        y+= Math.sin(Math.toRadians(angle)) * speed;
    }
    public void changeAngle(float angle){
        if(angle<0){
            angle = 359;
        }else if(angle>359){
            angle =0;
        }
        this.angle = angle;
    }
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x,y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle), ROCKET_BOSS_SIZE/2, ROCKET_BOSS_SIZE/2);
        g2.drawImage(image,tran,null);
        Shape shap = getShape();
        hpRender(g2, shap, y);
        g2.setTransform(oldTransform);
        g2.setColor(Color.red);
//        g2.draw(shap);
        g2.draw(shap.getBounds2D());
    }

    public double getX(){
        return x;
    }
    public double getY(){
        return y;
    }
    public float getAngle(){
        return angle;
    }
    public Area getShape(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x,y);
        afx.rotate(Math.toRadians(angle), ROCKET_BOSS_SIZE/2,ROCKET_BOSS_SIZE/2);
        return new Area(afx.createTransformedShape(rocketShap));
    }

    public boolean check(int width,int height){
        Rectangle size = getShape().getBounds();
        return !(x <= -size.getWidth()) && !(y < -size.getHeight()) && !(x > width) && !(y > height);
    }
}

