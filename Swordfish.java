import greenfoot.*;

/**
 * A 
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Swordfish extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(4);
        settings.setBaseValue(100);
        settings.setBodyImage("fishes/swordfish.png");
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
        settings.setEggColor(Egg.EggColor.BLUE);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(25);
        settings.setEvolutionChance(0.2);
        settings.setEvolutions(Whale.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.BIG_EYE, 18, 4);
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 26, 2);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 26, 2);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 26, 2);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 17, 1);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 18, -1);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Swordfish(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
