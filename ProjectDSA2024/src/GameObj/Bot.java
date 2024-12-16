package GameObj;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Bot extends HealthBar{
    private final double screenWidth;
    private final double screenHeight;

    public Bot(double screenWidth, double screenHeight) {
        super(new HealthPoints(10,10));
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
            p.moveTo(BOT_SIZE * 0.5, 0); // Nose
            p.lineTo(BOT_SIZE * 0.8, BOT_SIZE * 0.3); // Right wing tip
            p.lineTo(BOT_SIZE * 0.6, BOT_SIZE * 0.5); // Right edge of body
            p.lineTo(BOT_SIZE * 0.6, BOT_SIZE * 0.8); // Tail bottom-right
            p.lineTo(BOT_SIZE * 0.4, BOT_SIZE * 0.8); // Tail bottom-left
            p.lineTo(BOT_SIZE * 0.4, BOT_SIZE * 0.5); // Left edge of body
            p.lineTo(BOT_SIZE * 0.2, BOT_SIZE * 0.3); // Left wing tip
            p.closePath(); // Back to Nose
            botShap = new Area(p);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static final double BOT_SIZE = 64;
    private double x;
    private double y;
    private final float MAX_SPEED = 9f;
    private float speed = 0f;
    private float angle = 0f;
    private Area botShap;
    private  Image image;
    private  Image image_speed;
    private boolean speedUp;
    private boolean alive = true;


    public void changeLocation(double x,double y){
        this.x = x;
        this.y = y;
    }
//
public void update() {

    y += speed;
    System.out.println("Y Position: " + y + ", Speed: " + speed);
    // Check for and adjust movement based on screen boundaries
    if (y <= 0) {
        y = 0;
//        speed = -speed; // Reverse the direction of movement
        speed = 0;
    } else if (y + BOT_SIZE >= screenHeight) {
        y = screenHeight - BOT_SIZE;
//        speed = -speed; // Reverse the direction of movement
        speed = 0;
    }
}
//public void moveUp() {
//    if (y > 0) { // Allow upward movement only if not at the top boundary
//        speed = -MAX_SPEED;
//        System.out.println(speed);
//    }
//    else {
//        speed = 0; // Stop movement if at the boundary
//    }
//}
//
//    public void moveDown() {
//        if (y + BOT_SIZE < screenHeight) { // Allow downward movement only if not at the bottom boundary
//            speed = MAX_SPEED;
//        }
//        else {
//            speed = 0; // Stop movement if at the boundary
//        }
//    }
        public void moveUp() {
        speedUp = false; // Not relevant here, but can be kept for consistency
        if (speed <= 0) {
            speed = -MAX_SPEED; // Negative speed for upwards movement
        }
    }

    public void moveDown() {
        speedUp = true; // Not relevant here, but can be kept for consistency
        if (speed >= 0) {
            speed = MAX_SPEED; // Positive speed for downwards movement
        }
    }
    public void stopMoving(){
        speed = 0;
    }
    public void draw(Graphics2D g2){
        AffineTransform oldTransform = g2.getTransform();
        g2.translate(x,y);
        AffineTransform tran = new AffineTransform();
        tran.rotate(Math.toRadians(angle-90), BOT_SIZE/2, BOT_SIZE/2);
        g2.drawImage(speedUp ? image_speed : image, tran, null);

        hpRender(g2,getShape(),y);
        Shape shp = getShape();
        g2.setColor(new Color(255, 0, 0, 150)); // Semi-transparent red
        g2.setTransform(oldTransform);
        g2.draw(shp);
        g2.draw(getShape().getBounds());
    }

    public Area getShape(){
        AffineTransform afx = new AffineTransform();
        afx.translate(x,y);
        afx.rotate(Math.toRadians(angle-90), BOT_SIZE/2,BOT_SIZE/2);
        return new Area(afx.createTransformedShape(botShap));
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
//    public void speedDown(){
//        speedUp = false;
//        if(speed <= 0){
//            speed =0;
//        } else{
//            speed -= 0.003f;
//        }
//    }
public void speedDown() {
    speedUp = false;
    if (speed <= 0) {
        speed = 0;
    } else {
        speed -= 0.5f; // Increased decrement to make slowing noticeable
        if (speed < 1f) {
            speed = 1f; // Maintain a minimum speed to avoid stalling
        }
    }
    System.out.println("Speed: " + speed + ", Y: " + y);

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





