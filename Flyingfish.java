import greenfoot.*;

/**
 * A 
 *
 * @author Stanley Wang
 * @author Sandra Huang
 * @version April 2024
 */
public class Flyingfish extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings settings;
    static {
        settings = new FishSettings();
        // Base settings
        settings.setTier(2);
        settings.setBaseValue(100);
        settings.setBodyImage("fishes/flyingfish.png");
        settings.setCatchOffset(20, 7);
        settings.setAllowedFeatures(FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.1);
        settings.setMinDepth(70);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        settings.setAverageTurnInterval(300);
        settings.setMaxTurnDegrees(20);
        // Evolution settings
        settings.setEggSize(Egg.EggSize.MEDIUM);
        settings.setEggColor(Egg.EggColor.BLUE);
        settings.setEggSpawnFrequency(1000);
        settings.setEvoPointGain(25);
        settings.setEvolutionChance(0.2);
        settings.setEvolutions(Swordfish.class);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 18, -3);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 18, -3);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 18, -3);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 8, -2);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 8, -3);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }

    public Flyingfish(int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
}
