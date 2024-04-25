import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * A massive many teethed horror that consumes everything.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class Bloop extends EndingFish
{
    // Collection of all settings that define this subclass of EndingFish
    private static final FishSettings settings;
    private boolean eat;
    private int doomTimer = 2400;
    //sets this ending fish's settings to basic settings, with specified body image and egg color
    static {
        settings = new FishSettings();
        settings.setEggColor(Egg.Color.GREEN); // unused
        settings.setEggSpawnFrequency(999999);
        settings.setBodyImage("endFish/Bloop/bloop-young.png");
        settings.setSwimSpeed(0.3);
        initializeEndingFish(settings);
    }

    public Bloop(int _evoPoints, FishFeature... _features) {
        super(settings, null);
        eat = false;
    }

    public void act()
    {
        //Behaves like normal fish until timer hits 0
        if (doomTimer > 0)
        {
            doomTimer -= 1;
            if (doomTimer < 600)
            {
                settings.setMaxTurnDegrees(0);
            }
            super.act();
        } else {
            //Bloop will leave the screen and come back bigger and starts to eat the fish
            if (doomTimer > -5)
            {
                crossTheScreen();
            } else {
                //The Bloop then disappears and then appears looming from the background
                eat = false;
                setLocation(getWorld().getWidth()/2, 75);
                setImage("endFish/Bloop/bloop-looming.png");
                if (doomTimer > -1000)
                {
                    doomTimer -= 1;
                } else {
                    ((SimulationWorld)getWorld()).fishEnd(0);
                }
                setTransparency(0-(int)(doomTimer*0.1));
            }
        }
    }

    //The Bloop will move across the screen back and fourth, eating fish
    private void crossTheScreen()
    {
        //If Bloop is fully grown, eats fish along the way
        if (eat) {
            PixelWorld world = getWorld();
            List<Fish> eatenFish = world.getObjectsInRange(18, getX()+((getHeading() > 90 && getHeading() < 270)?-45:+45), getY(), Fish.class);
            List<Egg> eatenEggs = world.getObjectsInRange(18, getX()+((getHeading() > 90 && getHeading() < 270)?-45:+45), getY(), Egg.class);
            for (Fish f:eatenFish) {
                if (f != this) {
                    world.removeObject(f);
                }
            }
            for (Egg e:eatenEggs) {
                world.removeObject(e);
            }
        }

        move(settings.getSwimSpeed());
        if (getX() < -100 || getX() > getWorld().getWidth()+100) {
            setHeading(getHeading() > 90 && getHeading() < 270 ? 0 : 180);
            setImage("endFish/Bloop/bloop-grown.png");
            setMirrorX(getHeading() > 90 && getHeading() < 270);
            setRotation(0);
            setLocation(getX(), Greenfoot.getRandomNumber(70)+65);
            settings.setSwimSpeed(0.6);
            doomTimer -= 1;
            eat = true;
        }
    }
}
