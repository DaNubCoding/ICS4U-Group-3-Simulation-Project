import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The final tier of fish, the rapid mutations have turned them into deadly calamities.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public class EndingFish extends Fish
{
    static boolean endFish = false;
    public EndingFish(FishSettings settings, FishFeature... features) {
        super(settings, 0, features);
    }

    public static void initializeEndingFish(FishSettings settings, String bodyImage, double speed, int type) {
        // Base settings
        settings.setTier(5);
        settings.setBaseValue(100);
        settings.setBodyImage(bodyImage);
        // Movement settings
        settings.setSwimSpeed(speed);
        settings.setMinDepth(90);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(10);
        settings.setEggSize(Egg.Size.COLOSSAL);
        settings.setEggColor(Egg.Color.GREEN);
        settings.setEggSpawnFrequency(300);
    }

    public void act()
    {
        swim();
    }
}
