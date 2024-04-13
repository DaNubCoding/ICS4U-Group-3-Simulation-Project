import greenfoot.*;

/**
 * A large deep sea fish.
 *
 * @author Martin Baldwin
 * @author Andrew Wang
 * @version April 2024
 */
public class Anglerfish extends Fish {
    // Collection of all settings that define this subclass of Fish
    private static final FishSettings fishSettings;
    static {
        fishSettings = new FishSettings();
        // Base settings
        fishSettings.setBaseValue(100);
        fishSettings.setBodyImage("anglerfish.png");
        fishSettings.setCatchOffset(27, 18);
        fishSettings.setAllowedFeatures(FishFeature.BIG_EYE, FishFeature.HAT_PARTY, FishFeature.HAT_BROWN, FishFeature.ANGLER_SOCK, FishFeature.ANGLER_BOMB);
        // Movement settings
        fishSettings.setSwimSpeed(0.1);
        fishSettings.setMinDepth(90);
        fishSettings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        fishSettings.setAverageTurnInterval(300);
        fishSettings.setMaxTurnDegrees(20);
        // Evolution settings
        fishSettings.setEggSize(Egg.EggSize.GIGANTIC);
        fishSettings.setEggColor(Egg.EggColor.GREEN);
        fishSettings.setEggSpawnFrequency(1000);
        fishSettings.setEvoPointGain(25);
        fishSettings.setEvolutionChance(0.5);
        fishSettings.setEvolutions(null);
        // Feature image offsets
        fishSettings.setFeaturePoint(FishFeature.BIG_EYE, 19, 8);
        fishSettings.setFeaturePoint(FishFeature.HAT_PARTY, 15, 2);
        fishSettings.setFeaturePoint(FishFeature.HAT_BROWN, 13, 4);
        fishSettings.setFeaturePoint(FishFeature.ANGLER_SOCK, 22, 0);
        fishSettings.setFeaturePoint(FishFeature.ANGLER_BOMB, 22, 0);

        // Sanity check to ensure all settings have been defined
        fishSettings.validate();
    }

    public Anglerfish(int evoPoints, FishFeature... features) {
        super(fishSettings, evoPoints, features);
    }
}
