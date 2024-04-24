import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Ink splotch made by the Kraken.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class KrakenInk extends PixelActor
{
    private double speed;
    private double ySpeed;
    public KrakenInk() {
        super("endFish/Kraken/ink_"+Util.randInt(0, 3)+".png", Layer.FG_BUBBLE);
        speed = Util.randDouble(0.1, 1);
        ySpeed = Util.randDouble(-0.2, 0.5);
        setHeading(Util.randInt(0, 360));
    }

    public void act()
    {
        setLocation(getDoubleX(), getDoubleY() - ySpeed);
        move(speed);
        speed *= 0.99;
        ySpeed *= 0.99;
        speed += Util.randDouble(0.01, -0.01);
        ySpeed += Util.randDouble(0.01, -0.01);
        if (getY() < SimulationWorld.SEA_SURFACE_Y) {
            setLocation(getX(), SimulationWorld.SEA_SURFACE_Y);
        }
        if (getY() > SimulationWorld.SEA_FLOOR_Y) {
            setLocation(getX(), SimulationWorld.SEA_FLOOR_Y);
        }

        //Ink kills any creature it engulfs
        PixelWorld world = getWorld();
        List<Fish> fish = getObjectsInRange(5, Fish.class);
        List<Egg> egg = getObjectsInRange(5, Egg.class);
        for (Fish f:fish) {
            if (!f.getClass().isAssignableFrom(Kraken.class) && Util.randInt(0, 10) == 0) {
                world.removeObject(f);
            }
        }
        for (Egg e:egg) {
            if (Util.randInt(0, 10) == 0) {
                world.removeObject(e);
            }
        }
    }
}
