import greenfoot.*;

/**
 * A
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Mollusk extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(2);
        settings.setBaseValue(200);
        settings.setBodyImage("fishes/mollusk.png");
        settings.setCatchOffset(10, 6);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(60);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.Size.MEDIUM);
        settings.setEggColor(Egg.Color.PINK);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(50);
        settings.setEvolutionChance(0.4);
        settings.setEvolutions(Jellyfish.class, Lionfish.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 8, 2);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 6, -3);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 7, -6);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Mollusk(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
