import greenfoot.*;
import java.util.Map;
import java.util.EnumMap;
import java.util.Collections;

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
        fishSettings.setBodyImage(new GreenfootImage("anglerfish.png"));
        fishSettings.setCatchOffset(new IntPair(27, 18));
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
        // Feature locations
        Map<FishFeature, IntPair> featurePoints = new EnumMap<>(FishFeature.class);
        featurePoints.put(FishFeature.BIG_EYE, new IntPair(19, 8));
        fishSettings.setFeaturePoints(Collections.unmodifiableMap(featurePoints));

        // Sanity check to ensure all settings have been defined
        fishSettings.validate();
    }

    public Anglerfish(FishFeature... features) {
        super(fishSettings, features);
    }

    @Override
    public void act() {
        swim();

        lookForHook();
        attachToHook();
    }
}
