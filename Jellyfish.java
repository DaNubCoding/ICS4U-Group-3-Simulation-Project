import greenfoot.*;

/**
 * A
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Jellyfish extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(3);
        settings.setBaseValue(500);
        settings.setBodyImage("fishes/jellyfish.png");
        settings.setCatchOffset(15, 8);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(70);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.Size.LARGE);
        settings.setEggColor(Egg.Color.PINK);
        settings.setEggSpawnFrequency(1200);
        settings.setEvoPointGain(40);
        settings.setEvolutionChance(0.4);
        settings.setEvolutions(Squid.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 12, 3);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 10, -2);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 11, -5);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Jellyfish(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
