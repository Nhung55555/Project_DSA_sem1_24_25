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
    private Thread thread;
    private boolean start = true;
    private Key key;
    private int shotTime;
    private final int FPS = 60;
    private final int TARGET_TIME = 1000000000 / FPS;
    private Sound sound;
    //game object
    private Player player;
    private Bot bot;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private List<Effects> boomEffects;
    private int score = 0;

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
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
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

    private void checkPlayer(Rocket rocket) {
        if (rocket != null) {
            Area area = new Area(player.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHealth();
                if (!rocket.updateHealth(player.getHealth())) {
                    rockets.remove(rocket);
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
        player.changeLocation(150, 150);
        rockets = new ArrayList<>();
        boomEffects = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    addRocket();
                    sleep(900);
                }
            }
        }).start();
    }

    private void resetGame() {
        score = 0;
        rockets.clear();
        bullets.clear();
        player.changeLocation(150, 150);
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
                }
//                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                float s = 10.0f; //tốc độ quay
//                Random random = new Random(); // Random number generator

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
                    } else {
                        if (key.isKey_enter()) {
                            resetGame();
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
                    sleep(FPS);
                }
            }
        }).start();
    }

    private void initBullets() {
        bullets = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
                    for (int i = 0; i < bullets.size(); i++) {
                        Bullet bullet = bullets.get(i);
                        if (bullet != null) {
                            bullet.update();
                            checkBullets(bullet);
                            if (!bullet.check(width, height)) {
                                bullets.remove(bullet);
                            }
                        } else {
                            bullets.remove(bullet);
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
        }).start();
    }

    private void drawBackGround() {
        g2.setColor(new Color(30, 30, 30));
        g2.fillRect(0, 0, width, height);
    }

    private void drawGame() {
        // Game Playing
        if (player.isAlive()) {
            player.draw(g2);
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet != null) {
                bullet.draw(g2);
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
        g2.setColor(Color.WHITE);
        g2.setFont(getFont().deriveFont(Font.BOLD, 15f));
        g2.drawString("Score : " + score, 10, 20);
        if (!player.isAlive()) {
            String text = "GAME OVER";
            String textKey = "Press key enter to Continue ...";
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
