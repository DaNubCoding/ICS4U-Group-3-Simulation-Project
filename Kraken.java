import greenfoot.*;

/**
 * Many tentacled terror of the ocean.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class Kraken extends EndingFish
{
    // Collection of all settings that define this subclass of EndingFish
    private static final FishSettings settings;
    private int doomTimer = 2400;
    private int alpha = 255;
    //sets this ending fish's settings to basic settings, with specified body image and egg color
    static {
        settings = new FishSettings();
        settings.setEggColor(Egg.Color.PINK); // unused
        settings.setEggSpawnFrequency(999999);
        settings.setBodyImage("endFish/Kraken/kraken.png");
        settings.setSwimSpeed(0.2);
        initializeEndingFish(settings);
    }

    public Kraken(int _evoPoints, FishFeature... _features) {
        super(settings, null);
    }

    @Override
    public void act()
    {
        //Acts normal at first
        super.act();
        doomTimer -= 1;
        if (doomTimer <= 0) {
            //Starts spewing mass ammounts of ink that darkens the sea and kills the fish
            if (doomTimer > -800) {
                if (Util.randInt(0,2) == 0) {
                    getWorld().addObject(new KrakenInk(), getX(), getY()+5);
                }
            } else {
                //The Kraken waits for a bit before attacking
                if (alpha > 0) {
                    alpha -= 1;
                }
                setTransparency(alpha);
                if (doomTimer == -1550) {
                    for (int i = 0; i < 5; i++) {
                        getWorld().addObject(new KrakenTentacle(Layer.BG_BUBBLE), 25+(getWorld().getWidth()/5)*i, 55);
                    }
                }
                if (doomTimer == -1625) {
                    for (int i = 0; i < 4; i++) {
                        getWorld().addObject(new KrakenTentacle(Layer.FG_BUBBLE), 40+(getWorld().getWidth()/4-10)*i, 50);
                    }
                }
                if (doomTimer == -1650) {
                    ((SimulationWorld)getWorld()).fishEnd(1);
                }
            }
        }
    }
}
