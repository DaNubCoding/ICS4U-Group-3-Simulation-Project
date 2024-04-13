import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * An anadromous fish, tasty with a bit of lemon juice
 *
 * @author Sandra Huang
 * @version April 2024
 */
public class Salmon extends TierOneFish
{
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings fishSettings;

    //sets this tier one fish's settings to basic settings, with specified body image and egg color
    static {
        fishSettings = new FishSettings();
        initialiseTierOneFishSettings(fishSettings, "salmon.png", Egg.EggColor.PINK);
    }

    public Salmon(int evoPoints, FishFeature... features) {
        super(fishSettings, evoPoints, features);
    }
}
