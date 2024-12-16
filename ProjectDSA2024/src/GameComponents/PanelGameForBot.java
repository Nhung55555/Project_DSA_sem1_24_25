
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

import static GameObj.Bot.BOT_SIZE;
import static GameObj.Rocket.ROCKET_SIZE;

public class PanelGameForBot extends JPanel{

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
    private Bot bot;
    private List<Bullet> bullets;
    private List<Rocket> rockets;
    private List<RocketBoss> bossrockets;
    private List<Effects> boomEffects;
    private int score = 0;
    private int screenHeight = 760;

    private int phase = 1;

    public PanelGameForBot() {
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
    private void addRocketPhase1(int locationIndexer){
        int locationY = locationIndexer +20;
        Rocket rocket = new Rocket();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rockets.add(rocket);
//        rocketBullets.add(0, rocketbullet);
    }
    private void addRocketPhase2(int locationIndexer){
        int locationY = locationIndexer +450;
        Rocket rocket = new Rocket();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rockets.add(rocket);
//        rocketBullets.add(0, rocketbullet);
    }
    private void addRocketPhase3(int locationIndexer){
        int locationY = locationIndexer +100;
        Rocket rocket = new Rocket();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rockets.add(rocket);
//        rocketBullets.add(0, rocketbullet);
    }
    private void addRocketPhase4(int locationIndexer){
        int locationY = locationIndexer +200;
        Rocket rocket = new Rocket();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rockets.add(rocket);
//        rocketBullets.add(0, rocketbullet);
    }
    private void addRocketPhase5(int locationIndexer){
        int locationY = locationIndexer +50;
        Rocket rocket = new Rocket();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rockets.add(rocket);
//        rocketBullets.add(0, rocketbullet);
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
//                        sound.shoundDestroy();
                        double x = rocket.getX() + ROCKET_SIZE / 2;
                        double y = rocket.getY() + ROCKET_SIZE / 2;
                        boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                        boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                        boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                        boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                        boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                    } else {
//                        sound.shoundHit();
                    }
                    bullets.remove(bullet);
                }
            }
        }
    }


    private void checkBot(RocketBoss rocket) {
        if (rocket != null) {
            Area area = new Area(bot.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHealth();
                if (!rocket.updateHealth(bot.getHealth())) {
                    bossrockets.remove(rocket);
//                    sound.shoundDestroy();

                    double x = rocket.getX() + ROCKET_SIZE / 2;
                    double y = rocket.getY() + ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }
                if (!bot.updateHealth(rocketHp)) {
                    bot.setAlive(false);
                    sound.shoundDestroy();

                    double x = rocket.getX() + ROCKET_SIZE / 2;
                    double y = rocket.getY() + ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }

            }
        }

    }

    private void checkBot(Rocket rocket) {
        if (rocket != null) {
            Area area = new Area(bot.getShape());
            area.intersect(rocket.getShape());
            if (!area.isEmpty()) {
                double rocketHp = rocket.getHealth();
                if (!rocket.updateHealth(bot.getHealth())) {
                    rockets.remove(rocket);
//                    sound.shoundDestroy();

                    double x = rocket.getX() + ROCKET_SIZE / 2;
                    double y = rocket.getY() + ROCKET_SIZE / 2;
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.05f, new Color(248, 140, 9)));
                    boomEffects.add(new Effects(x, y, 5, 5, 75, 0.1f, new Color(255, 78, 78)));
                    boomEffects.add(new Effects(x, y, 10, 10, 100, 0.3f, new Color(49, 234, 78)));
                    boomEffects.add(new Effects(x, y, 10, 5, 100, 0.5f, new Color(126, 210, 84)));
                    boomEffects.add(new Effects(x, y, 10, 5, 150, 0.2f, new Color(213, 227, 51)));
                }
                if (!bot.updateHealth(rocketHp)) {
                    bot.setAlive(false);
//                    bot.shoundDestroy();

                    double x = rocket.getX() + ROCKET_SIZE / 2;
                    double y = rocket.getY() + ROCKET_SIZE / 2;
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
        bot = new Bot(1366, 760);
        bot.changeLocation(150, 150);
        bossrockets = new ArrayList<>();

        rockets = new ArrayList<>();
        boomEffects = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start) {
//                    addRocket();
//                    sleep(900);
                    switch (phase) {

                        case 1 -> {
                            System.out.println("Phase 1");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase1(i * 63);
                            }
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                        sleep(500);
                            System.out.println("Phase 2");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase2(i * 63);
                            }
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Phase 3");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase3(i * 63);
                            }
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Phase 4");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase4(i * 63);
                            }
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Phase 5");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase5(i * 63);
                            }
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                        System.out.println("Boss Appeared!");
                        addBossRocketPhase(450);
                        while(!bossrockets.isEmpty()){
                            sleep(30);
                        }
                            phase++;
                            sleep(2000);
                        }
                    }
                }
            }
        }).start();
    }

    private void resetGameBot() {
        score = 0;
        rockets.clear();
        bullets.clear();
        bot.changeLocation(150, 150);
        bot.reset();
    }




//    private void initKeyboard() {
//        key = new Key();
//        requestFocus();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                float dangerDistance = 100.0f;  // Danger zone for dodging
//                float shootingDistance = 1000.0f;  // Shooting range
//                float safeOffset = 150.0f;  // Safe zone distance above or below rocket
//
//                Rocket currentTarget = null;  // Track the current target rocket
//
//                while (start) {
//                    if (bot.isAlive()) {
//                        bot.update();
//
//                        // Find the nearest rocket
//                        Rocket nearestRocket = null;
//                        double minDistance = Double.MAX_VALUE;
//
//                        for (Rocket rocket : rockets) {
//                            if (rocket != null) {
//                                double distance = Math.hypot(
//                                        rocket.getX() - bot.getX(),
//                                        rocket.getY() - bot.getY()
//                                );
//
//                                if (distance < minDistance) {
//                                    minDistance = distance;
//                                    nearestRocket = rocket;
//                                }
//                            }
//                        }
//
//                        // Assign the nearest rocket as the target if no target is set or the target is destroyed
//                        if (currentTarget == null || !rockets.contains(currentTarget)) {
//                            currentTarget = nearestRocket;
//                        }
//
//                        // Bot logic: Dodge and shoot
//                        if (currentTarget != null) {
//                            float rocketY = (float) currentTarget.getY();
//                            float botY = (float) bot.getY();
//
//                            // Move to the safe zone opposite the rocket
//                            if (minDistance < dangerDistance) {
//                                if (rocketY < botY && botY + BOT_SIZE < screenHeight - 10) {
//                                    bot.moveDown();  // Move down if the rocket is above the bot
//                                } else if (rocketY > botY && botY > 10) {
//                                    bot.moveUp();  // Move up if the rocket is below the bot
//                                }
//                            } else if (minDistance < shootingDistance) {
//                                // Stay in position above or below the rocket to shoot
//                                if (botY > rocketY - safeOffset && botY > 10) {
//                                    bot.moveUp();
//                                } else if (botY < rocketY + safeOffset && botY + BOT_SIZE < screenHeight - 10) {
//                                    bot.moveDown();
//                                } else {
//                                    bot.stopMoving();
//                                }
//
//                                // Shoot at the rocket
//                                bullets.add(0, new Bullet(bot.getX(), bot.getY(), bot.getAngle(), 5, 1f));
//                            } else {
//                                bot.stopMoving();  // Stop moving if no immediate threat
//                            }
//                        } else {
//                            bot.stopMoving();  // No rockets in range
//                        }
//
//                        // Update and remove rockets
//                        for (int i = 0; i < rockets.size(); i++) {
//                            Rocket rocket = rockets.get(i);
//                            if (rocket != null) {
//                                rocket.update();
//                                if (!rocket.check(width, height)) {
//                                    rockets.remove(rocket);
//                                    currentTarget = null;  // Reset the target
//                                }
//                            }
//                        }
//
//                        sleep(FPS);  // Maintain game loop speed
//                    } else {
//                        if (key.isKey_enter()) {
//                            resetGameBot();  // Restart the game if the bot is dead
//                        }
//                    }
//                }
//            }
//        }).start();
//    }


//xác định vị trí và ngắm bắn từng rocket
    private void initKeyboard() {
        key = new Key();
        requestFocus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                float safetyDistance = 100.0f; // Maintain this distance from the rocket in the x-axis
                Rocket currentTarget = null; // Track the current target rocket

                while (start) {
                    if (bot.isAlive()) {
                        bot.update();

                        // Find the nearest rocket
                        Rocket nearestRocket = null;
                        double minDistance = Double.MAX_VALUE;

                        for (Rocket rocket : rockets) {
                            if (rocket != null) {
                                double distance = Math.hypot(
                                        rocket.getX() - bot.getX(),
                                        rocket.getY() - bot.getY()
                                );

                                if (distance < minDistance) {
                                    minDistance = distance;
                                    nearestRocket = rocket;
                                }
                            }
                        }

                        // Assign the nearest rocket as the target if no target is set or the target is destroyed
                        if (currentTarget == null || !rockets.contains(currentTarget)) {
                            currentTarget = nearestRocket;
                        }

                        // Bot logic: Move to align with the rocket's y-coordinate
                        if (currentTarget != null) {
                            float rocketY = (float) currentTarget.getY();
                            float botY = (float) bot.getY();

                            // Move up or down to align with the rocket's y-coordinate
                            if (botY > rocketY) {
                                bot.moveUp();
                            } else if (botY < rocketY) {
                                bot.moveDown();
                            } else {
                                bot.stopMoving(); // Stop when aligned with the rocket's y-coordinate
                            }

                            // Stay in place and shoot if within range and aligned
                            if (Math.abs(bot.getX() - currentTarget.getX()) >= safetyDistance) {
                                bullets.add(0, new Bullet(bot.getX(), bot.getY(), bot.getAngle(), 5, 1f));
                            }
                        } else {
                            bot.stopMoving(); // No rocket target, allow free movement
                        }

                        // Update and remove rockets
                        for (int i = 0; i < rockets.size(); i++) {
                            Rocket rocket = rockets.get(i);
                            if (rocket != null) {
                                rocket.update();
                                if (!rocket.check(width, height)) {
                                    rockets.remove(rocket);
                                    currentTarget = null; // Reset the target when a rocket is removed
                                }
                            }
                        }

                        sleep(FPS); // Maintain game loop speed
                    } else {
                        if (key.isKey_enter()) {
                            resetGameBot(); // Restart the game if the bot is dead
                        }
                    }
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
            if (bot.isAlive()) {
                bot.draw(g2);
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

            if (!bot.isAlive()) {
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
