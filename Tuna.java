import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A delicious canned delicacy!
 * 
 * @author Sandra
 * @version (a version number or a date)
 */
public class Tuna extends TierOneFish
{
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings fishSettings;
    
    //sets this tier one fish's settings to basic settings, with specified body image and egg color
    static {
        fishSettings = new FishSettings();
        initialiseTierOneFishSettings(fishSettings, "tuna.png", Egg.EggColor.BLUE);
    }
    
    public Tuna(int evoPoints, FishFeature... features) {
        super(fishSettings, evoPoints, features);
    }
    
    //change this!!!!
    public Fish createOffspring(){
        return null;
    }
}
