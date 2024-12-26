package GameObj;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Player extends HealthBar{
    private final double screenWidth;
    private final double screenHeight;


    public static final double PLAYER_SIZE = 64;
    private double x;
    private double y;
    private final float MAX_SPEED = 6f;
    private float speed = 0f;
    private float angle = 0f;
    private Area playerShap;
    private  Image image;
    private  Image image_speed;
    private boolean speedUp;
    private boolean alive = true;

    public Player(double screenWidth, double screenHeight) {
        super(new HealthPoints(150,150));
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        try {
            BufferedImage originalImage = ImageIO.read(getClass().getResourceAsStream("/GameImage/plane.png"));
            int newWidth = originalImage.getWidth() * 2;
            int newHeight = originalImage.getHeight() * 2;
            BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            scaledImage.getGraphics().drawImage(originalImage, 0, 0, newWidth, newHeight, null);

            BufferedImage originalImage1 = ImageIO.read(getClass().getResourceAsStream("/GameImage/plane_speed.png"));
            int newWidth1 = originalImage.getWidth() * 2;
            int newHeight1 = originalImage.getHeight() *2 ;
            BufferedImage scaledImage1 = new BufferedImage(newWidth1, newHeight1, BufferedImage.TYPE_INT_ARGB);
            scaledImage1.getGraphics().drawImage(originalImage1, 0, 0, newWidth1, newHeight1, null);


            this.image = scaledImage;
            this.image_speed = scaledImage1;

            Path2D p = new Path2D.Double();
            p.moveTo(PLAYER_SIZE * 0.5, 0); // Nose
            p.lineTo(PLAYER_SIZE * 0.8, PLAYER_SIZE * 0.3); // Right wing tip
            p.lineTo(PLAYER_SIZE * 0.6, PLAYER_SIZE * 0.5); // Right edge of body
            p.lineTo(PLAYER_SIZE * 0.6, PLAYER_SIZE * 0.8); // Tail bottom-right
            p.lineTo(PLAYER_SIZE * 0.4, PLAYER_SIZE * 0.8); // Tail bottom-left
            p.lineTo(PLAYER_SIZE * 0.4, PLAYER_SIZE * 0.5); // Left edge of body
            p.lineTo(PLAYER_SIZE * 0.2, PLAYER_SIZE * 0.3); // Left wing tip
            p.closePath(); // Back to Nose
            playerShap = new Area(p);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void changeLocation(double x,double y){
        this.x = x;
        this.y=y;
    }
    public void update(){
        x+= Math.cos(Math.toRadians(angle)) * speed;
        y+= Math.sin(Math.toRadians(angle)) * speed;
        // Check for and prevent movement beyond screen boundaries
        if (x < 0) {
            x = 0;
        } else if (x + PLAYER_SIZE > screenWidth) {
            x = screenWidth - PLAYER_SIZE;
        }

        if (y < 0) {
            y = 0;
        } else if (y + PLAYER_SIZE > screenHeight) {
            y = screenHeight - PLAYER_SIZE;
        }

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
        tran.rotate(Math.toRadians(angle-90),
                PLAYER_SIZE/2, PLAYER_SIZE/2);
        g2.drawImage(speedUp ? image_speed : image, tran, null);

        hpRender(g2,getShape(),y);
        Shape shp = getShape();
        g2.setColor(new Color(255, 0, 0, 100)); // Semi-transparent red
        g2.setTransform(oldTransform);
        g2.draw(shp);
        g2.draw(getShape().getBounds());
    }

    public Area getShape(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x,y);
        afx.rotate(Math.toRadians(angle-90),
                PLAYER_SIZE/2,PLAYER_SIZE/2);
        return new Area(afx.createTransformedShape(playerShap));
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
    public void speedUp(){
        speedUp = true;
        if(speed> MAX_SPEED){
            speed = MAX_SPEED;
        } else{
            speed += 3f;
        }
    }
    public void speedDown(){
        speedUp = false;
        if(speed <= 0){
            speed =0;
        } else{
            speed -= 0.15f;
        }
    }

    public boolean isAlive(){
        return alive;
    }
    public void setAlive(boolean alive){
        this.alive = alive;
    }
    public void reset(){
        alive=true;
        resetHealth();
        angle=0;
        speed=0;
    }

}




