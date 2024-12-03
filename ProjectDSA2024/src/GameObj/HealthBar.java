package GameObj;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HealthBar {
    private final HealthPoints health;

    public HealthBar(HealthPoints health) {
        this.health = health;
    }
    protected void hpRender(Graphics2D g2, Shape shape, double y) {
        double hpY = shape.getBounds().getY() - y - 10;
        g2.setColor(new Color(70, 70, 70));
        g2.fill(new Rectangle2D.Double(0, hpY, Player.PLAYER_SIZE, 2));
        g2.setColor(new Color(253, 91, 91));
        double hpSize = health.getCurrentHealth() / health.getMaxHealth() * Player.PLAYER_SIZE;
        g2.fill(new Rectangle2D.Double(0, hpY, hpSize, 2));
    }
    public boolean updateHealth(double damage){
        health.setCurrentHealth(health.getCurrentHealth() - damage);
        return health.getCurrentHealth() > 0;
    }

    public double getHealth(){
        return health.getCurrentHealth();
    }
    public void resetHealth(){
        health.setCurrentHealth(health.getMaxHealth());
    }

}
