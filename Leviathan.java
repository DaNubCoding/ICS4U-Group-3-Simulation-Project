import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Invasive apex preadators that reproduce like bunnies and wrecks the whole eco-system.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class Leviathan extends EndingFish
{
    // Collection of all settings that define this subclass of EndingFish
    private static final FishSettings settings;
    private int doomTimer = 1000;
    private double speed;
    private int type;
    //sets this ending fish's settings to basic settings, with specified body image and egg color
    static {
        settings = new FishSettings();
        settings.setEggColor(Egg.Color.GREEN);
        settings.setEggSpawnFrequency(1000);
        settings.setBodyImage("endFish/Leviathans/leviathan_1.png");
        settings.setSwimSpeed(0.6);
        initializeEndingFish(settings);
    }

    public Leviathan(int _evoPoints, FishFeature... _features) {
        super(settings, null);
        type = Util.randInt(1, 3);
        setImage("endFish/Leviathans/leviathan_" + type + ".png");
        speed = settings.getSwimSpeed();
    }

    public void act()
    {
        super.act();
        //Acts normally at first
        doomTimer -= 1;
        settings.setSwimSpeed(speed);
        if (doomTimer > 300)
        {
            doomTimer -= 1;
        } else {
            //Starts to mass reporduce and get feral
            if (settings.getSwimSpeed() < 1.5)
            {
                speed += 0.001;
            } else {
                //Starts eating eggs after a while
                int speedMulti = 18;
                int rad = 4;
                if (type == 2) {
                    speedMulti = 8;
                    rad = 9;
                }
                if (type == 3) {
                    speedMulti = 14;
                    rad = 6;
                }
                move(speed*speedMulti);
                PixelWorld world = getWorld();
                List<Egg> egg = getObjectsInRange(rad, Egg.class);
                for (Egg e:egg) {
                    if (!e.isLeviathanEgg()) {
                        world.removeObject(e);
                    }
                }
                //Starts eating all of the fish that arent Leviathans
                if (doomTimer < -500) {
                    settings.setMinDepth(SimulationWorld.SEA_SURFACE_Y-8);
                    List<Fish> fish = getObjectsInRange(rad, Fish.class);
                    for (Fish f:fish) {
                        if (!f.getClass().isAssignableFrom(Leviathan.class)) {
                            world.removeObject(f);
                        }
                    }
                }
                move(-speed*speedMulti);
            }
        }
    }
}
