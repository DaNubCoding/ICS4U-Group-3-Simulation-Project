import greenfoot.*;

/**
 * A large deep sea fish.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
 * @author Stanley Wang
 * @version April 2024
 */
public class Anglerfish extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(4);
        settings.setBaseValue(100);
        settings.setBodyImage("fishes/anglerfish.png");
        settings.setCatchOffset(27, 18);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        settings.addRequiredFeatureSet(
            new FishFeature[] {FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK},
            new int[] {16, 3, 1}
        );
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(90);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.Size.GIGANTIC);
        settings.setEggColor(Egg.Color.GREEN);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(20);
        settings.setEvolutionChance(0.1);
        settings.setEvolutions(null);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 19, 4);
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 22, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 22, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 22, -4);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 13, 0);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 15, -2);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Anglerfish(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
