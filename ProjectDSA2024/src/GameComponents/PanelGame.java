package GameComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import GameObj.Bullet;
import GameObj.Player;
import GameObj.Rocket;

public class PanelGame extends JComponent {
    //game fps
    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private Thread thread;
    private boolean start = true;
    private Key key;
    private int shotTime;

    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000/FPS;
    //game object
    private Player player;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    public void start(){
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        thread = new Thread(new Runnable(){
            @Override
            public void run(){
                while(start){
                    long startTime = System.nanoTime();
                    drawBackGround();
                    drawGame();
                    render();
                    long time = System.nanoTime() - startTime;
                    if(time<TARGET_TIME){
                        long sleep=(TARGET_TIME-time)/1000000;
                        sleep(sleep);
                    }
                }
            }
        });
        intitObjectGame();
        initKeyboard();
        initBullets();
        thread.start();
    }

    private void addRocket(){
        Random ran = new Random();
        int locationY = ran.nextInt(height-50) +25;
        Rocket rocket = new Rocket();
        rocket.changeLocation(0,locationY);
        rocket.changeAngle(0);
        rockets.add(rocket);
        int locationY2 = ran.nextInt(height-50) + 25;
        Rocket rocket2 = new Rocket();
        rocket2.changeLocation(width, locationY2);
        rocket2.changeAngle(180);
        rockets.add(rocket2);


    }
    private void checkBullets(Bullet bullet){
        for(int i=0; i<rockets.size();i++){
            Rocket rocket = rockets.get(i);
            if(rocket!=null){
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if(!area.isEmpty()){
                    rockets.remove(rocket);
                    bullets.remove(bullet);
                }
            }
        }
    }

    private void intitObjectGame(){
        player = new Player();
        player.changeLocation(150,150);
        rockets = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    addRocket();
                    sleep(3000);
                }
            }
        }).start();
    }
    private void initKeyboard(){
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    key.setKey_left(true);
                } else if(e.getKeyCode() == KeyEvent.VK_D){
                    key.setKey_right(true);
                } else if(e.getKeyCode() == KeyEvent.VK_J){
                    key.setKey_j(true);
                }else if(e.getKeyCode() == KeyEvent.VK_K){
                    key.setKey_k(true);
                } else if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    key.setKey_space(true);
                }
            }
            @Override
            public void keyReleased(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_A){
                    key.setKey_left(false);
                } else if(e.getKeyCode() == KeyEvent.VK_D){
                    key.setKey_right(false);
                } else if(e.getKeyCode() == KeyEvent.VK_J){
                    key.setKey_j(false);
                }else if(e.getKeyCode() == KeyEvent.VK_K){
                    key.setKey_k(false);
                }else if(e.getKeyCode() == KeyEvent.VK_SPACE){
                    key.setKey_space(false);
                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 10.0f; //tốc độ quay
                while(start){
                    float angle = player.getAngle();
                    if(key.isKey_left()){
                        angle-=s;
                    }
                    if(key.isKey_right()){
                        angle +=s;
                    }
                    if(key.isKey_j() || key.isKey_k()){
                        if(shotTime ==0){
                            if(key.isKey_j()){
                                bullets.add(0,new Bullet(player.getX(), player.getY(), player.getAngle(), 5,3f));
                            } else{
                                bullets.add(0,new Bullet(player.getX(), player.getY(), player.getAngle(), 20,3f));
                            }
                        }
                        shotTime++;
                        if(shotTime==15){
                            shotTime =0;
                        }
                    } else{
                        shotTime =0;
                    }
                    if(key.isKey_space()){
                        player.speedUp();
                    }else{
                        player.speedDown();
                    } //bỏ space thì trở về speedown
                    player.update();
                    player.changeAngle(angle);
                    for(int i=0; i<rockets.size();i++){
                        Rocket rocket=rockets.get(i);
                        if(rocket!=null){
                            rocket.update();
                            if(!rocket.check(width,height)){
                                rockets.remove(rocket);
//                                System.out.println("Remove...");
                            }
                        }
                    }
                    sleep(FPS);
                }
            }
        }).start();
    }

    private void initBullets(){
        bullets = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(start){
                    for(int i=0; i<bullets.size(); i++){
                        Bullet bullet = bullets.get(i);
                        if(bullet!= null){
                            bullet.update();
                            checkBullets(bullet);
                            if(!bullet.check(width, height)){
                                bullets.remove(bullet);
                            }
                        } else{
                            bullets.remove(bullet);
                        }
                    }
                    sleep(1);
                }
            }
        }).start();
    }
    private void drawBackGround(){
        g2.setColor(new Color(30,30,30));
        g2.fillRect(0,0,width,height);
    }
    private void drawGame(){
         player.draw(g2);
         for(int i=0; i<bullets.size(); i++){
             Bullet bullet = bullets.get(i);
             if(bullet != null){
                 bullet.draw(g2);
             }
         }
        for(int i=0; i<rockets.size();i++){
            Rocket rocket=rockets.get(i);
            if(rocket!=null){
                rocket.draw(g2);
            }
        }
    }
    private void render(){
        Graphics g =getGraphics();
        g.drawImage(image, 0,0,null);
        g.dispose();
    }
    private void sleep(long speed){
        try{
            Thread.sleep(speed);
        } catch(InterruptedException ex){
            System.err.println(ex);
        }
    }
}
