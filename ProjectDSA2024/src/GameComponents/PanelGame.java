//package GameComponents;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//import java.awt.geom.Area;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import GameObj.*;
//import GameObj.sound.Sound;
//
//public class PanelGame extends JPanel{
//    private int gameState = 0; // 0: Start Screen, 1: Playing, 2: Game Over
//
//    //game fps
//    private Graphics2D g2;
//    private BufferedImage image;
//    private int width;
//    private int height;
//    private Thread thread;
//    private boolean start = true;
//    private Key key;
//    private int shotTime;
//    private final int FPS = 60;
//    private final int TARGET_TIME = 1000000000 / FPS;
//    private Sound sound;
//    //game object
//    private Player player;
//    private Bot bot;
//    private List<Bullet> bullets;
//    private List<Rocket> rockets;
//    private List<Effects> boomEffects;
//    private int score = 0;
//
////    public PanelGame() {
////        // Set a non-zero preferred size for the panel
////        setPreferredSize(new Dimension(300, 200));
////
////        // ... (rest of your PanelGame logic)
////    }
//public PanelGame() {
//    setBackground(Color.GREEN); // Set a background color for distinction
//}
//    public void start() {
//        width = getWidth();
//        height = getHeight();
//        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//        g2 = image.createGraphics();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (start) {
//                    long startTime = System.nanoTime();
//                    drawBackGround();
//                    drawGame();
//                    render();
//                    long time = System.nanoTime() - startTime;
//                    if (time < TARGET_TIME) {
//                        long sleep = (TARGET_TIME - time) / 1000000;
//                        sleep(sleep);
//                    }
//                }
//            }
//        });
//
//        intitObjectGame();
//        initKeyboard();
//        initBullets();
//        thread.start();
//    }
//
//    private void addRocket() {
//        Random ran = new Random();
//
//        int locationY2 = ran.nextInt(height - 50) + 25;
//        Rocket rocket2 = new Rocket();
//        rocket2.changeLocation(width, locationY2);
//        rocket2.changeAngle(180);
//        rockets.add(rocket2);
//    }
//
//    private void checkBullets(Bullet bullet) {
//        for (int i = 0; i < rockets.size(); i++) {
//            Rocket rocket = rockets.get(i);
//            if (rocket != null) {
//                Area area = new Area(bullet.getShape());
//                area.intersect(rocket.getShape());
//                if (!area.isEmpty()) {
//                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60, 0.5f, new Color(230, 207, 105)));
//                    if (!rocket.updateHealth(bullet.getSize())) {
//                        score++;
//                        rockets.remove(rocket);
//                        sound.shoundDestroy();
//                        double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
//                        double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
//                        boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
//                        boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
//                        boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
//                        boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
//                        boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
//                    } else {
//                        sound.shoundHit();
//                    }
//                    bullets.remove(bullet);
//                }
//            }
//        }
//    }
//
//    private void checkPlayer(Rocket rocket) {
//        if (rocket != null) {
//            Area area = new Area(player.getShape());
//            area.intersect(rocket.getShape());
//            if (!area.isEmpty()) {
//                double rocketHp = rocket.getHealth();
//                if (!rocket.updateHealth(player.getHealth())) {
//                    rockets.remove(rocket);
//                    sound.shoundDestroy();
//
//                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
//                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
//                }
//                if (!player.updateHealth(rocketHp)) {
//                    player.setAlive(false);
//                    sound.shoundDestroy();
//
//                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
//                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
//                }
//
//            }
//        }
//
//    }
//
//    private void checkBot(Rocket rocket) {
//        if (rocket != null) {
//            Area area = new Area(bot.getShape());
//            area.intersect(rocket.getShape());
//            if (!area.isEmpty()) {
//                double rocketHp = rocket.getHealth();
//                if (!rocket.updateHealth(bot.getHealth())) {
//                    rockets.remove(rocket);
//                    sound.shoundDestroy();
//
//                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
//                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
//                }
//                if (!bot.updateHealth(rocketHp)) {
//                    bot.setAlive(false);
////                    bot.shoundDestroy();
//
//                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
//                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
//                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
//                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
//                }
//
//            }
//        }
//
//    }
//
//    private void intitObjectGame() {
//        sound = new Sound();
//
//        player = new Player(1366, 750);
//        player.changeLocation(150, 150);
//
//        bot = new Bot(1366, 750);
//        bot.changeLocation(150, 150);
//
//        rockets = new ArrayList<>();
//        boomEffects = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (start) {
//                    addRocket();
//                    sleep(900);
//                }
//            }
//        }).start();
//    }
//
//    private void resetGame() {
//        score = 0;
//        rockets.clear();
//        bullets.clear();
//        player.changeLocation(150, 150);
//        player.reset();
//        gameState = 1;
//    }
//    private void resetGameBot() {
//        score = 0;
//        rockets.clear();
//        bullets.clear();
//        bot.changeLocation(150, 150);
//        bot.reset();
//        gameState = 1;
//    }
//
//
//
//    private void initKeyboard() {
//        key = new Key();
//        requestFocus();
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (gameState == 0){
//                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
//                        gameState = 1; // Start the game
//                    }else if(e.getKeyCode() == KeyEvent.VK_N){
//                        gameState = 2;
//                    }
//                } else if (!player.isAlive() && e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    resetGame(); // Restart the game
//                } else if( !bot.isAlive() && e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    resetGameBot();
//                }
//
//                if (gameState == 1 || gameState ==2) {
//                    if (e.getKeyCode() == KeyEvent.VK_A) {
//                        key.setKey_left(true);
//                    } else if (e.getKeyCode() == KeyEvent.VK_D) {
//                        key.setKey_right(true);
//                    } else if (e.getKeyCode() == KeyEvent.VK_J) {
//                        key.setKey_j(true);
//                    } else if (e.getKeyCode() == KeyEvent.VK_K) {
//                        key.setKey_k(true);
//                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                        key.setKey_space(true);
//                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                        key.setKey_enter(true);
//                    }else if (e.getKeyCode() == KeyEvent.VK_N) {
//                        key.setKey_enter(true);
//                    }
//
//                }
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                if (gameState == 1 || gameState ==2) {
//                    if (e.getKeyCode() == KeyEvent.VK_A) {
//                        key.setKey_left(false);
//                    } else if (e.getKeyCode() == KeyEvent.VK_D) {
//                        key.setKey_right(false);
//                    } else if (e.getKeyCode() == KeyEvent.VK_J) {
//                        key.setKey_j(false);
//                    } else if (e.getKeyCode() == KeyEvent.VK_K) {
//                        key.setKey_k(false);
//                    } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//                        key.setKey_space(false);
//                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                        key.setKey_enter(false);
//                    }else if (e.getKeyCode() == KeyEvent.VK_N) {
//                        key.setKey_enter(false);
//                    }
//                }
//            }
//        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                float s = 10.0f; //tốc độ quay
//                Random random = new Random(); // Random number generator
//
//                while (start) {
//
//                        if(gameState == 2 && bot.isAlive()) {
//
//                            if (random.nextBoolean()) {
//                                bot.moveUp();
//                            } else {
//                                bot.moveDown();
//                            }
//                            bot.update();
//                            if (random.nextInt(100) < 90) { // 10% chance to shoot
//                                bullets.add(0, new Bullet(bot.getX(), bot.getY(), bot.getAngle(), 5, 3f));
//                                sound.shoundShoot();
//                            }
//                            // Randomly decide whether to speed up or down
//                            if (random.nextInt(100) < 50) { // 50% chance to speed up
//                                bot.speedUp();
//                            } else {
//                                bot.speedDown();
//                            }
//                        }
//                        else {
//                            if (key.isKey_enter()) {
//                                resetGameBot();
//                            }
//                        }
//
//                    if (gameState==1 && player.isAlive()) {
//                        float angle = player.getAngle();
//
//                        if (key.isKey_left()) {
//                            angle -= s;
//                        }
//                        if (key.isKey_right()) {
//                            angle += s;
//                        }
//                        if (key.isKey_j() || key.isKey_k()) {
//                            if (shotTime == 0) {
//                                if (key.isKey_j()) {
//                                    bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 5, 3f));
//                                } else {
//                                    bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 20, 3f));
//                                }
//                                sound.shoundShoot();
//                            }
//                            shotTime++;
//                            if (shotTime == 15) {
//                                shotTime = 0;
//                            }
//                        } else {
//                            shotTime = 0;
//                        }
//                        if (key.isKey_space()) {
//                            player.speedUp();
//                        } else {
//                            player.speedDown();
//                        } //bỏ space thì trở về speedown
//                        player.update();
//                        player.changeAngle(angle);
//                    } else {
//                        if (key.isKey_enter()) {
//                            resetGame();
//                        }
//                    }
//
//                    for (int i = 0; i < rockets.size(); i++) {
//                        Rocket rocket = rockets.get(i);
//                        if (rocket != null) {
//                            rocket.update();
//                            if (!rocket.check(width, height)) {
//                                rockets.remove(rocket);
//                            } else {
//                                if (player.isAlive()) {
//                                    checkPlayer(rocket);
//                                } else if (bot.isAlive()) {
//                                    checkBot(rocket);
//                                }
//                            }
//
//                        }
//                    }
//                    sleep(FPS);
//                }
//            }
//        }).start();
//    }
//
//    private void initBullets() {
//        bullets = new ArrayList<>();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (start) {
//                    for (int i = 0; i < bullets.size(); i++) {
//                        Bullet bullet = bullets.get(i);
//                        if (bullet != null) {
//                            bullet.update();
//                            checkBullets(bullet);
//                            if (!bullet.check(width, height)) {
//                                bullets.remove(bullet);
//                            }
//                        } else {
//                            bullets.remove(bullet);
//                        }
//                    }
//                    for (int i = 0; i < boomEffects.size(); i++) {
//                        Effects boomEffect = boomEffects.get(i);
//                        if (boomEffect != null) {
//                            boomEffect.update();
//                            if (!boomEffect.check()) {
//                                boomEffects.remove(boomEffect);
//                            }
//                        } else {
//                            boomEffects.remove(boomEffect);
//                        }
//                    }
//                    sleep(1);
//                }
//            }
//        }).start();
//    }
//
//    private void drawBackGround() {
//        g2.setColor(new Color(30, 30, 30));
//        g2.fillRect(0, 0, width, height);
//    }
//
//    private void drawGame() {
//
//        if (gameState == 0) {
//            // Start Screen
//            g2.setColor(Color.WHITE);
//            g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
//            String text = "PRESS ENTER OR N TO START";
//            FontMetrics fm = g2.getFontMetrics();
//            Rectangle2D r2 = fm.getStringBounds(text, g2);
//            double x = (width - r2.getWidth()) / 2;
//            double y = (height - r2.getHeight()) / 2;
//            g2.drawString(text, (int) x, (int) y + fm.getAscent());
//
//        } else if (gameState == 1) {
//            // Game Playing
//            if (player.isAlive()) {
//                player.draw(g2);
//            }
//
//            for (int i = 0; i < bullets.size(); i++) {
//                Bullet bullet = bullets.get(i);
//                if (bullet != null) {
//                    bullet.draw(g2);
//                }
//            }
//
//            for (int i = 0; i < rockets.size(); i++) {
//                Rocket rocket = rockets.get(i);
//                if (rocket != null) {
//                    rocket.draw(g2);
//                }
//            }
//
//            for (int i = 0; i < boomEffects.size(); i++) {
//                Effects boomEffect = boomEffects.get(i);
//                if (boomEffect != null) {
//                    boomEffect.draw(g2);
//                }
//            }
//
//            g2.setColor(Color.WHITE);
//            g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
//            g2.drawString("Score : " + score, 10, 20);
//
//            if (!player.isAlive()) {
//                String text = "GAME OVER";
//                String textKey = "Press key enter to Continue ...";
//                g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
//                FontMetrics fm = g2.getFontMetrics();
//                Rectangle2D r2 = fm.getStringBounds(text, g2);
//                double textWidth = r2.getWidth();
//                double textHeight = r2.getHeight();
//                double x = (width - textWidth) / 2;
//                double y = (height - textHeight) / 2;
//                g2.drawString(text, (int) x, (int) y + fm.getAscent());
//                g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
//                fm = g2.getFontMetrics();
//                r2 = fm.getStringBounds(textKey, g2);
//                textWidth = r2.getWidth();
//                textHeight = r2.getHeight();
//                x = (width - textWidth) / 2;
//                y = (height - textHeight) / 2;
//                g2.drawString(textKey, (int) x, (int) y + fm.getAscent() + 50);
//            }
//        } else if (gameState == 2) {
//            // Game Playing
//            if (bot.isAlive()) {
//                bot.draw(g2);
//            }
//
//            for (int i = 0; i < bullets.size(); i++) {
//                Bullet bullet = bullets.get(i);
//                if (bullet != null) {
//                    bullet.draw(g2);
//                }
//            }
//
//            for (int i = 0; i < rockets.size(); i++) {
//                Rocket rocket = rockets.get(i);
//                if (rocket != null) {
//                    rocket.draw(g2);
//                }
//            }
//
//            for (int i = 0; i < boomEffects.size(); i++) {
//                Effects boomEffect = boomEffects.get(i);
//                if (boomEffect != null) {
//                    boomEffect.draw(g2);
//                }
//            }
//
//            g2.setColor(Color.WHITE);
//            g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
//            g2.drawString("Score : " + score, 10, 20);
//
//            if (!bot.isAlive()) {
//                String text = "GAME OVER";
//                String textKey = "Press key enter to Continue ...";
//                g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
//                FontMetrics fm = g2.getFontMetrics();
//                Rectangle2D r2 = fm.getStringBounds(text, g2);
//                double textWidth = r2.getWidth();
//                double textHeight = r2.getHeight();
//                double x = (width - textWidth) / 2;
//                double y = (height - textHeight) / 2;
//                g2.drawString(text, (int) x, (int) y + fm.getAscent());
//                g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
//                fm = g2.getFontMetrics();
//                r2 = fm.getStringBounds(textKey, g2);
//                textWidth = r2.getWidth();
//                textHeight = r2.getHeight();
//                x = (width - textWidth) / 2;
//                y = (height - textHeight) / 2;
//                g2.drawString(textKey, (int) x, (int) y + fm.getAscent() + 50);
//            }
//        }
//    }
//
//
//
//
//    private void render(){
//        Graphics g =getGraphics();
//        g.drawImage(image, 0,0,null);
//        g.dispose();
//    }
//    private void sleep(long speed){
//        try{
//            Thread.sleep(speed);
//        } catch(InterruptedException ex){
//            System.err.println(ex);
//        }
//    }
//}


package GameComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import GameObj.*;
import GameObj.sound.Sound;

import static java.lang.Thread.sleep;

public class PanelGame extends JPanel {
//    private int gameState = 0; // 0: Start Screen, 1: Playing, 2: Game Over

    //game fps
    private Graphics2D g2;
    private BufferedImage image;
    private int width;
    private int height;
    private boolean start = true;
    private Key key;
    private int shotTime;
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    private Sound sound;
    //game object
    private Player player;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private List<RocketBoss> bossrockets;
    private List<Effects> boomEffects;
    private List<RocketBullets> rocketBullets;
    private int phase = 1;
    private int score = 0;


    private Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (thread.isInterrupted()){
                System.out.println("Game Thread is interrupted");
            }
            while (start) {
                long startTime = System.nanoTime();
                drawBackGround();
                drawGame();
                render();
                long time = System.nanoTime() - startTime;
                if (time < TARGET_TIME) {
                    long sleep = (TARGET_TIME - time) / 1000000;
                    sleep(sleep);
                }
            }
        }
    });

    private Thread keyboardInitilization = new Thread(new Runnable() {
        @Override
        public void run() {
            float s = 10.0f; //tốc độ quay
//                Random random = new Random(); // Random number generator
            if (keyboardInitilization.isInterrupted()){
                System.out.println("Keyboard Thread is interrupted");
            }
            while (start) {

                if (player.isAlive()) {
                    float angle = player.getAngle();

                    if (key.isKey_left()) {
                        angle -= s;
                    }
                    if (key.isKey_right()) {
                        angle += s;
                    }
                    if (key.isKey_j() || key.isKey_k()) {
                        if (shotTime == 0) {
                            if (key.isKey_j()) {
                                bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 5, 3f));
                            } else {
                                bullets.add(0, new Bullet(player.getX(), player.getY(), player.getAngle(), 20, 3f));
                            }
                            sound.shoundShoot();
                        }
                        shotTime++;
                        if (shotTime == 15) {
                            shotTime = 0;
                        }
                    } else {
                        shotTime = 0;
                    }
                    if (key.isKey_space()) {
                        player.speedUp();
                    } else {
                        player.speedDown();
                    } //bỏ space thì trở về speedown
                    player.update();
                    player.changeAngle(angle);
                    if(phase == 2 && key.isKey_enter()){
                        System.exit(0);
                    }
                } else {
                    if (!player.isAlive() && key.isKey_enter()) {
                        System.exit(0);
                    }
                }
                for (int i = 0; i < rockets.size(); i++) {
                    Rocket rocket = rockets.get(i);
                    if (rocket != null) {
                        rocket.update();
                        if (!rocket.check(width, height)) {
                            rockets.remove(rocket);
                        } else {
                            if (player.isAlive()) {
                                checkPlayer(rocket);
                            }
                        }
                    }
                }
                for (int i = 0; i < bossrockets.size(); i++) {
                    RocketBoss rocketBoss = bossrockets.get(i);
                    if (rocketBoss != null) {
                        if(rocketBoss.getX() <= (double) width/2){

                        }
                        rocketBoss.update();
                        if (!rocketBoss.check(width, height)) {
                            bossrockets.remove(rocketBoss);
                        } else {
                            if (player.isAlive()) {
                                checkPlayer(rocketBoss);
                            }
                        }
                    }
                }
                sleep(FPS);
            }
        }
    });


     private Thread enemySpawningthread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (enemySpawningthread.isInterrupted()){
                System.out.println("Enemy Thread is interrupted");
            }
            while (player.isAlive()) {
                    switch (phase) {
                        case 1 -> {
                            sleep(1200);
                            System.out.println("Phase 1");
                            for (int i = 0; i < 7; i++) {
                                addRocketPhase1(i * 100 + 60);
                            }
                            sleep(1500);
                            while(!rockets.isEmpty()){
                                sleep(100);
                            }
                            sleep(1100);
                            System.out.println("Boss Appeared!");
                            addBossRocketPhase(300);
                            for(int i = 0; i < 50; i ++){
                                addBulletsPhase1(300, i +14);
                            }
                            sleep(900);
                            addBulletsTracking(300);
                            addBulletsTracking(315);
                            addBulletsTracking(285);
                            for(int i = 0; i < 50; i ++){
                                addBulletsPhase1(300, i +25);
                            }
                            sleep(300);
                            for(int i = 0; i < 70; i ++){
                                addBulletsPhase1(300, i +20);
                            }
                            sleep(300);
                            addBulletsTracking(300);
                            addBulletsTracking(315);
                            addBulletsTracking(285);
                            for(int i = 0; i < 30; i ++){
                                addBulletsPhase1(300, i +17);
                            }
                            sleep(400);
                            addBulletsTracking(300);
                            addBulletsTracking(315);
                            addBulletsTracking(285);
                            for(int i = 0; i <= 10; i ++){
                                addBulletsPhase1(300, i +35);
                            }
                            sleep(900);
                            addBulletsTracking(300);
                            addBulletsTracking(315);
                            addBulletsTracking(285);
                            for(int i = 0; i < 70; i ++){
                                addBulletsPhase1(300, i +67);
                            }
                            sleep(800);
                            for(int i = 0; i < 70; i ++){
                                addBulletsPhase1(300, i +45);
                            }
                            sleep(700);
                            for(int i = 0; i < 80; i ++){
                                addBulletsPhase1(300, i +19);
                            }
                            sleep(700);
                            for(int i = 0; i < 50; i ++){
                                addBulletsPhase1(300, i +56);
                            }
                            sleep(700);
                            for(int i = 0; i < 100; i ++){
                                addBulletsPhase1(300, i +70);
                            }
                            System.out.println("Finídhed");
                            while(!bossrockets.isEmpty()){
                                sleep(10);
                            }
                            phase ++;
                            sleep(21000);
//                            addRocketPhase1(167);
//                            addRocketPhase1(501);
//                            sleep(1200);
//                            for (int i = 0; i < 4; i++) {
//                                addRocketPhase1(i * 175 + 85);
//                            }
//                            sleep(1200);
//                            for (int i = 0; i < 12; i++) {
//                                addRocketPhase1(i * 63);
//                            }
//                            sleep(1200);
//                            for (int i = 0; i < 5; i++) {
//                                addRocketPhase1(i * 160);
//                            }
//                            sleep(1200);
//                            for (int i = 0; i < 10; i++) {
//                                addRocketPhase1(i * 73 + 25);
//                            }
//                            sleep(1000);
                        }
                        case 2 -> {
                            System.out.println("Phase 2");
                            for (int i = 0; i < 5; i++) {
                                addRocketPhase1(i * 50);
                            }
                            sleep(13000);
                            phase++;
                        }
                        default -> {
                            addRocket();
                            sleep(1900);
                        }
                    }
            }
        }
    });

     Thread bulletSpawning = new Thread(new Runnable() {
        @Override
        public void run() {
            while (start) {
                for (int i = 0; i < bullets.size(); i++) {
                    Bullet bullet = bullets.get(i);
                    if (bullet != null) {
                        bullet.update();
                        checkBullets(bullet);
                        checkBulletsBoss(bullet);
                        if (!bullet.check(width, height)) {
                            bullets.remove(bullet);
                        }
                    } else {
                        bullets.remove(bullet);
                    }
                }
                for (int i = 0; i < rocketBullets.size(); i++) {
                    RocketBullets rocketbullet = rocketBullets.get(i);
                    if (rocketbullet != null) {
                        rocketbullet.update();
                        checkBulletRocket(rocketbullet);
                        if (!rocketbullet.check(width, height)) {
                            rocketBullets.remove(rocketbullet);
                        }
                    } else {
                        rocketBullets.remove(rocketbullet);
                    }
                }
                for (int i = 0; i < boomEffects.size(); i++) {
                    Effects boomEffect = boomEffects.get(i);
                    if (boomEffect != null) {
                        boomEffect.update();
                        if (!boomEffect.check()) {
                            boomEffects.remove(boomEffect);
                        }
                    } else {
                        boomEffects.remove(boomEffect);
                    }
                }
                sleep(1);
            }
        }
    });

    public PanelGame() {
        setBackground(Color.GREEN); // Set a background color for distinction
    }

    public void start() {
        width = getWidth();
        height = getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2 = image.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        intitObjectGame();
        initKeyboard();
        initBullets();
        thread.start();
    }

    private void addRocket() {
        Random ran = new Random();
        int locationY2 = ran.nextInt(height - 50) + 25;
        Rocket rocket2 = new Rocket();
        rocket2.changeLocation(width, locationY2);
        rocket2.changeAngle(180);
        rockets.add(rocket2);
    }
    private void addRocketPhase1(int locationIndexer){
        Rocket rocket = new Rocket();
        double shotDistanceAngle =  Math.abs(player.getY()-locationIndexer) / Math.abs(player.getX() - width);
        float angle = (float) Math.toDegrees(Math.atan(shotDistanceAngle));
        if(locationIndexer >= 350){
            angle += 180;
        }else {
            angle = 180 - angle;
        }
        RocketBullets rocketbullet = new RocketBullets(width-20 , locationIndexer, angle, 25, 0.5f);
        rocket.changeLocation(width, locationIndexer);
        rocket.changeAngle(180);
//        System.out.println(rocketbullet.getX());
//        System.out.println(rocketbullet.getY());
        rockets.add(rocket);
        rocketBullets.add(rocketbullet);
    }
    private void addBulletsPhase1(int locationIndexer, int angleIndexer){
        RocketBullets rocketbullet = new RocketBullets(width-60 , locationIndexer, 25 * angleIndexer , 30, 0.5f);
        rocketBullets.add(rocketbullet);
    }
    private void addBulletsTracking(int locationIndexer){
        double shotDistanceAngle =  Math.abs(player.getY()-locationIndexer) / Math.abs(player.getX() - width);
        float angle = (float) Math.toDegrees(Math.atan(shotDistanceAngle));
        if(locationIndexer >= 350){
            angle += 180;
        }else {
            angle = 180 - angle;
        }
        RocketBullets rocketbullet = new RocketBullets(width-60 , locationIndexer, angle, 30, 0.5f);
        rocketBullets.add(rocketbullet);
    }
    private void addBossRocketPhase(int locationIndexer){
        int locationY = locationIndexer;
        RocketBoss rocketBoss = new RocketBoss("/GameImage/boss_rockets.png");
        rocketBoss.changeLocation(width, locationY);
        rocketBoss.changeAngle(180);
        bossrockets.add(rocketBoss);
    }

    private void checkBullets(Bullet bullet) {
        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60, 0.5f, new Color(230, 207, 105)));
                    if (!rocket.updateHealth(bullet.getSize())) {
                        score++;
                        rockets.remove(rocket);
                        sound.shoundDestroy();
                        double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                        double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                        boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                        boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                        boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                        boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                        boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                    } else {
                        sound.shoundHit();
                    }
                    bullets.remove(bullet);
                }
            }
        }
    }
    private void checkBulletsBoss(Bullet bullet) {
        for (int i = 0; i < bossrockets.size(); i++) {
            RocketBoss rocket = bossrockets.get(i);
            if (rocket != null) {
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 320, 0.5f, new Color(255, 218, 52)));
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 310, 0.15f, new Color(222, 89, 6)));
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 220, 0.25f, new Color(255, 42, 4)));
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 230, 0.7f, new Color(255, 245, 186)));
                    if (!rocket.updateHealth(bullet.getSize())) {
                        score += 100;
                        bossrockets.remove(rocket);
                        sound.shoundDestroy();
                        double x = rocket.getX() + RocketBoss.ROCKET_BOSS_SIZE / 2;
                        double y = rocket.getY() + RocketBoss.ROCKET_BOSS_SIZE / 2;
                        boomEffects.add(new Effects(x, y, 5, 15, 175, 0.25f, new Color(255, 38, 0)));
                        boomEffects.add(new Effects(x, y, 5, 25, 175, 0.1f, new Color(255, 144, 144)));
                        boomEffects.add(new Effects(x, y, 10, 12, 200, 0.3f, new Color(246, 227, 9)));
                        boomEffects.add(new Effects(x, y, 10, 40, 200, 0.5f, new Color(168, 229, 111)));
                        boomEffects.add(new Effects(x, y, 10, 25, 250, 0.2f, new Color(248, 196, 72)));
                    } else {
                        sound.shoundHit();
                    }
                    bullets.remove(bullet);
                }
            }
        }
    }


    private void checkBulletRocket(RocketBullets bullet){
        if (player != null) {
            Area area = new Area(bullet.getShape());
            area.intersect(player.getShape());
            if (!area.isEmpty()) {
                boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60, 0.5f, new Color(239, 23, 23)));
                if (!player.updateHealth(bullet.getSize())) {
                    player.setAlive(false);
                    sound.shoundDestroy();
                    double x = player.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = player.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 9, 144)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(175, 78, 255)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 163)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(222, 239, 148)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(218, 122, 26)));
                } else {
                    sound.shoundHit();
                }
                rocketBullets.remove(bullet);
            }
        }
    }
    

    private void checkPlayer(Rocket rocket) {
        if (rocket != null) {
            Area area = new Area(player.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHealth();
                if (!rocket.updateHealth(player.getHealth())) {
                    rockets.remove(rocket);
                    sound.shoundDestroy();
                    score++;
                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }
                if (!player.updateHealth(rocketHp)) {
                    player.setAlive(false);
                    sound.shoundDestroy();

                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }

            }
        }

    }
    private void checkPlayer(RocketBoss rocket) {
        if (rocket != null) {
            Area area = new Area(player.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHealth();
                if (!rocket.updateHealth(player.getHealth())) {
                    bossrockets.remove(rocket);
                    sound.shoundDestroy();

                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }
                if (!player.updateHealth(rocketHp)) {
                    player.setAlive(false);
                    sound.shoundDestroy();

                    double x = rocket.getX() + Rocket.ROCKET_SIZE / 2;
                    double y = rocket.getY() + Rocket.ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }

            }
        }

    }


    private void intitObjectGame() {
        sound = new Sound();
        player = new Player(1366, 750);
        player.changeLocation(150, 350);
        rockets = new ArrayList<>();
        bullets = new ArrayList<>();
        rocketBullets = new ArrayList<>();
        bossrockets = new ArrayList<>();
        boomEffects = new ArrayList<>();
        enemySpawningthread.start();
    }

    private void resetGame() {
        score = 0;
        phase = 1;
        rockets.clear();
        bullets.clear();
        rocketBullets.clear();
        player.reset();
//        gameState = 1;
    }

    private void initKeyboard() {
        key = new Key();
        requestFocus();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(true);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(true);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(true);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(true);
                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    key.setKey_enter(true);
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    key.setKey_ESC(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    key.setKey_left(false);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    key.setKey_right(false);
                } else if (e.getKeyCode() == KeyEvent.VK_J) {
                    key.setKey_j(false);
                } else if (e.getKeyCode() == KeyEvent.VK_K) {
                    key.setKey_k(false);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    key.setKey_space(false);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    key.setKey_enter(false);
                } else if (e.getKeyCode() == KeyEvent.VK_N) {
                    key.setKey_enter(false);
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    key.setKey_ESC(false);
                }
//                }
            }
        });
        //the thread is cut here
        keyboardInitilization.start();
    }

    private void initBullets() {
        bullets = new ArrayList<>();
        rocketBullets = new ArrayList<>();
        bulletSpawning.start();
    }

    private Image loadImage(String path) {
        try {
            URL resource = getClass().getResource(path); // Load image as resource
            if (resource != null) {
                return new ImageIcon(resource).getImage();
            } else {
                System.err.println("Image resource not found: " + path);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            return null;
        }
    }
    private void drawBackGround() {
        Image backgroundImage = loadImage("/GameImage/game_background.png");
        if (backgroundImage == null) {
            System.err.println("Failed to load background image: " + "");
        }
        if(backgroundImage != null){
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void drawGame() {
        // Game Playing
        if (player.isAlive()) {
            player.draw(g2);
        }
        for (int i = 0; i < bossrockets.size(); i++) {
            RocketBoss bossRocket = bossrockets.get(i);
            if (bossRocket != null) {
                bossRocket.draw(g2);
            }
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2);
            }
        }
        for (int i = 0; i < rocketBullets.size(); i++) {
            RocketBullets rbullet = rocketBullets.get(i);
            if (rbullet != null) {
                rbullet.draw(g2);
            }
        }

        for (int i = 0; i < rockets.size(); i++) {
            Rocket rocket = rockets.get(i);
            if (rocket != null) {
                rocket.draw(g2);
            }
        }

        for (int i = 0; i < boomEffects.size(); i++) {
            Effects boomEffect = boomEffects.get(i);
            if (boomEffect != null) {
                boomEffect.draw(g2);
            }
       }
        g2.setColor(Color.RED);
        g2.setFont(getFont().deriveFont(Font.BOLD, 35f));
        g2.drawString("Score : " + score, 15, 40);
        if (!player.isAlive()) {
            String text = "GAME OVER";
            String textKey = "..Press Enter to Quit..";
            g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(text, g2);
            double textWidth = r2.getWidth();
            double textHeight = r2.getHeight();
            double x = (width - textWidth) / 2;
            double y = (height - textHeight) / 2;
            g2.drawString(text, (int) x, (int) y + fm.getAscent());
            g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
            fm = g2.getFontMetrics();
            r2 = fm.getStringBounds(textKey, g2);
            textWidth = r2.getWidth();
            textHeight = r2.getHeight();
            x = (width - textWidth) / 2;
            y = (height - textHeight) / 2;
            g2.drawString(textKey, (int) x, (int) y + fm.getAscent() + 50);
        }
        if (player.isAlive() && phase == 2 && rockets.isEmpty() && bossrockets.isEmpty()) {
            rockets.clear();
            String text = "WIN!";
            String textKey = "..Press Enter to Quit..";
            g2.setFont(getFont().deriveFont(Font.BOLD, 50f));
            FontMetrics fm = g2.getFontMetrics();
            Rectangle2D r2 = fm.getStringBounds(text, g2);
            double textWidth = r2.getWidth();
            double textHeight = r2.getHeight();
            double x = (width - textWidth) / 2;
            double y = (height - textHeight) / 2;
            g2.drawString(text, (int) x, (int) y + fm.getAscent());
            g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
            fm = g2.getFontMetrics();
            r2 = fm.getStringBounds(textKey, g2);
            textWidth = r2.getWidth();
            textHeight = r2.getHeight();
            x = (width - textWidth) / 2;
            y = (height - textHeight) / 2;
            g2.drawString(textKey, (int) x, (int) y + fm.getAscent() + 50);
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
