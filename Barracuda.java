import greenfoot.*;

/**
 * A large and ferocious predatory fish.
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Barracuda extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(3);
        settings.setBaseValue(100);
        settings.setBodyImage("fishes/barracuda.png");
        settings.setCatchOffset(28, 6);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(70);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.EggSize.LARGE);
        settings.setEggColor(Egg.EggColor.GREEN);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(25);
        settings.setEvolutionChance(0.5);
        settings.setEvolutions(Anglerfish.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 20, 1);
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 26, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 26, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 26, -4);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 18, -1);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 19, -4);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Barracuda(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}