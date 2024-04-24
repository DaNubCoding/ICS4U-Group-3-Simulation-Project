import greenfoot.*;

/**
 * A
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Lionfish extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(3);
        settings.setBaseValue(550);
        settings.setBodyImage("fishes/lionfish.png");
        settings.setCatchOffset(29, 16);
        settings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
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
        settings.setEvolutionChance(0.0);
        settings.setEvolutions(null);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 21, 10);
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 25, 4);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 25, 4);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 25, 4);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 15, 4);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 16, 2);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Lionfish(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
