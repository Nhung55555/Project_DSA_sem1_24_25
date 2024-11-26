package GameObj;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {
//    public Player(){
//        this.image = new ImageIcon(getClass().getResource("/GameImage/plane.png")).getImage();
//        this.image_speed = new ImageIcon(getClass().getResource("/GameImage/plane_speed.png")).getImage();;
//    }
    public Player() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final double PLAYER_SIZE = 64;
    private double x;
    private double y;
    private final float MAX_SPPED = 1f;
    private float speed = 0f;
    private float angle = 0f;
    private  Image image;
    private  Image image_speed;
    private boolean speedUp;

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
        tran.rotate(Math.toRadians(angle-90), PLAYER_SIZE/2, PLAYER_SIZE/2);
        g2.drawImage(speedUp ? image_speed : image, tran, null);
        g2.setTransform(oldTransform);
//        AffineTransform tran = new AffineTransform();
//        tran.rotate(Math.toRadians(angle-90), PLAYER_SIZE/2, PLAYER_SIZE/2);
//        g2.drawImage(speedUp ? image_speed : image, tran, null);
//        g2.setTransform(oldTransform);
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
        if(speed>MAX_SPPED){
            speed = MAX_SPPED;
        } else{
            speed += 3f;
        }
    }
    public void speedDown(){
        speedUp = false;
        if(speed <= 0){
            speed =0;
        } else{
            speed -= 0.003f;
        }
    }

}




