import greenfoot.*;

/**
 * The final tier of fish, the rapid mutations have turned them into deadly calamities.
 *
 * @author Stanley Wang
 * @version April 2024
 */
public abstract class EndingFish extends Fish
{
    static boolean endFish = false;
    public EndingFish(FishSettings settings, FishFeature... features) {
        super(settings, 0, features);
    }

    /**
     * Initialize the settings for all types of EndingFish.
     *
     * @param settings The partially initialized settings to complete
     */
    public static void initializeEndingFish(FishSettings settings) {
        // Base settings
        settings.setTier(5);
        settings.setBaseValue(100);
        settings.setMinDepth(90);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(10);
        settings.setEggSize(Egg.Size.COLOSSAL);

        // Unused
        settings.setEvoPointGain(0);
        settings.setEvolutionChance(0);
        settings.setCatchOffset(0, 0);
        settings.setEvolutions(null);
        settings.setAllowedFeatures(null);

        settings.validate();
    }

    @Override
    public void act()
    {
        swim();
        reproduce();
    }
}
