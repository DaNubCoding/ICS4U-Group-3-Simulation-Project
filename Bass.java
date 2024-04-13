import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A fast freshwater fish
 * 
 * @author Sandra Huang
 * @version April 2024
 */
public class Bass extends TierOneFish
{
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings fishSettings;
    
    //sets this tier one fish's settings to basic settings, with specified body image and egg color
    static {
        fishSettings = new FishSettings();
        initialiseTierOneFishSettings(fishSettings, "bass.png", Egg.EggColor.GREEN);
    }
    
    public Bass(int evoPoints, FishFeature... features) {
        super(fishSettings, evoPoints, features);
    }
    
    //change this!!!
    public Fish createOffspring(){
        return null;
    }
}
