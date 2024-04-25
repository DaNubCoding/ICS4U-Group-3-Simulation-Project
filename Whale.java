import greenfoot.*;

/**
 * A
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Whale extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(4);
        settings.setBaseValue(600);
        settings.setBodyImage("fishes/whale.png");
        settings.setCatchOffset(28, 6);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(50);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.Size.GIGANTIC);
        settings.setEggColor(Egg.Color.BLUE);
        settings.setEggSpawnFrequency(1500);
        settings.setEvoPointGain(60);
        settings.setEvolutionChance(0.6);
        settings.setEvolutions(Bloop.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 35, 4);
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 41, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 41, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 41, -4);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 29, 0);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 31, -4);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Whale(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
