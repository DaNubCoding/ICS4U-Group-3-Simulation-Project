import greenfoot.*;

/**
 * A deep sea creature delicious when fried and served with marinara sauce
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Squid extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(4);
        settings.setBaseValue(300);
        settings.setBodyImage("fishes/squid.png");
        settings.setCatchOffset(15, 6);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(90);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.Size.GIGANTIC);
        settings.setEggColor(Egg.Color.PINK);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(35);
        settings.setEvolutionChance(0.3);
        settings.setEvolutions(null);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 15, 3);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 20, -1);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 21, -4);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Squid(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
