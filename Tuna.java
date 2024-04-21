import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A delicious canned delicacy!
 *
 * @author Brandon Law
 * @author Sandra Huang
 * @version April 2024
 */
public class Tuna extends TierOneFish
{
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;

    //sets this tier one fish's settings to basic settings, with specified body image and egg color
    static {
        settings = new FishSettings();
        initialiseTierOneFishSettings(settings, "fishes/tuna.png", Egg.Color.BLUE, Flyingfish.class);
    }

    public Tuna(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
