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
        fishSettings.setBodyImage(new GreenfootImage("anglerfish.png"));
        fishSettings.setAllowedFeatures(FishFeature.BIG_EYE);
        fishSettings.setCatchOffset(new IntPair(27, 18));
        // Movement settings
        fishSettings.setSwimSpeed(0.1);
        fishSettings.setMinDepth(90);
        fishSettings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 5);
        fishSettings.setAverageTurnInterval(300);
        fishSettings.setMaxTurnDegrees(20);
        // Evolution settings
        fishSettings.setEggSize(Egg.EggSize.SMALL); // TODO: REVERT BACK TO GIGANTIC
        fishSettings.setEggColor(Egg.EggColor.GREEN);
        fishSettings.setEggSpawnFrequency(1000);
        fishSettings.setEvoPointGain(25);
        fishSettings.setEvolutionChance(0.5);
        // Feature locations
        fishSettings.putFeaturePoint(FishFeature.BIG_EYE, new IntPair(19, 8));

        // Sanity check to ensure all settings have been defined
        fishSettings.validate();
    }

    public Anglerfish(int evoPoints, FishFeature... features) {
        super(fishSettings, evoPoints, features);
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public Fish createOffspring() {
        if (Util.randInt(1) == 0) {
            return new Anglerfish(getEvoPoints());
        } else {
            return new Anglerfish(getEvoPoints(), FishFeature.BIG_EYE);
        }
    }
}
