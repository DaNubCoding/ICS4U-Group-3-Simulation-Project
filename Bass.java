import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A fast freshwater fish
 *
 * @author Brandon Law
 * @author Sandra Huang
 * @version April 2024
 */
public class Bass extends TierOneFish
{
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;

    //sets this tier one fish's settings to basic settings, with specified body image and egg color
    static {
        settings = new FishSettings();
        initialiseTierOneFishSettings(settings, "fishes/bass.png", Egg.EggColor.GREEN, Piranha.class);
    }

    public Bass(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
