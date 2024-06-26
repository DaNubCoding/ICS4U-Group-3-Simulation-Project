import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An anadromous fish, tasty with a bit of lemon juice
 *
 * @author Brandon Law
 * @author Sandra Huang
 * @version April 2024
 */
public class Salmon extends TierOneFish
{
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;

    //sets this tier one fish's settings to basic settings, with specified body image and egg color
    static {
        settings = new FishSettings();
        initialiseTierOneFishSettings(settings, "fishes/salmon.png", Egg.Color.PINK, Mollusk.class);
    }

    public Salmon(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
