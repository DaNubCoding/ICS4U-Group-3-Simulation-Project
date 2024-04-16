import greenfoot.*;

/**
 * A fish with an impressive sense of smell for blood
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Piranha extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(2);
        settings.setBaseValue(100);
        settings.setBodyImage("fishes/piranha.png");
        settings.setCatchOffset(11, 7);
        settings.setAllowedFeatures(FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(50);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.EggSize.MEDIUM);
        settings.setEggColor(Egg.EggColor.GREEN);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(30);
        settings.setEvolutionChance(0.4);
        settings.setEvolutions(Barracuda.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 10, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 10, -4);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 10, -4);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 2, -2);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 8, -5);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Piranha(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
