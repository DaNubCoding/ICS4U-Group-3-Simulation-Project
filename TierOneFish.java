import greenfoot.*;

/**
 * A class that holds all fish settings for a basic, tier one fish.
 *
 * @author Sandra Huang
 * @version April 2024
 */
public abstract class TierOneFish extends Fish
{
    public TierOneFish(FishSettings settings, int evoPoints, FishFeature... features) {
        super(settings, evoPoints, features);
    }
    /**
     * Sets fish settings for tier one fish, according to defaults, while specifying the different body image, egg color, and evolutions
     *
     * @param settings the FishSettings to modify, unique to each subclass
     * @param bodyImage the specific image of the fish, unique to each subclass
     * @param eggColor the egg color, unique to each subclass
     * @param evolutions the possible evolutions, unique to each subclass
     */
    public static void initialiseTierOneFishSettings(FishSettings settings, String bodyImage, Egg.Color eggColor, Class<? extends Fish>... evolutions){
        // Base settings
        settings.setTier(1);
        settings.setBaseValue(300);
        settings.setBodyImage(bodyImage);
        settings.setCatchOffset(13, 5);
        settings.setAllowedFeatures(FishFeature.ANGLER_LIGHT, FishFeature.ANGLER_BOMB, FishFeature.ANGLER_SOCK, FishFeature.HAT_BROWN, FishFeature.HAT_PARTY);
        // Movement settings
        settings.setSwimSpeed(0.2);
        settings.setMinDepth(SimulationWorld.SEA_SURFACE_Y + 10);
        settings.setMaxDepth(SimulationWorld.SEA_FLOOR_Y - 20);
        settings.setAverageTurnInterval(200);
        settings.setMaxTurnDegrees(15);
        // Evolution settings
        settings.setEggSize(Egg.Size.SMALL);
        settings.setEggColor(eggColor);
        settings.setEggSpawnFrequency(800);
        settings.setEvoPointGain(80);
        settings.setEvolutionChance(0.8);
        settings.setEvolutions(evolutions);
        // Feature image offsets
        settings.setFeaturePoint(FishFeature.ANGLER_LIGHT, 9, -5);
        settings.setFeaturePoint(FishFeature.ANGLER_BOMB, 9, -5);
        settings.setFeaturePoint(FishFeature.ANGLER_SOCK, 9, -5);
        settings.setFeaturePoint(FishFeature.HAT_BROWN, 3, -2);
        settings.setFeaturePoint(FishFeature.HAT_PARTY, 3, -5);

        // Sanity check to ensure all settings have been defined
        settings.validate();
    }
}
