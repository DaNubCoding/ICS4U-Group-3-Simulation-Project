import greenfoot.*;

/**
 * A bubble that Fish may produce. Purely aesthetic.
 *
 * @author Andrew Wang
 * @version April 2024
 */
public class Bubble extends PixelActor {
    private double speed;
    private double riseSpeed;

    public Bubble() {
        super(Util.randInt(0, 1) == 0 ? "bubble_small.png" : "bubble_large.png");

        speed = Util.randDouble(0.1, 0.5);
        riseSpeed = Util.randDouble(0.4, 0.6);
        setHeading(Util.randInt(180, 360));
    }

    public void act() {
        setLocation(getDoubleX(), getDoubleY() - riseSpeed);
        move(speed);
        speed *= 0.97;

        if (getY() < SimulationWorld.SEA_SURFACE_Y) {
            getWorld().removeObject(this);
        }
    }
}
