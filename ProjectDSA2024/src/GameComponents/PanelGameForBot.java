
package GameComponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import GameMain.Main;
import GameObj.*;
import GameObj.sound.Sound;

import static GameObj.Rocket.ROCKET_SIZE;
import static GameObj.RocketBoss.ROCKET_BOSS_SIZE;
import static GameObj.RocketForDodge.ROCKET_DODGE_SIZE;

public class PanelGameForBot extends JPanel{
    //game
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
    private List<RocketForDodge> rocketForDodges;
    private List<RocketBoss> bossrockets;
    private List<Effects> boomEffects;
    private int score = 0;
    private int phase = 1;

    private Stack<Rocket> rocketStack = new Stack<>();

    private Main mainFrame; // Reference to the Main class


    public PanelGameForBot(Main mainFrame) {

//        setBackground(Color.GREEN);
        this.mainFrame = mainFrame;
        setFocusable(true); // Make sure the panel can listen for key events
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Example: Press Escape to go back to the menu
                    mainFrame.switchToMenu(); // Switch back to the menu
                }
            }
        });

    }
    public void start() {
        System.out.println("Game started for bot");

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

//    private void addRocket() {
//        Random ran = new Random();
//
//        int locationY2 = ran.nextInt(height - 50) + 25;
//        Rocket rocket2 = new Rocket();
//        rocket2.changeLocation(width, locationY2);
//        rocket2.changeAngle(180);
//        rockets.add(rocket2);
//    }
    private void addRocketPhase1(int locationIndexer){
        int locationY = locationIndexer +20;
        RocketForDodge rocket = new RocketForDodge();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rocketForDodges.add(rocket);
    }
    private void addRocketPhase2(int locationIndexer){
        int locationY = locationIndexer +300;
        RocketForDodge rocket = new RocketForDodge();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rocketForDodges.add(rocket);
    }

    private void addRocketPhase3(int locationIndexer){
        int locationY = locationIndexer +50;
        Rocket rocket = new Rocket();
        RocketBullets rocketbullet = new RocketBullets(width, locationY, 180, 5, 10);
        rocket.changeLocation(width, locationY);
        rocket.changeAngle(180);
        System.out.println(rocketbullet);
        rockets.add(rocket);
//        rocketBullets.add(0, rocketbullet);
    }
//    private void addRocketToStack(int locationY) {
//        Rocket rocket = new Rocket();
//        rocket.changeLocation(width, locationY);
//        rocket.changeAngle(180);
//        rocketStack.push(rocket); // Push the rocket onto the stack
//    }
//    private void launchRocketsFromStack() {
//        while (!rocketStack.isEmpty()) {
//            Rocket rocket = rocketStack.pop(); // Retrieve and remove the last rocket
//            rockets.add(rocket);
//            System.out.println("Launched Rocket from Stack at Y: " + rocket.getY());
//            sleep(30); // Add a delay to simulate rocket launch
//        }
//    }
private void addRocketToStack(int locationX, int locationY) {
    Rocket rocket = new Rocket();
    rocket.changeLocation(locationX, locationY); // Set both X and Y position
    rocket.changeAngle(180); // Set the rocket's angle (flying to the left, for example)
    rocketStack.push(rocket); // Push the rocket onto the stack
}
    private void launchRocketsFromStack() {
        while (!rocketStack.isEmpty()) {
            Rocket rocket = rocketStack.pop(); // Retrieve and remove the last rocket
            rockets.add(rocket);
            System.out.println("Launched Rocket from Stack at X: " + rocket.getX() + ", Y: " + rocket.getY());
            sleep(30); // Add a delay to simulate rocket launch
        }
    }
    // Function to move the rocket along X and Y
//    private void moveRocket(Rocket rocket) {
//        double newX = rocket.getX() - 2; // Move left (decreasing X)
//        double newY = rocket.getY() - 1; // Move upward (decreasing Y)
//        // Update rocket's position
//        rocket.changeLocation(newX, newY);
//    }


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
    private void checkBulletsDodge(Bullet bullet) {
        for (int i = 0; i < rocketForDodges.size(); i++) {
            RocketForDodge rocket = rocketForDodges.get(i);
            if (rocket != null) {
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 3, 5, 60, 0.5f, new Color(230, 207, 105)));
                    if (!rocket.updateHealth(bullet.getSize())) {
                        score++;
                        rocketForDodges.remove(rocket);
//                        sound.shoundDestroy();
                        double x = rocket.getX() + ROCKET_DODGE_SIZE / 2;
                        double y = rocket.getY() + ROCKET_DODGE_SIZE / 2;
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

    private void checkBulletsBoss(Bullet bullet) {
        for (int i = 0; i < bossrockets.size(); i++) {
            RocketBoss rocket = bossrockets.get(i);
            if (rocket != null) {
                Area area = new Area(bullet.getShape());
                area.intersect(rocket.getShape());
                if (!area.isEmpty()) {
//                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 320, 0.5f, new Color(255, 218, 52)));
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 310, 0.15f, new Color(222, 89, 6)));
//                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 220, 0.25f, new Color(255, 42, 4)));
                    boomEffects.add(new Effects(bullet.getCenterX(), bullet.getCenterY(), 6, 20, 230, 0.7f, new Color(255, 245, 186)));
                    if (!rocket.updateHealth(bullet.getSize())) {
                        score += 100;
                        bossrockets.remove(rocket);
//                        sound.shoundDestroy();
                        double x = rocket.getX() + ROCKET_BOSS_SIZE/2;
                        double y = rocket.getY() + ROCKET_BOSS_SIZE/2;
//                        boomEffects.add(new Effects(x, y, 5, 15, 175, 0.25f, new Color(255, 38, 0)));
//                        boomEffects.add(new Effects(x, y, 5, 25, 175, 0.1f, new Color(255, 144, 144)));
                        boomEffects.add(new Effects(x, y, 10, 12, 200, 0.3f, new Color(246, 227, 9)));
                        boomEffects.add(new Effects(x, y, 10, 40, 200, 0.5f, new Color(168, 229, 111)));
//                        boomEffects.add(new Effects(x, y, 10, 25, 250, 0.2f, new Color(248, 196, 72)));
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
        rocketForDodges = new ArrayList<>();
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
//                            addRocket();
//                            sleep(900);
//                            addRocket();
//                            sleep(900);
//                            addRocket();
//                            sleep(900);
//                            addRocket();
//                            sleep(900);
//                            addRocket();
//                            sleep(900);
//
//                            System.out.println("Phase 2");
//                            for (int i = 0; i < 1; i++) {
//                                addRocketPhase1(i * 63);
//                            }
//                            while (!rockets.isEmpty()) {
//                                sleep(30);
//                            }
//                            System.out.println("Phase 3");
//                            for (int i = 0; i < 1; i++) {
//                                addRocketPhase2(i * 63);
//                            }
//                            while (!rockets.isEmpty()) {
//                                sleep(30);
//                            }
//                            System.out.println("Phase 4");
//                            for (int i = 0; i < 1; i++) {
//                                addRocketPhase3(i * 63);
//                            }
//                            while (!rockets.isEmpty()) {
//                                sleep(30);
//                            }
//                            System.out.println("Phase 5");
//                            for (int i = 0; i < 1; i++) {
//                                addRocketPhase4(i * 63);
//                            }
//                            while (!rockets.isEmpty()) {
//                                sleep(30);
//                            }
                            System.out.println("Phase 3");

                            System.out.println("Adding rockets with multiple X and Y positions...");
                            int[] rocketXPositions = {1200,1100,1000,1200}; // X positions
                            int[] rocketYPositions = { 100,300,405,650}; // Y positions

                            for (int i = 0; i < rocketXPositions.length; i++) {
                                addRocketToStack(rocketXPositions[i], rocketYPositions[i]);
                            }
                            launchRocketsFromStack();
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Phase 1");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase1(i * 63);
                            }
                            while (!rocketForDodges.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Phase 2");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase2(i * 63);
                            }
                            while (!rocketForDodges.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Phase 3");
                            for (int i = 0; i < 1; i++) {
                                addRocketPhase3(i * 63);
                            }
                            while (!rockets.isEmpty()) {
                                sleep(30);
                            }
                            System.out.println("Boss Appeared!");
                        addBossRocketPhase(450);
//                        addRocketPhase2(63);
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

    private void initKeyboard() {
        key = new Key();
        requestFocus();
        new Thread(new Runnable() {
            @Override
            public void run() {
                float safetyDistance = 100.0f; // Distance to maintain for shooting
                float dangerDistance = 100.0f; // Distance to start dodging
                float dodgeStep = 50.0f;     // How far the bot moves when dodging
                Rocket currentTarget = null; // Current target rocket
                RocketBoss currentBossTarget = null; // Current target rocket boss
                RocketForDodge currentRocket = null;

                while (start) {
                    if (bot.isAlive()) {
                        bot.update();
                        Rocket nearestRocketDFS = findNearestRocketDFS(rockets, 0, null, Double.MAX_VALUE);
                        RocketForDodge nearestRocketDodgeBFS = findNearestRocketBFS();
                        // Assign the nearest rocket as the target if no target is set or the target is destroyed
                        if (currentTarget == null || !rockets.contains(currentTarget)) {
                            currentTarget = nearestRocketDFS;
                        }
                        if(currentRocket == null || !rocketForDodges.contains(currentRocket)){
                            currentRocket = nearestRocketDodgeBFS;
                        }
                        if (currentTarget != null) {
                            float rocketX = (float) currentTarget.getX();
                            float rocketY = (float) currentTarget.getY();
                            float botX = (float) bot.getX();
                            float botY = (float) bot.getY();
                            // Calculate the horizontal distance to the rocket
                            double horizontalDistance = Math.abs(botX - rocketX);
                            // If the rocket is too close, prioritize dodging
                            if (horizontalDistance < dangerDistance) {
                                // Dodge logic: Check if there is space above or below
                                if (botY - dodgeStep > 0) { // Space above
                                    bot.moveUp();
                                } else if (botY + dodgeStep < height) { // Space below
                                    bot.moveDown();
                                } else {
                                    bot.stopMoving();}
                            } else {
                                // Normal behavior: Align vertically with the rocket and shoot
                                if (Math.abs(botY - rocketY) > 5.0f) { // Align vertically
                                    if (botY > rocketY) {
                                        bot.moveUp();
                                    } else if (botY < rocketY) {
                                        bot.moveDown();
                                    }
                                } else {
                                    bot.stopMoving(); // Stop moving when aligned
                                    // Shoot if at a safe distance
                                    if (horizontalDistance >= safetyDistance) {
                                        bullets.add(0, new Bullet(bot.getX(), bot.getY(), bot.getAngle(), 2, 3f));
                                    }
                                }
                            }
                        }
                        else if (currentRocket != null) {
                            float rocketX = (float) currentRocket.getX();
                            float rocketY = (float) currentRocket.getY();
                            float botX = (float) bot.getX();
                            float botY = (float) bot.getY();
                            // Calculate the horizontal distance to the rocket
                            double horizontalDistance = Math.abs(botX - rocketX); //tính theo trục x trục ngang, cái nào gần hơn thì bắn trước
                            // If the rocket is too close, prioritize dodging
                            if (horizontalDistance < dangerDistance) {
                                // Dodge logic: Check if there is space above or below
                                if (botY - dodgeStep > 0) { // Space above
                                    bot.moveUp();
                                } else if (botY + dodgeStep < height) { // Space below
                                    bot.moveDown();
                                } else {
                                    bot.stopMoving(); // No space to dodge
                                }
                            } else {
                                // Normal behavior: Align vertically with the rocket and shoot
                                if (Math.abs(botY - rocketY) > 5.0f) { // Align vertically
                                    if (botY > rocketY) {
                                        bot.moveUp();
                                    } else if (botY < rocketY) {
                                        bot.moveDown();
                                    }
                                } else {
                                    bot.stopMoving(); // Stop moving when aligned

                                    // Shoot if at a safe distance
                                    if (horizontalDistance >= safetyDistance) {
                                        bullets.add(0, new Bullet(bot.getX(), bot.getY(), bot.getAngle(), 2, 3f));
                                    }
                                }
                            }
                        }

                        else {
                            // If no regular rockets are left, focus on RocketBoss
                            if (rockets.isEmpty() &&rocketForDodges.isEmpty() && !bossrockets.isEmpty()) {
                                // Assign a RocketBoss as the target if not already assigned
                                if (currentBossTarget == null || !bossrockets.contains(currentBossTarget)) {
                                    currentBossTarget = bossrockets.get(0); // Take the first RocketBoss
                                }
                                if (currentBossTarget != null) {
                                    float bossCenterX = (float) (currentBossTarget.getX() + ROCKET_BOSS_SIZE / 2);
                                    float bossCenterY = (float) (currentBossTarget.getY() + ROCKET_BOSS_SIZE / 2);
                                    float botY = (float) bot.getY();

                                    // Align with the RocketBoss and shoot
                                    if (Math.abs(botY - bossCenterY) > 5.0f) { // Align vertically
                                        if (botY > bossCenterY) {
                                            bot.moveUp();
                                        } else if (botY < bossCenterY) {
                                            bot.moveDown();
                                        }
                                    } else {
                                        bot.stopMoving(); // Stop moving when aligned
                                        bullets.add(0, new Bullet(bot.getX(), bot.getY(), bot.getAngle(), 3, 3f)); // Shoot at RocketBoss
                                    }
                                }

                            } else {
                                bot.stopMoving(); // No target, stop movement
                            }
                        }
//

                        // Update and remove rockets
                        for (int i = 0; i < rockets.size(); i++) {
                            Rocket rocket = rockets.get(i);
                            if (rocket != null) {
                                rocket.update();
                                if (!rocket.check(width, height)) {
                                    rockets.remove(rocket);
                                    currentTarget = null; // Reset the target when a rocket is removed
                                }
                                else {
                                    if (bot.isAlive()) {
                                        checkBot(rocket);
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < rocketForDodges.size(); i++) {
                            RocketForDodge rocket = rocketForDodges.get(i);
                            if (rocket != null) {
                                rocket.update();
                                if (!rocket.check(width, height)) {
                                    rocketForDodges.remove(rocket);
                                    currentRocket = null; // Reset the target when a rocket is removed
                                }
                            }
                        }

                        for (int i = 0; i < bossrockets.size(); i++) {
                            RocketBoss rocketBoss = bossrockets.get(i);
                            if (rocketBoss != null) {
                                rocketBoss.update();
                                if (!rocketBoss.check(width, height)) {
                                    bossrockets.remove(rocketBoss);
                                } else {
                                    if (bot.isAlive()&& bossrockets.isEmpty()) {
                                        checkBot(rocketBoss);
                                    }
                                    else {
                                        if (bot.isAlive()) {
                                            checkBot(rocketBoss);
                                        }
                                    }
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

    // DFS to find the nearest rocket
    private Rocket findNearestRocketDFS(List<Rocket> rockets, int index, Rocket nearestRocket, double minDistance) {
        if (index >= rockets.size()) return nearestRocket; // Base case

        Rocket currentRocket = rockets.get(index);
        if (currentRocket != null) {
            double distance = Math.hypot(
                    currentRocket.getX() - bot.getX(),
                    currentRocket.getY() - bot.getY()
            );
            if (distance < minDistance) {
                nearestRocket = currentRocket;
                minDistance = distance;
            }
        }
        // Recursive call for next rocket
        return findNearestRocketDFS(rockets, index + 1, nearestRocket, minDistance);
    }

private RocketForDodge findNearestRocketBFS() {
    RocketForDodge nearestRocket = null;
    double minDistance = Double.MAX_VALUE;

    Queue<RocketForDodge> queue = new LinkedList<>(rocketForDodges);

    while (!queue.isEmpty()) {
        RocketForDodge currentRocket = queue.poll();

        if (currentRocket != null) {
            double distance = Math.hypot(
                    currentRocket.getX() - bot.getX(),
                    currentRocket.getY() - bot.getY()
            );
            if (distance < minDistance) {
                minDistance = distance;
                nearestRocket = currentRocket;
            }
        }
    }
    return nearestRocket;
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
                            checkBulletsDodge(bullet);
                            checkBulletsBoss(bullet);

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
        for (int i = 0; i < rocketForDodges.size(); i++) {
            RocketForDodge rocket = rocketForDodges.get(i);
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

            if(bot.isAlive()&&rockets.isEmpty()&&rocketForDodges.isEmpty()){
                sleep(35);
                if(bossrockets.isEmpty()){
                    String text = "WIN";
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
